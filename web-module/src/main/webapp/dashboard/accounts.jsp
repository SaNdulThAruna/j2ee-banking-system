<%--
  Created by IntelliJ IDEA.
  User: sandu
  Date: 7/15/2025
  Time: 9:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ATHENA | ACCOUNTS</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/simple-notify/dist/simple-notify.css" />
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Lexend+Mega:wght@100..900&display=swap" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Zain:ital,wght@0,200;0,300;0,400;0,700;0,800;0,900;1,300;1,400&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="../css/common.css">
    <link rel="stylesheet" href="../css/user/account.css">
</head>
<body>

<%@include file="../includes/user-navigation.jsp"%>


<section class="account-section">
    <div class="container">

        <div class="account-header">
            <h1>Account Details</h1>
            <p>Manage your account information and settings</p>

            <form action="#">

                <div class="input-group">
                    <label for="accountName">Account name</label>
                    <input id="accountName" type="text" name="accountName" placeholder="Enter your account name"
                           required>
                </div>

                <div class="input-group">
                    <label for="accountType">Account Type</label>
                    <select name="accountType" id="accountType" required>
                        <option value="" disabled selected>Select account type</option>
                    </select>
                </div>

                <button type="submit">Create an Account</button>

            </form>

        </div>
        <div class="account-list-container">
            <h2>My Accounts</h2>
            <div id="account_list" class="account-list">
                <!-- Account items will be dynamically inserted here -->
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
<script src="../js/user/account.js" type="module"></script>
</body>
</html>