<%--
  Created by IntelliJ IDEA.
  User: sandu
  Date: 7/16/2025
  Time: 3:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ATHENA | HISTORY</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/simple-notify/dist/simple-notify.css" />
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Lexend+Mega:wght@100..900&display=swap" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Zain:ital,wght@0,200;0,300;0,400;0,700;0,800;0,900;1,300;1,400&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="../css/common.css">
    <link rel="stylesheet" href="../css/user/history.css">
</head>
<body>

<%@include file="../includes/user-navigation.jsp"%>

<section class="history-section">

    <div class="container">

        <div class="history-header">
            <h1>History</h1>
            <p>View your transaction history and account activities.</p>

            <div class="filters">
                <form action="#" style="width: 100%">
                    <label for="filterDate">Filter By Date</label>
                    <input type="date" name="filterDate" id="filterDate" required>
                    <button class="filter-btn" id="filterBtn" type="submit">Filter</button>
                </form>
            </div>
        </div>

        <div class="history">
            <table class="history-table">
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Type</th>
                    <th>Amount</th>
                    <th>Status</th>
                    <th>Description</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>

    </div>

</section>

<script>
    const customerId = <%= request.getAttribute("customerId") != null ? request.getAttribute("customerId") : "null" %>;
</script>
<script src="https://kit.fontawesome.com/f0f1e7100c.js" crossorigin="anonymous"></script>
<script src="https://kit.fontawesome.com/f0f1e7100c.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/simple-notify/dist/simple-notify.min.js"></script>
<script src="../js/nav.js" type="module"></script>
<script src="../js/user/history.js" type="module"></script>
</body>
</html>
