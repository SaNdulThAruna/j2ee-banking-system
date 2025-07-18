<%--
  Created by IntelliJ IDEA.
  User: sandu
  Date: 7/18/2025
  Time: 12:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ATHENA | TRANSACTION</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/simple-notify/dist/simple-notify.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Lexend+Mega:wght@100..900&display=swap" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Zain:ital,wght@0,200;0,300;0,400;0,700;0,800;0,900;1,300;1,400&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="../css/common.css">
    <link rel="stylesheet" href="../css/admin/transaction.css">
</head>
<body>

<%@include file="../includes/admin-navigation.jsp" %>

<section class="transaction-section">
    <div class="container">
        <div class="transaction-form">
            <div class="form-header">
                <h1>New Transaction</h1>
                <p>Enter the details of the transaction you want to make</p>
            </div>
            <form action="#" id="transactionForm">
                <div class="input-group">
                    <label for="fromAccount">From Account</label>
                    <input id="fromAccount" type="text" name="fromAccount" placeholder="Enter account number"
                           required>
                </div>

                <div class="input-group">
                    <label for="toAccount">To Account</label>
                    <input id="toAccount" type="text" name="toAccount" placeholder="Enter account number"
                           required>
                </div>

                <div class="input-group">
                    <label for="amount">Amount</label>
                    <input id="amount" type="number" name="amount" placeholder="Enter amount" required>
                </div>

                <div class="input-group">
                    <div class="quick-transfer">
                        <div class="qt-container">
                            <label for="quick_transfer" class="qt-label">Quick Transfer</label>
                            <input id="quick_transfer" type="checkbox" name="quick_transfer" class="qt-checkbox">
                        </div>
                        <p class="qt-p">or</p>
                    </div>
                    <label for="date">Date</label>
                    <input id="date" type="datetime-local" name="date">
                </div>

                <div class="input-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description" placeholder="Enter description"></textarea>
                </div>

                <button type="submit">Submit Transaction</button>
            </form>
        </div>
        <div class="transaction-history">
            <div class="transaction-container">
                <div class="history-header">
                    <h2>Transaction History</h2>
                    <p>View and manage all transactions made through the system.</p>
                    <div class="search-container">
                        <form action="" id="filterForm" style="width: 100%">
                            <div class="filter-options">
                                <div class="search-input">
                                    <input name="searchText" type="text" placeholder="Search transactions..." id="search-input">
                                    <button id="search-button" type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
                                </div>
                                <div class="filter-select">
                                    <label for="filterDate">Filter by:</label>
                                    <input type="date" id="filter-date" name="filterDate">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <table class="history-table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>From Account</th>
                        <th>To Account</th>
                        <th>Amount</th>
                        <th>Description</th>
                        <th>Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    <!-- Sample data, replace it with dynamic content -->
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</section>

<script src="https://kit.fontawesome.com/f0f1e7100c.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/simple-notify/dist/simple-notify.min.js"></script>
<script src="../js/nav.js" type="module"></script>
<script src="../js/admin/transaction.js" type="module"></script>
</body>
</html>
