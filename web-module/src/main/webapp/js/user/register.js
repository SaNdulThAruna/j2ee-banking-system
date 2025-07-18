import {getBaseUrl, pushNotify, setupPasswordToggle} from "../util.js";

const visibleEl = document.getElementById("visible");
const passwordEl = document.getElementById("password");
const formEl = document.querySelector("form");
const submitBtn = formEl.querySelector('button[type="submit"]');

setupPasswordToggle(visibleEl, passwordEl);

formEl.addEventListener("submit", async function (e) {
    e.preventDefault();
    submitBtn.disabled = true;

    try {
        const response = await fetch(getBaseUrl("register"), {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username: formEl.username.value,
                password: formEl.password.value,
                email: formEl.email.value,
                fullName: formEl.fullName.value,
                phone: formEl.phone.value,
                address: formEl.address.value,
                role: "USER"
            })
        });

        const data = await response.json();

        if (response.ok && data.status === "success") {
            window.location.href = getBaseUrl("index.jsp");
        } else {
            pushNotify('error', 'Registration Failed', data.message || 'Unknown error');
        }
    } catch (err) {
        pushNotify('error', 'Network Error', 'Could not connect to server.');
    } finally {
        submitBtn.disabled = false;
    }
});