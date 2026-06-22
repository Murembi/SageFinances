<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<!-- ================= HEADER (SHARED ACROSS ALL PAGES) ================= -->
<div>

    <!-- Logo -->
    <div>
        <img src="${pageContext.request.contextPath}/images/img_1.png"
             alt="Logo"
             class="login-logo"
             width="100">
    </div>

    <!-- System Title -->
    <h2>Admin Dashboard</h2>

    <!-- User Info -->
    <p>
        Username: ${username} <br>
        Role: ${userRole}
    </p>

</div>



<!-- ================= NAVIGATION (SHARED) ================= -->
        <div>
           <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a> |
           <a href="${pageContext.request.contextPath}/admin/assets">Assets</a> |
           <a href="${pageContext.request.contextPath}/admin/users">Users</a> |
           <a href="${pageContext.request.contextPath}/admin/loans">Loans</a> |
           <a href="${pageContext.request.contextPath}/admin/auditlog">Audit Log</a> |
            <form action="${pageContext.request.contextPath}/logout"
                  method="post">

                <button type="submit">
                    Log Out
                </button>

            </form>
        </div>



<!-- ================= DASHBOARD CONTENT ================= -->
<h3>System Overview</h3>

<div class="dashboard-grid">

    <div class="stat-card">
        <h3>Total Assets</h3>
        <p>${dashboard.totalAssets}</p>
    </div>

    <div class="stat-card">
        <h3>Available Assets</h3>
        <p>${dashboard.availableAssets}</p>
    </div>

    <div class="stat-card">
        <h3>Loaned Assets</h3>
        <p>${dashboard.loanedAssets}</p>
    </div>

    <div class="stat-card">
        <h3>Retired Assets</h3>
        <p>${dashboard.retiredAssets}</p>
    </div>

    <div class="stat-card">
        <h3>Total Users</h3>
        <p>${dashboard.totalUsers}</p>
    </div>

    <div class="stat-card">
        <h3>Pending Loans</h3>
        <p>${dashboard.pendingLoans}</p>
    </div>

</div>



<!-- ================= FOOTER (SHARED ACROSS ALL PAGES) ================= -->
<div>
    <a href="${pageContext.request.contextPath}/terms">Terms & Conditions</a> |
    <a href="${pageContext.request.contextPath}/contact">Contact Us</a>
</div>

<script src="${pageContext.request.contextPath}/js/adminDashboard.js"></script>
</body>
</html>