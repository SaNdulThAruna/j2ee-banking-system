import {getBaseUrl, pushNotify} from "../util.js";

const formEl = document.querySelector('form');
const inputSearchEl = document.querySelector('#search-input');
const searchButtonEl = document.querySelector('#search-button');

formEl.addEventListener('submit', async (event) => {

    event.preventDefault();

    const response = await fetch(getBaseUrl('super_admin/accounts'), {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            fullName: formEl.fullName.value,
            email: formEl.email.value,
            phone: formEl.phone.value,
            address: formEl.address.value,
            username: formEl.username.value,
            password: formEl.password.value,
        })
    });


    try {
        if (response.ok) {
            const data = await response.json();
            if (data.status === 'success') {
                pushNotify("success", "Success", "Account created successfully.");
                loadTable(); // Reload the table to show the new account
                formEl.reset(); // Reset the form
            } else {
                pushNotify("error", "Error", data.message || "Failed to create account.");
            }
        } else {
            pushNotify("error", "Error", "Failed to create account.");
        }
    } catch (error) {
        console.error('Error creating account:', error);
        pushNotify("error", "Error", "An error occurred while creating the account.");
    }
});

searchButtonEl.addEventListener('click', (event) => {
    loadTable(inputSearchEl.value);
})

const loadTableRows = (accounts) => {
    const accountTableBody = document.querySelector('.accounts-table tbody');
    accountTableBody.innerHTML = ''; // Clear existing content


    if (!accounts || accounts.length === 0) {
        accountTableBody.innerHTML = '<tr><td colspan="6" style="text-align: center;">No accounts available.</td></tr>';
        return;
    }

    accountTableBody.innerHTML = accounts.map(account => (
        `<tr>
            <td>${account.id}</td>
            <td>${account.fullName}</td>
            <td>${account.email}</td>
            <td>${account.username}</td>
            <td>${account.role}</td>
            <td style="color: ${account.status === 'ACTIVE' ? 'limegreen':'red'}">${account.status}</td>
        </tr>`
    )).join('');
}

function loadTable(searchText = '') {
    fetch(getBaseUrl(`super_admin/accounts?searchText=${searchText}`))
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (data.status === 'success') {
                loadTableRows(data.accounts);
            } else {
                pushNotify("error", "Error", data.message || "Failed to load accounts.");
            }
        })
        .catch(error => {
            console.error('Error loading accounts:', error);
            pushNotify("error", "Error", "An error occurred while loading accounts.");
        });
}

document.addEventListener("DOMContentLoaded", () => {
    loadTable(); // Load the table on page load
});