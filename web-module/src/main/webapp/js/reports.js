import {formatDateTime, formatInterestRate, getBaseUrl} from "./util.js";

const loadReportButton = document.getElementById("ld_btn");
const exportPDFButton = document.getElementById("ex_btn");
const reportSelectEl = document.getElementById("reportType");

let currentTableData = []


loadReportButton.addEventListener("click", loadReport);
exportPDFButton.addEventListener("click", exportReportAsPDF);


async function loadReport() {

    const reportType = reportSelectEl.value;

    let url = '';

    if (reportType === "transactions") {
        url = 'reports/transaction';
        }else if (reportType === "balances") {
        url = 'reports/balance';
    }else if (reportType === "interest") {
        url = 'reports/interest';
    }

    const response = await fetch(getBaseUrl(url));
    if (response.ok) {
        const data = await response.json();
        if (data.status === 'success') {
            const report = data.report;
            currentTableData = report;
            renderTable(report,reportType);

        } else {
            console.error("Error loading report data:", data.message);
        }
    } else {
        console.error("Failed to fetch report data.");
    }
}

function renderTable(report, reportType) {

    const container = document.getElementById('reportArea');
    container.innerHTML = "";

    const table = document.createElement("table");
    table.border = "1";
    table.style.width = "100%";
    table.style.borderCollapse = "collapse";

    const thead = document.createElement("thead");
    const tbody = document.createElement("tbody");

    // Set table headers based on a report type
    let headers = [];
    if (reportType === "transactions") {
        headers = ["#", "Description", "Amount", "Date"];
    } else if (reportType === "balances") {
        headers = ["#", "Account Name", "Balance"];
    } else if (reportType === "interest") {
        headers = ["#", "Account", "Interest (%)", "Calculated On"];
    }

    // Render headers
    const headerRow = document.createElement("tr");
    headers.forEach(h => {
        const th = document.createElement("th");
        th.textContent = h;
        th.style.padding = "1rem";
        th.style.color = "black";
        th.style.background = "#ffff00";
        headerRow.appendChild(th);
    });
    thead.appendChild(headerRow);

    // Render body rows
    report.forEach((row, index) => {
        const tr = document.createElement("tr");
        const rowData = getRowData(reportType, row, index);
        rowData.forEach(cell => {
            const td = document.createElement("td");
            td.textContent = cell;
            td.style.padding = "6px";
            tr.appendChild(td);
        });
        tbody.appendChild(tr);
    });

    table.appendChild(thead);
    table.appendChild(tbody);
    container.appendChild(table);
}

// Helper to get row data
function getRowData(type, row, index) {
    if (type === "transactions") {
        return [index + 1, row.description, row.amount, formatDateTime(row.transactionTime)];
    } else if (type === "balances") {
        return [index + 1, row.accountName, row.balance];
    } else if (type === "interest") {
        return [index + 1, row.accountNumber, formatInterestRate(row.interestRate), formatDateTime(row.appliedDate)];
    }
}

// Event listener for report type change
function exportReportAsPDF() {
    const { jsPDF } = window.jspdf;
    const pdf = new jsPDF("p", "mm", "a4");

    const type = document.getElementById("reportType").value;

    const headersMap = {
        transactions: ["#", "Description", "Amount", "Date"],
        balances: ["#", "Account Name", "Balance"],
        interest: ["#", "Account", "Interest (%)", "Calculated On"]
    };

    const rows = currentTableData.map((row, i) => getRowData(type, row, i));

    pdf.setFontSize(16);
    pdf.text("Athena Bank - " + type.replace(/^\w/, c => c.toUpperCase()), 14, 20);

    pdf.autoTable({
        head: [headersMap[type]],
        body: rows,
        startY: 30,
        styles: { fontSize: 10 },
        theme: "striped",
        headStyles: { fillColor: [60, 60, 60] }
    });

    pdf.output("dataurlnewwindow"); // open in new tab (not download)
}

