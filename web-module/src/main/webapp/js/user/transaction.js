import {formatDateTime, getBaseUrl, pushNotify} from "../util.js";

const quickTransfer = document.getElementById('quick_transfer');
const dateInput = document.getElementById('date');
const formEl = document.querySelector('form');

async function loadUserAccounts(customerId) {
    const response = await fetch(getBaseUrl(`account?id=${customerId}`));
    if (response.ok) {
        const data = await response.json();
        if (data.status === 'success') {
            const accounts = data.accounts;
            const accountSelectEl = document.getElementById('account');
            loadSelectOptions(accountSelectEl, accounts)
        } else {
            pushNotify("error", "Error", data.message || "Failed to load accounts.");
        }
    } else {
        pushNotify("error", "Error", "Failed to load accounts.");
    }
}

const loadSelectOptions = (selectElement, accounts) => {
    while (selectElement.options.length > 1) {
        selectElement.remove(1);
    }
    accounts.forEach(account => {
        const option = document.createElement('option');
        option.value = account.accountNumber;
        option.id = account.accountId;
        option.textContent = `${account.accountName} (${account.accountNumber})`;
        selectElement.appendChild(option);
    });
}

formEl.addEventListener('submit', async (event) => {
    event.preventDefault();

    const fromAccountId = formEl.account.selectedOptions[0].id;

    if (quickTransfer.checked) {
        dateInput.value = '';
        try {
            const response = await transaction(
                'fund-transaction',
                {
                    fromAccountId: fromAccountId,
                    fromAccountNumber:'',
                    toAccountNumber: formEl.accountNumber.value,
                    amount: formEl.amount.value,
                    description: formEl.description.value,
                }
            );

            if (response.ok) {
                const data = await response.json();
                if (data.status === 'success') {
                    loadAllTransactions(customerId);
                    pushNotify('success', 'Transfer Successful', data.message);
                    formEl.reset();
                } else {
                    pushNotify('error', 'Transfer Failed', data.message);
                }
            } else {
                const errorData = await response.json();
                pushNotify('error', 'Transfer Failed', errorData.message || 'An error occurred while processing your request.');
            }
        } catch (error) {
            console.error('Error during transaction:', error);
            pushNotify('error', 'Transfer Failed', 'An error occurred while processing your request.');
        }

    } else {
        try {
            const response = await transaction(
                'scheduled-transaction',
                {
                    fromAccountId: fromAccountId,
                    fromAccountNumber:'',
                    toAccountNumber: formEl.accountNumber.value,
                    amount: formEl.amount.value,
                    transferDate: formEl.date.value,
                    description: formEl.description.value,
                }
            );

            if (response.ok) {
                const data = await response.json();
                if (data.status === 'success') {
                    loadAllTransactions(customerId);
                    pushNotify('success', 'Transfer Successful', data.message);
                    formEl.reset();
                } else {
                    pushNotify('error', 'Transfer Failed', data.message);
                }
            } else {
                const errorData = await response.json();
                pushNotify('error', 'Transfer Failed', errorData.message || 'An error occurred while processing your request.');
            }
        } catch (error) {
            console.error('Error during transaction:', error);
            pushNotify('error', 'Transfer Failed', 'An error occurred while processing your request.');
        }
    }

})

async function transaction(url, jsonObject) {
    return await fetch(getBaseUrl(url), {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(jsonObject)
    })
}

const loadTransactionList = (transactionEl, transactions) => {
    transactionEl.innerHTML = ''; // Clear existing content
    transactionEl.innerHTML = transactions.map(transaction => (
        `<div class="history-item">
            <div class="history-info">
                <span class="description">${transaction.description}</span>
                <span class="date">${formatDateTime(transaction.transactionTime)}</span>
            </div>
            <div class="history-details">
                <span class="amount">$${transaction.amount}</span>
                <span 
                class="status"
                style="color: ${transaction.status === 'PENDING' ? '#edec20' : transaction.status === 'SUCCESS' ? 'green' : 'red'};"
                >${transaction.status}</span>
            </div>
        </div>`
    )).join('');
}

async function loadAllTransactions(customerId) {

    const response = await fetch(getBaseUrl(`fund-transaction?id=${customerId}`));

    try {
        if (response.ok) {
            const data = await response.json();
            if (data.status === 'success') {
                const transactions = data.transactions;
                const transactionEl = document.querySelector('.history-list');
                if (transactions && transactions.length) {

                    loadTransactionList(transactionEl, transactions);

                } else {
                    transactionEl.innerHTML = '<p style="text-align: center;color: red;font-weight: bold">No transactions found.</p>';
                }
            } else {
                pushNotify("error", "Error", data.message || "Failed to load transactions.");
            }
        } else {
            pushNotify("error", "Error", "Failed to load transactions.");
        }
    } catch (error) {
        console.error('Error loading transactions:', error);
        pushNotify("error", "Error", "An error occurred while loading transactions.");
    }

}

document.addEventListener('DOMContentLoaded', () => {
    if (typeof customerId !== 'undefined' && customerId !== null) {
        loadUserAccounts(customerId);
        loadAllTransactions(customerId);
    } else {
        console.error("Customer ID is not defined or is null.");
    }
    quickTransfer.addEventListener('change', () => {
        if (quickTransfer.checked) {
            dateInput.required = false;
            dateInput.disabled = true;
        } else {
            dateInput.required = true;
            dateInput.disabled = false;
        }
    });
});

