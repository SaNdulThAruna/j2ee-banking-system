<%--
  Created by IntelliJ IDEA.
  User: sandu
  Date: 7/18/2025
  Time: 3:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ATHENA | ACCOUNTS</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/simple-notify/dist/simple-notify.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Lexend+Mega:wght@100..900&display=swap" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Zain:ital,wght@0,200;0,300;0,400;0,700;0,800;0,900;1,300;1,400&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="../css/common.css">
    <link rel="stylesheet" href="../css/admin/accounts.css">
</head>
<body>

<%@include file="../includes/admin-navigation.jsp" %>

<section class="accounts-section">
    <div class="container">
        <div class="accounts-form">
            <h2>Manage Accounts</h2>
            <form action="#" id="create-account-form">
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
                        <label for="address">Address:</label>
                        <input type="text" id="address" name="address" required>
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <label for="phone">Phone:</label>
                        <input type="tel" id="phone" name="phone" required>
                    </div>
                    <div class="input-group">
                        <label for="account-type">Account Type:</label>
                        <select id="account-type" name="accountType" required>
                            <option value="" disabled selected>Select Type</option>
                        </select>
                    </div>

                </div>

                <div class="form-group">
                    <div class="input-group">
                        <label for="account-name">Account Name:</label>
                        <input type="text" id="account-name" name="accountName" required>
                    </div>
                    <div class="input-group">
                        <label for="balance">Initial Balance:</label>
                        <input type="number" id="balance" name="balance" required>
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
                    <form action="#" id="search-form">
                        <div class="input-group">
                            <label for="search-input">Search:</label>
                            <input name="searchText" type="text" placeholder="Search accounts..." id="search-input">
                        </div>
                        <p>Filter By</p>
                        <div class="form-group">
                            <div class="input-group">
                                <label for="at">Account Type:</label>
                                <select name="accountType" id="at">
                                    <option value="" disabled selected>Select Account Type</option>
                                </select>
                            </div>
                            <div class="input-group">
                                <label for="status">Account Status:</label>
                                <select name="accountStatus" id="status">
                                    <option value="" disabled selected>Select Account Status</option>
                                    <option value="active">Active</option>
                                    <option value="inactive">Inactive</option>
                                </select>
                            </div>
                        </div>
                        <button id="search-button" type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
                    </form>
                </div>
            </div>

            <table class="accounts-table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Full Name</th>
                    <th>Email</th>
                    <th>Username</th>
                    <th>Account Name</th>
                    <th>Account Type</th>
                    <th>Balance</th>
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
</body>
<script src="../js/admin/accounts.js" type="module"></script>
</html>
