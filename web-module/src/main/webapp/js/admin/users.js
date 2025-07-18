import {getBaseUrl, pushNotify} from "../util.js";

const formEl = document.querySelector('form');

formEl.addEventListener('submit',(event) => {
    event.preventDefault(); // Prevent the default form submission

    loadUsers(formEl.searchText.value)

});

async function loadUsers(searchText = '') {
    try {
        const response = await fetch(getBaseUrl(`admin/users?searchText=${searchText}`));

        if (response.ok) {
            const result = await response.json();
            if (result.status === 'success') {
                const userList = result.users || [];
                loadTable(userList);
            } else {
                pushNotify("error","Error", result.message || "Failed to load users. Please try again.");
            }
        } else {
            pushNotify("error","Error", "Failed to load users. Please try again later.");
        }
    } catch (error) {
        console.error('Error loading users:', error);
        pushNotify("error","Error", "An error occurred while loading users.");
    }
}

function loadTable(userList) {
    const tableBody = document.querySelector('.users-table tbody');
    tableBody.innerHTML = ''; // Clear existing content

    if (!userList || userList.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="5" style="text-align: center;">No users found.</td></tr>';
        return;
    }

    userList.forEach(user => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td>${user.role}</td>
            <td>
                <button 
                class="${user.status === 'ACTIVE' ? 'btn-active' : 'btn-inactive'}" 
                >${user.status}</button>
            </td>
        `;
        tableBody.appendChild(row);
    });
}

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
                    loadUsers(formEl.searchText.value.trim());
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
    loadUsers('');
})