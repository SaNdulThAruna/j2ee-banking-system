<%--
  Created by IntelliJ IDEA.
  User: sandu
  Date: 7/17/2025
  Time: 4:13 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ATHENA | REPORTS</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/simple-notify/dist/simple-notify.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Lexend+Mega:wght@100..900&display=swap" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Zain:ital,wght@0,200;0,300;0,400;0,700;0,800;0,900;1,300;1,400&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="../css/common.css">
    <link rel="stylesheet" href="../css/super-admin/reports.css">
</head>
<body>

<%@include file="../includes/admin-navigation.jsp"%>

<section class="reports-section">
    <div class="container">
        <div class="report-header">
            <h1>Banking Reports</h1>
            <p>Generate and view various banking reports including daily transactions, account balances, and interest
                summaries.</p>

            <div class="report-controls">
                <div class="report-control">
                    <label for="reportType">Choose Report:</label>
                    <select id="reportType">
                        <option value="transactions">Daily Transactions</option>
                        <option value="balances">Account Balances</option>
                        <option value="interest">Interest Summary</option>
                    </select>
                </div>
                <button id="ld_btn">Load Report</button>
                <button id="ex_btn">Export as PDF</button>
            </div>
        </div>

        <div id="reportArea">
        </div>
    </div>
</section>

<!-- jsPDF for PDF export -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.28/jspdf.plugin.autotable.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/simple-notify/dist/simple-notify.min.js"></script>
<script src="https://kit.fontawesome.com/f0f1e7100c.js" crossorigin="anonymous"></script>
<script src="../js/nav.js" type="module"></script>
<script src="../js/reports.js" type="module"></script>
</body>
</html>
