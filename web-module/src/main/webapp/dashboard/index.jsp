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
    <link rel="stylesheet" href="../css/user/user-dashboard.css">
</head>
<body>

<%@include file="../includes/user-navigation.jsp" %>

<main>
    <div class="dashboard-balance">
        <p>Total balance<i class="fa-solid fa-circle-question"></i></p>
        <div class="balance">
            <h1 id="total_amount" class="amount"></h1>
        </div>
    </div>
</main>
<section class="dashboard-container">
    <section class="dashboard-actions">
        <div class="actions">
            <a href="transaction.jsp" class="action-item">
                <i class="fa-solid fa-arrow-up ar1"></i>
                <h2>Transfer</h2>
            </a>
            <a href="history.jsp" class="action-item">
                <i class="fa-solid fa-arrow-up ar2"></i>
                <h2>All Transactions</h2>
            </a>
        </div>
        <div class="accounts">
            <div class="accounts-header">
                <h2>Accounts</h2>
                <a href="accounts.jsp">
                    <i class="fa-solid fa-plus"></i>
                </a>
            </div>
            <div id="account_list" class="account-list">
            </div>
        </div>
    </section>
    <section class="tr-history">
        <div class="tr-history-header">
            <h2>History</h2>
        </div>
        <div id="history_list" class="transaction-history">
        </div>
    </section>
</section>

<script src="https://kit.fontawesome.com/f0f1e7100c.js" crossorigin="anonymous"></script>
<script>
    const customerId = <%= request.getAttribute("customerId") != null ? request.getAttribute("customerId") : "null" %>;
</script>
<script src="../js/nav.js" type="module"></script>
<script src="../js/user/user-dashboard.js" type="module"></script>
</body>
</html>
