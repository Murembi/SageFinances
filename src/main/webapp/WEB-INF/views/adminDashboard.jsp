<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>

<header class="header">
    <div class="logo">
        <img src="${pageContext.request.contextPath}/images/sage.png"
             alt="Logo"
             class="dashboard-logo"
             width="100">
    </div>

    <span class="header-text">ASSET MANAGEMENT SYSTEM</span>
</header>

<div class="container">

    <aside class="sidebar">

        <ul class="sidebar-menu">
            <li>
                <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/admin/assets">Assets</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/admin/users">Users</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/admin/loans">Loans</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/admin/auditlog">Audit Log</a>
            </li>
        </ul>

        <form action="${pageContext.request.contextPath}/logout" method="post">
            <button type="submit" class="logout-btn">Logout →</button>
        </form>

    </aside>

    <main class="main-content">

        <section class="dashboard-header">
            <h1>Admin Dashboard</h1>
            <p>Username: ${username} | Role: ${userRole}</p>
        </section>

        <section class="manager-dashboard-cards">
            <div class="dashboard-grid">

                <div class="stat-card">
                    <h2>Total Assets</h2>
                    <p>${dashboard.totalAssets}</p>
                </div>

                <div class="stat-card">
                    <h2>Available Assets</h2>
                    <p>${dashboard.availableAssets}</p>
                </div>

                <div class="stat-card">
                    <h2>Loaned Assets</h2>
                    <p>${dashboard.loanedAssets}</p>
                </div>

                <div class="stat-card">
                    <h2>Retired Assets</h2>
                    <p>${dashboard.retiredAssets}</p>
                </div>

                <div class="stat-card">
                    <h2>Total Users</h2>
                    <p>${dashboard.totalUsers}</p>
                </div>

                <div class="stat-card">
                    <h2>Pending Loans</h2>
                    <p>${dashboard.pendingLoans}</p>
                </div>

            </div>
        </section>

    </main>

</div>

<script src="${pageContext.request.contextPath}/js/adminDashboard.js"></script>

</body>
</html>