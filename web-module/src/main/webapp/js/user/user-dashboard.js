import {formatDateTime, getBaseUrl, pushNotify} from "../util.js";

async function loadData(customerId) {
    try {
        const response = await fetch(getBaseUrl("user-dashboard?id=" + customerId));
        if (!response.ok) {
            pushNotify("error", "Error", "Failed to load user dashboard data.");
            return;
        }
        const data = await response.json();
        if (data) {
            const totalBalance = document.getElementById("total_amount");
            const accountList = document.getElementById("account_list");
            const historyList = document.getElementById("history_list");

            if (data.status === 'success') {
                const dashboard = data.data;
                totalBalance.textContent = `$${dashboard.totalBalance}`;
                loadAccountList(accountList, dashboard.accountsList);
                loadTransactionHistory(historyList, dashboard.transactionList);
            } else {
                pushNotify("error", "Error", "No user data found.");
            }
        }
    } catch (error) {
        console.error("Error fetching user dashboard data:", error);
        pushNotify("error", "Error", "An error occurred while loading user dashboard data.");
    }
}

const loadAccountList = (accountList, accountArray) => {
    accountList.innerHTML = '';
    if (!accountArray || accountArray.length === 0) {
        const noAccountsMessage = document.createElement('p');
        noAccountsMessage.style.color = 'red';
        noAccountsMessage.style.textAlign = 'center';
        noAccountsMessage.style.fontSize = '1.2rem';
        noAccountsMessage.style.marginTop = '20px';
        noAccountsMessage.style.fontWeight = 'bold';
        noAccountsMessage.textContent = 'No accounts available.';
        accountList.appendChild(noAccountsMessage);
        return;
    }

    accountList.innerHTML = accountArray.map(account => (
        `<a href="accounts.jsp" class="account-item">
            <div class="account-info">
                <h3>${account.accountName} - ${account.accountNumber}</h3>
                <p>$${account.balance}</p>
            </div>
            <div class="account-type-container">
                <p class="account-type">${account.accountType}</p>
            </div>
        </a>`
    )).join('');
}

const loadTransactionHistory = (historyList, historyArray) => {
    historyList.innerHTML = '';
    if (!historyArray || historyArray.length === 0) {
        const noHistoryMessage = document.createElement('p');
        noHistoryMessage.style.color = 'red';
        noHistoryMessage.style.textAlign = 'center';
        noHistoryMessage.style.fontSize = '1.2rem';
        noHistoryMessage.style.marginTop = '20px';
        noHistoryMessage.style.fontWeight = 'bold';
        noHistoryMessage.textContent = 'No history available.';
        historyList.appendChild(noHistoryMessage);
        return;
    }

    historyList.innerHTML = historyArray.map(account => (
        `<a href="#" class="transaction-item">
                <div class="transaction-info">
                    <h3>${account.description}</h3>
                    <p>${formatDateTime(account.date)}</p>
                </div>
                <div class="transaction-amount">
                    <p class="amount">$${account.amount}</p>
                </div>
        </a>`
    )).join('');
}


document.addEventListener('DOMContentLoaded', async () => {
    if (typeof customerId !== 'undefined' && customerId) {
        console.log("Customer ID:", customerId);
        await loadData(customerId);
    } else {
        pushNotify("error", "Error", "Customer ID is missing.");
    }
});