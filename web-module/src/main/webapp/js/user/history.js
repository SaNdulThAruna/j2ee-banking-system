import {getBaseUrl, pushNotify,formatDateTime} from "../util.js";

const formEl = document.querySelector('form');

const loadHistory = async (customerId, date) => {
    let url = 'fund-transaction?id=' + customerId;
    if (date) {
        url += '&date=' + date;
    }
    const response = await fetch(getBaseUrl(url));
    if (!response.ok) {
        pushNotify('error', 'Error', 'Failed to load history');
        throw new Error('Failed to load history');
    }

    try {
        const data = await response.json();
        if (data.status === 'success') {
            const transactions = data.transactions;
            loadTableRows(transactions);
        } else {
            pushNotify('error', 'Error', data.message || 'Failed to load history');
            loadTableRows([]);
        }
    } catch (error) {
        console.error('Error parsing history data:', error);
        pushNotify('error', 'Error', 'Failed to parse history data');
        loadTableRows([]);
    }
};

const loadTableRows = (data) => {
    const tableBody = document.querySelector('.history-table tbody');
    tableBody.innerHTML = ''; // Clear existing rows

    if (!data || data.length === 0) {
        const row = document.createElement('tr');
        row.innerHTML = '<td colspan="5" style="color: red;text-align: center; font-size: 1.3rem">No transactions found.</td>';
        tableBody.appendChild(row);
        return;
    }

    data.forEach(item => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${formatDateTime(item.transactionTime)}</td>
            <td>${item.type}</td>
            <td>${item.amount}</td>
            <td>${item.status}</td>
            <td>${item.description}</td>
        `;
        tableBody.appendChild(row);
    });
};

formEl.addEventListener('submit', (event) => {
    event.preventDefault(); // Prevent form submission
    loadHistory(customerId, formEl.filterDate.value);
});

document.addEventListener('DOMContentLoaded', () => {
    if (typeof customerId !== 'undefined' && customerId !== null) {
        loadHistory(customerId, null);
    } else {
        console.error("Customer ID is not defined or is null.");
    }
});