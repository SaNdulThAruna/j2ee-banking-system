<%--
  Created by IntelliJ IDEA.
  User: sandu
  Date: 7/6/2025
  Time: 9:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ATHENA | DASHBOARD</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Lexend+Mega:wght@100..900&display=swap" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Zain:ital,wght@0,200;0,300;0,400;0,700;0,800;0,900;1,300;1,400&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="../css/common.css">
    <link rel="stylesheet" href="../css/admin/dashboard.css">
</head>
<body>

<%@include file="../includes/admin-navigation.jsp"%>

<section class="dashboard-section">
    <div class="dashboard-container">
        <div class="dashboard-header">
            <div class="dashboard-title">
                <p>Total Accounts</p>
                <h1 id="ta"></h1>
            </div>
            <div class="dashboard-title">
                <p>Total Transactions</p>
                <h1 id="tt"></h1>
            </div>
            <div class="dashboard-title">
                <p>Active Customers</p>
                <h1 id="ac"></h1>
            </div>
        </div>
        <div class="dashboard-content">
            <div class="dashboard-actions">
                <div class="actions">
                    <a href="#" class="action-item">
                        <i class="fa-solid fa-arrow-up ar1"></i>
                        <h2>Transaction</h2>
                    </a>
                    <a href="#" class="action-item">
                        <i class="fa-solid fa-user"></i>
                        <h2>Users</h2>
                    </a>
                </div>
                <div class="actions">
                    <a href="#" class="action-item">
                        <i class="fa-solid fa-file-invoice-dollar ar3"></i>
                        <h2>Accounts</h2>
                    </a>
                    <a href="#" class="action-item">
                        <i class="fa-solid fa-chart-line"></i>
                        <h2>Reports</h2>
                    </a>
                </div>
            </div>
            <div class="accounts">
                <div class="accounts-container">
                    <h2>Daily Transactions Log</h2>
                    <div class="transaction-list">
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<script src="https://kit.fontawesome.com/f0f1e7100c.js" crossorigin="anonymous"></script>
<script src="../js/nav.js" type="module"></script>
<script src="../js/admin/dashboard.js" type="module"></script>
</body>
</html>
