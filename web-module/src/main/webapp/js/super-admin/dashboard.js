import {formatDateTime, formatInterestRate, getBaseUrl} from "../util.js";

const loadLogList = (logList) => {

    const interestLogList = document.getElementById("interest_list");
    interestLogList.innerHTML = ''; // Clear existing content

    if (!logList || logList.length === 0) {
        interestLogList.innerHTML = '<p style="text-align: center;background-color: red;font-size: 1.2rem">No interest logs available.</p>';
        return;
    }

    interestLogList.innerHTML = logList.map(log => (
        ` <div class="interest-item">
            <p class="interest-date">${formatDateTime(log.appliedDate)}</p>
            <p class="interest-account">${log.accountNumber}</p>
            <p class="interest-rate">${formatInterestRate(log.interestRate)}%</p>
        </div>`
    )).join('');

}

async function loadDashboardData() {
    const response = await fetch(getBaseUrl('super-admin/dashboard'));
    if (response.ok) {
        const data = await response.json();
        if (data.status === 'success') {

            document.getElementById("total_account").textContent = data.totalAccounts || 0;
            document.getElementById("total_transaction").textContent = data.totalTransactions || 0;
            document.getElementById("total_users").textContent = data.totalUsers || 0;

            const logList = data.dalyInterestLog;
            loadLogList(logList);

        } else {
            console.error("Error loading dashboard data:", data.message);
        }
    } else {
        console.error("Failed to fetch dashboard data.");
    }
}

document.addEventListener("DOMContentLoaded", loadDashboardData);