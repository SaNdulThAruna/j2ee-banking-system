<%--
  Created by IntelliJ IDEA.
  User: sandu
  Date: 7/17/2025
  Time: 2:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ATHENA | USERS</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/simple-notify/dist/simple-notify.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Lexend+Mega:wght@100..900&display=swap" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Zain:ital,wght@0,200;0,300;0,400;0,700;0,800;0,900;1,300;1,400&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="../css/common.css">
    <link rel="stylesheet" href="../css/admin/users.css">
</head>
<body>

<%@include file="../includes/admin-navigation.jsp" %>

<section class="users-section">
    <div class="container">
        <div class="search-info">
            <h1>Users</h1>
            <p>Manage your users here. You can search, view, and edit user details.</p>
            <div class="search-container">
                <form action="#" style="flex-direction: row;gap: 1rem">
                    <input name="searchText" type="text" style="flex: 1" placeholder="Search users..." id="search-input" required>
                    <button id="search-button" type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
                </form>
            </div>
        </div>
        <div class="users-table-section">
            <table class="users-table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Type</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody id="user-list">
                </tbody>
            </table>

        </div>
    </div>
</section>

<script src="https://cdn.jsdelivr.net/npm/simple-notify/dist/simple-notify.min.js"></script>
<script src="https://kit.fontawesome.com/f0f1e7100c.js" crossorigin="anonymous"></script>
<script src="../js/nav.js" type="module"></script>
<script src="../js/admin/users.js" type="module"></script>
</body>
</html>
