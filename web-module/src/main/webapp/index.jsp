<%--
  Created by IntelliJ IDEA.
  User: sandu
  Date: 7/6/2025
  Time: 9:30 PM
  To change this template use File | Settings | File Templates.
--%>

<%

    if (request.getSession().getAttribute("user") != null) {
        if (request.isUserInRole("SUPER_ADMIN")) {
            response.sendRedirect(request.getContextPath() + "/super-admin/");
        } else if (request.isUserInRole("ADMIN")) {
            response.sendRedirect(request.getContextPath() + "/admin/");
        } else if (request.isUserInRole("USER")) {
            response.sendRedirect(request.getContextPath() + "/dashboard/");
        }
    }

%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ATHENA | SignIn</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Lexend+Mega:wght@100..900&display=swap" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Zain:ital,wght@0,200;0,300;0,400;0,700;0,800;0,900;1,300;1,400&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="css/user/index.css">
</head>
<body>
<div class="img-container">
    <span class="logo">ATHENA</span>
</div>
<div class="form-container">
    <div class="form-wrapper">
        <h1>ATHENA BANK.</h1>
        <h2>Welcome back</h2>
        <p>Log in to access your account and stay connected</p>
        <form action="signin" method="post">
            <div>
                <label class="la1" for="username">Username</label>
                <div class="input-container">
                    <input id="username" type="text" name="username" placeholder="Enter your username" required>
                </div>
            </div>
            <div>
                <label for="password">Password</label>
                <div class="password-container">
                    <input id="password" type="password" name="password" placeholder="Enter your password" required>
                    <i class="fa-solid fa-eye" id="visible"></i>
                </div>
            </div>
            <div class="button-container">
                <button type="submit">Log in</button>
                <a class="register" href="register.jsp">Don't have an account? <span>Get Started</span></a>
            </div>
        </form>
    </div>
</div>

<script src="https://kit.fontawesome.com/f0f1e7100c.js" crossorigin="anonymous"></script>
<script src="js/user/index.js" type="module"></script>
</body>
</html>
