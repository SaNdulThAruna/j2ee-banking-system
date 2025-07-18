<%--
  Created by IntelliJ IDEA.
  User: sandu
  Date: 7/15/2025
  Time: 3:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ATHENA | PROFILE</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/simple-notify/dist/simple-notify.css" />
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Lexend+Mega:wght@100..900&display=swap" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Zain:ital,wght@0,200;0,300;0,400;0,700;0,800;0,900;1,300;1,400&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="../css/common.css">
    <link rel="stylesheet" href="../css/user/profile.css">
</head>
<body>

<%@include file="../includes/user-navigation.jsp"%>

<section class="profile-section">
    <div class="container">
        <div class="profile-header">
            <h1>Profile</h1>
            <p>Manage your account details</p>
        </div>
        <div class="profile-details">
            <form action="#">
                <div class="input-group">
                    <label for="fullName">Full Name:</label>
                    <input type="text" id="fullName" value="John Doe"/>
                </div>
                <div class="input-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" value="john@gmail.com" disabled/>
                </div>
                <div class="input-group">
                    <label for="username">Username:</label>
                    <input type="text" id="username" value="john"/>
                </div>
                <div class="input-group">
                    <label for="phone">Phone:</label>
                    <input type="tel" id="phone" value="+123456789"/>
                </div>
                <div class="input-group">
                    <label for="address">Address:</label>
                    <input type="text" id="address" value="123 Main St, City, Country"/>
                </div>
                <div class="button-container">
                    <button type="submit" class="update-btn">Update Profile</button>
                </div>
            </form>
        </div>
    </div>
</section>


<script>
    const customerId = <%= request.getAttribute("customerId") != null ? request.getAttribute("customerId") : "null" %>;
</script>
<script src="https://kit.fontawesome.com/f0f1e7100c.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/simple-notify/dist/simple-notify.min.js"></script>
<script src="../js/nav.js" type="module"></script>
<script src="../js/user/profile.js" type="module"></script>
</body>
</html>
