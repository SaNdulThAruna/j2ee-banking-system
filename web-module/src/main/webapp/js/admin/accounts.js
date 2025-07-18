import {accountTypes, getBaseUrl, pushNotify} from "../util.js";

const accountForm = document.getElementById('create-account-form');
const searchForm = document.getElementById('search-form');
const createAccountSelectElement = document.getElementById('account-type');
const filterAccountSelectElement = document.getElementById('at');

function loadAccountType(selectElement) {

    while (selectElement.options.length > 1) {
        selectElement.remove(1);
    }
    for (const [value, label] of Object.entries(accountTypes)) {
        const option = document.createElement('option');
        option.value = value;
        option.textContent = label;
        selectElement.appendChild(option);
    }
}

accountForm.addEventListener('submit', async (event) => {
    event.preventDefault();

    const response = await fetch(getBaseUrl('admin/account-management'), {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            fullName: accountForm.fullName.value,
            email: accountForm.email.value,
            username: accountForm.username.value,
            password: accountForm.username.value + '123',
            accountName: accountForm.accountName.value,
            phone: accountForm.phone.value,
            address: accountForm.address.value,
            type: accountForm.accountType.value,
            balance: accountForm.balance.value,
            role: 'user'

        })
    })

    if (response.ok) {
        const data = await response.json();
        if (data.status === 'success') {
            loadAccountTable()
            pushNotify('success', 'Account created successfully', `Account Name: ${accountForm.accountName.value}`);
            accountForm.reset();
        } else {
            pushNotify('error', 'Account creation failed', data.message);
        }
    } else {
        const errorData = await response.json();
        console.error('Error creating account:', errorData);
        pushNotify('error', 'Account creation failed', errorData.message);
    }

})

const loadTableData = (accountsList) => {

    const tableBody = document.querySelector('.accounts-table tbody');
    tableBody.innerHTML = ''; // Clear existing rows

    if (!Array.isArray(accountsList) || accountsList.length === 0) {
        const emptyRow = document.createElement('tr');
        emptyRow.innerHTML = '<td colspan="8" style="text-align: center;color: red">No accounts found</td>';
        tableBody.appendChild(emptyRow);
        return;
    }

    accountsList.forEach(account => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${account.id}</td>
            <td>${account.fullName}</td>
            <td>${account.email}</td>
            <td>${account.username}</td>
            <td>${account.accountName}</td>
            <td>${account.accountType}</td>
            <td>${account.balance}</td>
            <td>${account.status}</td>
        `;
        tableBody.appendChild(row);
    });

    if (accountsList.length === 0) {
        const emptyRow = document.createElement('tr');
        emptyRow.innerHTML = '<td colspan="7" class="text-center">No accounts found</td>';
        tableBody.appendChild(emptyRow);
    }

}

async function loadAccountTable(searchText = '', accountType = '',status = '') {

    const response = await fetch(getBaseUrl(`admin/account-management?searchText=${encodeURIComponent(searchText)}&accountType=${encodeURIComponent(accountType)}&status=${encodeURIComponent(status)}`));

    try{
        const data = await response.json();
        if (data.status === 'success') {
            const accounts = data.accounts;

            loadTableData(accounts)

        } else {
            pushNotify('error', 'Failed to load accounts', data.message);
        }
    }catch (error) {
        console.error('Error loading accounts:', error);
        pushNotify('error', 'Failed to load accounts', 'An unexpected error occurred.');
    }

}

searchForm.addEventListener('submit',(event) => {
    event.preventDefault();
   loadAccountTable(searchForm.searchText.value, filterAccountSelectElement.value, searchForm.status.value);
});


document.addEventListener('DOMContentLoaded', () => {
    loadAccountTable();
    loadAccountType(createAccountSelectElement);
    loadAccountType(filterAccountSelectElement);
})