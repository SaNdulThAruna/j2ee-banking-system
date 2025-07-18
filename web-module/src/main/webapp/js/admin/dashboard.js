import {formatDateTime, getBaseUrl} from "../util.js";

async function loadDashboardData() {

    const response = await fetch(getBaseUrl('admin/dashboard'));
    if (response.ok) {
        const data = await response.json();
        if (data.status === 'success') {
            document.getElementById("ta").textContent = data.totalAccounts || 0;
            document.getElementById("tt").textContent = data.totalTransactions || 0;
            document.getElementById("ac").textContent = data.activeCustomersCount || 0;

            const transactionList = data.transactionList || [];

            loadTransactionLog(transactionList)

        } else {
            console.error("Error loading dashboard data:", data.message);
        }
    } else {
        console.error("Failed to fetch dashboard data.");
    }
}

function loadTransactionLog(transactionList) {

    const trEl = document.querySelector(".transaction-list");
    trEl.innerHTML = ''; // Clear existing content

    if (!transactionList || transactionList.length === 0) {
        trEl.innerHTML = '<p style="text-align: center;background-color: red;font-size: 1.2rem">No transactions available.</p>';
        return;
    }

    trEl.innerHTML = transactionList.map(transaction => (
        `<div class="transaction-item">
            <p class="transaction-date">${formatDateTime(transaction.transactionTime)}</p>
            <p class="transaction-ac-name">${transaction.fromAccountNumber}</p>
            <p class="transaction-type">${transaction.type}</p>
            <p class="transaction-amount">${transaction.amount}</p>
        </div>`
    )).join('');

}

document.addEventListener('DOMContentLoaded',loadDashboardData)