import {formatDateTime, getBaseUrl, pushNotify} from "../util.js";

const quickTransfer = document.getElementById('quick_transfer');
const dateInput = document.getElementById('date');
const formEl = document.querySelector('#transactionForm');
const filterFormEl = document.querySelector('#filterForm');

async function loadAllTransactions(accountNumber='',filterDate=''){

    const response = await fetch(getBaseUrl(`admin/filter-transaction?accountNumber=${accountNumber}&date=${filterDate}`));

    try{

        if (response.ok){

            const data = await response.json();
            if (data.status === 'success') {
                const transactions = data.transactionList || [];
                const tableBody = document.querySelector('.history-table tbody');
                tableBody.innerHTML = ''; // Clear existing content

                if (transactions.length === 0) {
                    tableBody.innerHTML = '<tr><td colspan="6" style="text-align: center;">No transactions found.</td></tr>';
                    return;
                }

                transactions.forEach(transaction => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${transaction.transactionId}</td>
                        <td>${transaction.fromAccountNumber}</td>
                        <td>${transaction.toAccountNumber}</td>
                        <td>${transaction.amount}</td>
                        <td>${transaction.description}</td>
                        <td>${formatDateTime(transaction.transactionTime)}</td>
                    `;
                    tableBody.appendChild(row);
                });
            } else {
                pushNotify('error', 'Error', data.message || 'Failed to load transactions.');
            }


        }else{
            const errorData = await response.json();
            pushNotify('error', 'Error', errorData.message || 'Failed to load transactions.');
        }

    }catch(e){
        console.error('Error loading transactions:', e);
        pushNotify('error', 'Error', 'An error occurred while loading transactions.');
    }

}

filterFormEl.addEventListener('submit', (event)=>{
    event.preventDefault(); // Prevent the default form submission
    const accountNumber = filterFormEl.searchText.value;
    const filterDate = filterFormEl.filterDate.value;
    loadAllTransactions(accountNumber, filterDate);
})

formEl.addEventListener('submit', async (event) => {
    event.preventDefault(); // Prevent the default form submission

    if (quickTransfer.checked) {
        dateInput.value = '';
        try {
            const response = await transaction(
                'fund-transaction',
                {
                    fromAccountId: 0,
                    fromAccountNumber: formEl.fromAccount.value,
                    toAccountNumber: formEl.toAccount.value,
                    amount: formEl.amount.value,
                    description: formEl.description.value,
                }
            );

            if (response.ok) {
                const data = await response.json();
                if (data.status === 'success') {
                    loadAllTransactions(filterFormEl.searchText.value, filterFormEl.filterDate.value);
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
                    fromAccountId: 0,
                    fromAccountNumber: formEl.fromAccount.value,
                    toAccountNumber: formEl.toAccount.value,
                    amount: formEl.amount.value,
                    transferDate: formEl.date.value,
                    description: formEl.description.value,
                }
            );

            if (response.ok) {
                const data = await response.json();
                if (data.status === 'success') {
                    loadAllTransactions(filterFormEl.searchText.value, filterFormEl.filterDate.value);
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

document.addEventListener('DOMContentLoaded', () => {

    loadAllTransactions('','');

    quickTransfer.addEventListener('change', () => {
        if (quickTransfer.checked) {
            dateInput.required = false;
            dateInput.disabled = true;
        } else {
            dateInput.required = true;
            dateInput.disabled = false;
        }
    });
})