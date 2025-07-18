import {getBaseUrl, pushNotify} from "../util.js";

const formEl = document.querySelector('form');
const statusSelectEl = document.querySelector('#status-filter-select');
const typeSelectEl = document.querySelector('#type-filter-select');

const loadTableData = (data) => {
    const tableBody = document.querySelector('.users-table tbody');
    tableBody.innerHTML = ''; // Clear existing content

    if (!data || data.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center; color: red">No users found.</td></tr>';
        return;
    }

    tableBody.innerHTML = data.map(user => (
        `<tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td>${user.role}</td>
            <td>
                <button 
                class="${user.status === 'ACTIVE' ? 'btn-active' : 'btn-inactive'}" 
                >${user.status}</button>
            </td>
        </tr>`
    )).join('');
}

async function loadTable(searchText, status, type) {

    const response = await fetch(getBaseUrl('super_admin/users?searchText=' + searchText + "&status=" + status + "&type=" + type));

    try {
        if (response.ok) {
            const data = await response.json();
            if (data.status === 'success') {
                const users = data.users;
                loadTableData(users);
            } else {
                pushNotify("error", "Error", data.message || "Failed to load users.");
            }
        } else {
            pushNotify("error", "Error", "Failed to load users.");
        }
    } catch (error) {
        console.error('Error loading users:', error);
        pushNotify("error", "Error", "An error occurred while loading users.");
    }
}

formEl.addEventListener('submit', (event) => {
    event.preventDefault(); // Prevent form submission
    loadTable(formEl.searchText.value.trim(), statusSelectEl.value, typeSelectEl.value);
})

statusSelectEl.addEventListener('change', () => {
    loadTable(formEl.searchText.value.trim(), statusSelectEl.value, typeSelectEl.value);
});

typeSelectEl.addEventListener('change', () => {
    loadTable(formEl.searchText.value.trim(), statusSelectEl.value, typeSelectEl.value);
});

document.querySelector('.users-table tbody').addEventListener('click', (event) => {
    if (event.target.matches('button')) {
        const button = event.target;
        const userId = button.closest('tr').children[0].textContent.trim();
        console.log(`User ID: ${userId}`);
        const newStatus = button.classList.contains('btn-active') ? 'INACTIVE' : 'ACTIVE';

        fetch(getBaseUrl(`super_admin/users?id=${userId}&status=${newStatus}`), {
            method: 'PUT'
        })
        .then(response => response.json())
        .then(data => {
            if (data.status === 'success') {
                pushNotify("success", "Success", `User status updated to ${newStatus}.`);
                loadTable(formEl.searchText.value.trim(), statusSelectEl.value, typeSelectEl.value);
            } else {
                pushNotify("error", "Error", data.message || "Failed to update user status.");
            }
        })
        .catch(error => {
            console.error('Error updating user status:', error);
            pushNotify("error", "Error", "An error occurred while updating user status.");
        });
    }
})

document.addEventListener('DOMContentLoaded', () => {
    loadTable('', 'all', 'all');
})