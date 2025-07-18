<%--
  Created by IntelliJ IDEA.
  User: sandu
  Date: 7/6/2025
  Time: 9:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>ATHENA | DASHBOARD</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Lexend+Mega:wght@100..900&display=swap" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Zain:ital,wght@0,200;0,300;0,400;0,700;0,800;0,900;1,300;1,400&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="../css/common.css">
    <link rel="stylesheet" href="../css/super-admin/dashboard.css">
</head>
<body>

<%@include file="../includes/super-admin-navigation.jsp"%>

<section class="dashboard-section">

    <div class="dashboard-header">
        <div class="dashboard-header-item">
            <p>Total Accounts</p>
            <h1 id="total_account"></h1>
        </div>
        <div class="dashboard-header-item">
            <p>Total Transactions</p>
            <h1 id="total_transaction"></h1>
        </div>
        <div class="dashboard-header-item">
            <p>Active Users</p>
            <h1 id="total_users"></h1>
        </div>
    </div>

    <div class="dashboard-body">

        <div class="dashboard-container">
            <div class="dashboard-action">
                <a href="../super-admin/users.jsp" class="dashboard-link">
                    <i class="fa-solid fa-user"></i>
                    <h3>Manage All Users</h3>
                </a>
                <a href="../super-admin/admins.jsp" class="dashboard-link">
                    <i class="fa-solid fa-user-tie"></i>
                    <h3>Register Admins</h3>
                </a>
            </div>
            <div class="dashboard-action">
                <a href="../super-admin/report.jsp" class="dashboard-link">
                    <i class="fa-solid fa-chart-line"></i>
                    <h3>View Reports</h3>
                </a>
            </div>
        </div>

        <div class="interests">
            <div class="interests-container">
                <h2>Today Interest Log</h2>
                <div id="interest_list">
                </div>
            </div>
        </div>
    </div>

</section>

<script src="https://kit.fontawesome.com/f0f1e7100c.js" crossorigin="anonymous"></script>
<script src="../js/nav.js" type="module"></script>
<script src="../js/super-admin/dashboard.js" type="module"></script>
</body>
</html>
