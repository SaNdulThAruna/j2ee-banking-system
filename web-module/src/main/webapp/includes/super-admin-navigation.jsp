<%--
  Created by IntelliJ IDEA.
  User: sandu
  Date: 7/16/2025
  Time: 3:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%
    if (request.getSession().getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
    <div class="logo-container">
        <span class="logo">ATHENA</span>
    </div>
    <div class="menu-laptop">
        <nav>
            <ul>
                <li><i class="fa-solid fa-table"></i><a href="${pageContext.request.contextPath}/super-admin/index.jsp">Dashboard</a></li>
                <li><i class="fa-solid fa-user"></i><a href="users.jsp">Users</a></li>
                <li><i class="fa-solid fa-user-tie"></i><a href="../super-admin/admins.jsp">Admins</a></li>
                <li><i class="fa-solid fa-chart-line"></i><a href="../super-admin/report.jsp">Reports</a></li>
            </ul>
        </nav>
        <div class="user-info">
            <span class="user-name">Welcome, <%=request.getSession().getAttribute("user")%></span>
            <a href="/athena-banking/logout" class="logout">Log out</a>
        </div>
    </div>
    <i class="fa-solid fa-bars hamburger"></i>
</header>
<section class="mobile-nav active">
    <div class="close-menu">
        <i class="fa-solid fa-xmark close"></i>
    </div>
    <div class="m-user-info">
        <span class=m-user-name">Hi, <%=request.getSession().getAttribute("user")%></span>
    </div>
    <nav>
        <ul>
            <li><i class="fa-solid fa-table"></i><a href="${pageContext.request.contextPath}/super-admin/index.jsp">Dashboard</a></li>
            <li><i class="fa-solid fa-user"></i><a href="users.jsp">Users</a></li>
            <li><i class="fa-solid fa-user-tie"></i><a href="../super-admin/admins.jsp">Admins</a></li>
            <li><i class="fa-solid fa-chart-line"></i><a href="../super-admin/report.jsp">Reports</a></li>
            <li>
                <i class="fa-solid fa-right-from-bracket"></i><a href="/athena-banking/logout" class="logout">Log out</a>
            </li>
        </ul>
    </nav>
</section>
