<%--
  Created by IntelliJ IDEA.
  User: sandu
  Date: 7/16/2025
  Time: 3:42 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ATHENA | TRANSACTION</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/simple-notify/dist/simple-notify.css" />
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Lexend+Mega:wght@100..900&display=swap" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Zain:ital,wght@0,200;0,300;0,400;0,700;0,800;0,900;1,300;1,400&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="../css/common.css">
    <link rel="stylesheet" href="../css/user/transaction.css">
</head>
<body>

<%@include file="../includes/user-navigation.jsp"%>


<section class="transaction-section">
    <div class="container">
        <div class="transaction-form">
            <div class="form-header">
                <h1>New Transaction</h1>
                <p>Enter the details of the transaction you want to make</p>
            </div>
            <form action="#">
                <div class="input-group">
                    <label for="account">Select Your account</label>
                    <select id="account" name="account" required>
                        <option value="" disabled selected>Select account</option>
                        <%-- Assuming accounts is a list of account objects passed from the server --%>
                    </select>
                </div>

                <div class="input-group">
                    <label for="accountNumber">Account Number</label>
                    <input id="accountNumber" type="text" name="accountNumber" placeholder="Enter account number"
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
                    <input id="date" type="datetime-local" name="date" required>
                </div>

                <div class="input-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description" placeholder="Enter description" required></textarea>
                </div>

                <button type="submit">Submit Transaction</button>
            </form>
        </div>
        <div class="transaction-history">
            <h2>Transaction History</h2>
            <div class="history-list">
            </div>
        </div>
    </div>
</section>

<script>
    const customerId = <%= request.getAttribute("customerId") != null ? request.getAttribute("customerId") : "null" %>;
</script>
<script src="https://kit.fontawesome.com/f0f1e7100c.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/simple-notify/dist/simple-notify.min.js"></script>
<script src="../js/nav.js" type="module"></script>
<script src="../js/user/transaction.js" type="module"></script>
</body>
</html>
