function setupPasswordToggle(eyeIcon, passwordInput) {
    eyeIcon.addEventListener("click", function () {
        if (passwordInput.type === "password") {
            passwordInput.type = "text";
            eyeIcon.classList.add("fa-eye-slash");
            eyeIcon.classList.remove("fa-eye");
        } else {
            passwordInput.type = "password";
            eyeIcon.classList.add("fa-eye");
            eyeIcon.classList.remove("fa-eye-slash");
        }
    });
}

function pushNotify(status,title,content) {
    new Notify({
        status: status,
        title: title,
        text: content,
        effect: 'fade',
        speed: 300,
        customClass: null,
        customIcon: null,
        showIcon: true,
        showCloseButton: true,
        autoclose: true,
        autotimeout: 3000,
        gap: 20,
        distance: 20,
        type: 'outline',
        position: 'right top'
    })
}

function getBaseUrl(url) {
    return 'http://localhost:8080/athena-banking/' + url;
}

function setupToggle(trigger, target, className = 'active') {
    trigger.addEventListener('click', () => {
        target.classList.toggle(className);
    });
}

const accountTypes = {
    "SAVINGS": "Savings",
    "CHECKING": "Checking",
    "FIXED_DEPOSIT": "Fixed Deposit",
    "INVESTMENT": "Investment",
    "BUSINESS": "Business",
    "RETIREMENT": "Retirement",
}

function formatDateTime(date) {
    if (typeof date === 'string') {
        date = new Date(date);
    }
    const pad = n => n.toString().padStart(2, '0');
    const year = date.getFullYear();
    const month = pad(date.getMonth() + 1);
    const day = pad(date.getDate());
    const hours = pad(date.getHours());
    const minutes = pad(date.getMinutes());
    return `${year}-${month}-${day} ${hours}:${minutes}`;
}

function formatInterestRate(rate) {
    if (typeof rate !== "number") rate = Number(rate);
    if (isNaN(rate)) return "";
    return (rate * 100).toFixed(4).replace(/\.?0+$/, "");
}

export {
    setupPasswordToggle,
    setupToggle,
    pushNotify,
    getBaseUrl,
    accountTypes,
    formatDateTime,
    formatInterestRate
}