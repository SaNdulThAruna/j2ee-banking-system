import {accountTypes, getBaseUrl, pushNotify} from "../util.js";

const accountTypesEl = document.getElementById('accountType');
const formEl = document.querySelector('form');

function loadAccountTypes() {
    while (accountTypesEl.options.length > 1) {
        accountTypesEl.remove(1);
    }
    for (const [value, label] of Object.entries(accountTypes)) {
        const option = document.createElement('option');
        option.value = value;
        option.textContent = label;
        accountTypesEl.appendChild(option);
    }
}

formEl.addEventListener('submit', async (event) => {
    event.preventDefault();

    const response = await fetch(getBaseUrl('account'), {
        method: 'POST',
        body: JSON.stringify(
            {
                customerId: customerId,
                accountName: formEl.accountName.value,
                accountType: accountTypesEl.value,
            }),
    })

    if (response.ok) {
        const data = await response.json();
        if (data.status === 'success') {
            loadAllAccounts(customerId)
            pushNotify("success", "Success", "Account created successfully.");
        } else {
            pushNotify("error", "Error", data.message || "Failed to create account. Please try again.");
        }
    } else {
        pushNotify("error", "Error", "Failed to create account. Please try again.");
    }

})

async function loadAllAccounts(customerId) {
    const response = await fetch(getBaseUrl(`account?id=${customerId}`));
    if (response.ok) {
        const data = await response.json();
        if (data.status === 'success') {
            const accounts = data.accounts;
            const accountListEl = document.getElementById('account_list');
            loadAccountList(accountListEl, accounts);
        } else {
            pushNotify("error", "Error", data.message || "Failed to load accounts.");
        }
    } else {
        pushNotify("error", "Error", "Failed to load accounts.");
    }
}

const loadAccountList = async (element, list) => {
    element.innerHTML = ''; // Clear existing accounts
    element.innerHTML = list.map(account => (
        `<div class="account-item">
            <div class="account-info">
                <h3>${account.accountName} - ${account.accountNumber}</h3>
                ${account.accountStatus === 'ACTIVE' ?
            `<i class="fa-solid fa-trash" id="${account.accountNumber}"></i>`
            :
            `<p style="text-align: right;color: red;font-weight: bold">${account.accountStatus}</p>`}
            </div>
            <div class="account-details">
                <p>$${account.balance}</p>
                <span class="account-type">${account.accountType}</span>
            </div>
        </div>`
    )).join('')
}

// Add event listener for delete icons
document.getElementById('account_list').addEventListener('click', async (event) => {
    if (event.target.classList.contains('fa-trash')) {
        const accountNumber = event.target.id;
        const response = await fetch(getBaseUrl(`account?accountNumber=${accountNumber}`), {
            method: 'DELETE',
        });

        if (response.ok) {
            const data = await response.json();
            if (data.status === 'success') {
                pushNotify("success", "Success", "Account deleted successfully.");
                loadAllAccounts(customerId);
            } else {
                pushNotify("error", "Error", data.message || "Failed to delete account.");
            }
        } else {
            pushNotify("error", "Error", "Failed to delete account.");
        }
    }
});

document.addEventListener('DOMContentLoaded', () => {
    loadAccountTypes()
    if (typeof customerId !== 'undefined' && customerId !== null) {
        loadAllAccounts(customerId);
    } else {
        console.error("Customer ID is not defined or is null.");
    }
});