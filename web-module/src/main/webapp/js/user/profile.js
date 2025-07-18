import {getBaseUrl, pushNotify} from "../util.js";

const formEl = document.querySelector('form');
const fullName = document.querySelector('#fullName');
const username = document.querySelector('#username');
const email = document.querySelector('#email');
const phone = document.querySelector('#phone');
const address = document.querySelector('#address');

document.addEventListener('DOMContentLoaded', () => {
    if (typeof customerId !== 'undefined' && customerId !== null) {
        loadProfile(customerId);
    } else {
        console.error('Customer ID is missing.');
    }
});

formEl.addEventListener('submit', (event) => {
    event.preventDefault();
    updateProfile(customerId);
});

async function loadProfile(customerId) {
    try {
        const response = await fetch(getBaseUrl('profile?id=' + customerId));
        if (!response.ok) {
            pushNotify('error', 'Error', 'Failed to load profile. Please try again later.');
            return;
        }
        const data = await response.json();
        if (data.status === 'success') {
            const profile = data.data;
            fullName.value = profile.fullName || '';
            username.value = profile.username || '';
            email.value = profile.email || '';
            phone.value = profile.phone || '';
            address.value = profile.address || '';
        } else {
            console.error('Failed to load profile:', data.message);
            pushNotify('error', 'Error', 'Failed to load profile. Please try again later.');
        }
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
        pushNotify('error', 'Error', 'Failed to load profile. Please try again later.');
    }
}

async function updateProfile(customerId) {
    try {
        const response = await fetch(getBaseUrl('profile'), {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: customerId,
                fullName: fullName.value,
                username: username.value,
                phone: phone.value,
                address: address.value
            })
        });

        const data = await response.json();
        if (data.status === 'success') {
            pushNotify('success', 'Profile Updated', 'Your profile has been successfully updated.');
        } else {
            pushNotify('error', 'Update Failed', data.message || 'Unknown error occurred.');
        }
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
        pushNotify('error', 'Update Failed', 'Failed to update profile. Please try again later.');
    }
}