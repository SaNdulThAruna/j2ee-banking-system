<%--
  Created by IntelliJ IDEA.
  User: sandu
  Date: 7/7/2025
  Time: 12:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ATHENA | Register</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/simple-notify/dist/simple-notify.css" />
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Lexend+Mega:wght@100..900&display=swap" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Zain:ital,wght@0,200;0,300;0,400;0,700;0,800;0,900;1,300;1,400&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="css/user/register.css">
</head>
<body>
<div class="img-container">
    <span class="logo">ATHENA</span>
</div>
<div class="form-container">
    <div class="form-wrapper">
        <h1>ATHENA BANK.</h1>
        <h2>Welcome!</h2>
        <p>Register to create an account and start your journey with us</p>
        <form action="#">
            <div>
                <label for="fullName">Full Name</label>
                <div class="input-container">
                    <input id="fullName" type="text" name="fullName" placeholder="Enter your full name" required>
                </div>
            </div>
            <div>
                <label for="email">Email</label>
                <div class="input-container">
                    <input id="email" type="email" name="email" placeholder="Enter your email" required>
                </div>
            </div>
            <div class="username-password-container">
                <div>
                    <label for="username">Username</label>
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
            </div>
            <div>
                <label for="phone">Phone</label>
                <div class="input-container">
                    <input id="phone" type="tel" name="phone" placeholder="Enter your phone" required>
                </div>
            </div>
            <div>
                <label for="address">Address</label>
                <div class="input-container">
                    <input id="address" type="text" name="address" placeholder="Enter your address" required>
                </div>
            </div>
            <div class="button-container">
                <button type="submit">Create an Account</button>
                <a class="signin" href="index.jsp">Already have an account? <span>Sign in</span></a>
            </div>
        </form>
    </div>
</div>

<script src="https://kit.fontawesome.com/f0f1e7100c.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/simple-notify/dist/simple-notify.min.js"></script>
<script src="js/user/register.js" type="module"></script>
</body>
</html>
