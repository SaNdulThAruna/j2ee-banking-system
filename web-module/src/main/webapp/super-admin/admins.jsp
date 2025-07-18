<%--
  Created by IntelliJ IDEA.
  User: sandu
  Date: 7/17/2025
  Time: 2:39 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ATHENA | ADMINS</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/simple-notify/dist/simple-notify.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Lexend+Mega:wght@100..900&display=swap" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Zain:ital,wght@0,200;0,300;0,400;0,700;0,800;0,900;1,300;1,400&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="../css/common.css">
    <link rel="stylesheet" href="../css/super-admin/accounts.css">
</head>
<body>

<%@ include file="../includes/super-admin-navigation.jsp" %>

<section class="accounts-section">
    <div class="container">
        <div class="accounts-form">
            <h2>Manage Accounts</h2>
            <form action="#">
                <div>
                    <h3>Create New Account</h3>
                    <p>Fill in the details below to create a new account.</p>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <label for="full-name">Full Name:</label>
                        <input type="text" id="full-name" name="fullName" required>
                    </div>
                    <div class="input-group">
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" required>
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <label for="username">Username:</label>
                        <input type="text" id="username" name="username" required>
                    </div>
                    <div class="input-group">
                        <label for="password">Password:</label>
                        <input type="password" id="password" name="password" required>
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <label for="address">Address:</label>
                        <input type="text" id="address" name="address" required>
                    </div>
                    <div class="input-group">
                        <label for="phone">Phone:</label>
                        <input type="tel" id="phone" name="phone" required>
                    </div>
                </div>

                <button type="submit">Create Account</button>
            </form>
        </div>
        <div class="accounts-table-section">
            <div>
                <h2>Existing Accounts</h2>
                <p>View and manage existing accounts below.</p>
                <div class="search-container">
                    <input type="text" placeholder="Search accounts..." id="search-input">
                    <button id="search-button"><i class="fa-solid fa-magnifying-glass"></i></button>
                </div>
            </div>

            <table class="accounts-table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Full Name</th>
                    <th>Email</th>
                    <th>Username</th>
                    <th>User Type</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <!-- Sample data, replace with dynamic content -->
                </tbody>
            </table>
        </div>
    </div>
</section>

<script src="https://kit.fontawesome.com/f0f1e7100c.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/simple-notify/dist/simple-notify.min.js"></script>
<script src="../js/nav.js" type="module"></script>
<script src="../js/super-admin/accounts.js" type="module"></script>
</body>
</html>
