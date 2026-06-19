<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
</head>
<body>

<!-- ================= HEADER (SHARED ACROSS ALL PAGES) ================= -->
<div>

    <!-- Logo -->
    <div>
        <img src="${pageContext.request.contextPath}/images/mecer-inter-ed-logo.jpg"
             alt="Logo"
             width="120">
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
           <a href="${pageContext.request.contextPath}/settings">Settings</a> |
           <a href="${pageContext.request.contextPath}/loginpage">Log out</a>
        </div>



<!-- ================= DASHBOARD CONTENT ================= -->
<!-- ================= DASHBOARD CONTENT ================= -->
<h3>System Overview</h3>

<div class="admin-dashboard-cards">

    <div class="card">
        <h2>Total Assets</h2>
        <label>${dashboard.totalAssets}</label>
    </div>

    <div class="card">
        <h2>Available Assets</h2>
        <label>${dashboard.availableAssets}</label>
    </div>

    <div class="card">
        <h2>Loaned Assets</h2>
        <label>${dashboard.loanedAssets}</label>
    </div>

    <div class="card">
        <h2>Retired Assets</h2>
        <label>${dashboard.retiredAssets}</label>
    </div>

    <div class="card">
        <h2>Total Users</h2>
        <label>${dashboard.totalUsers}</label>
    </div>

    <div class="card">
        <h2>Pending Loans</h2>
        <label>${dashboard.pendingLoans}</label>
    </div>

</div>



<!-- ================= FOOTER (SHARED ACROSS ALL PAGES) ================= -->
<div>
    <a href="${pageContext.request.contextPath}/terms">Terms & Conditions</a> |
    <a href="${pageContext.request.contextPath}/contact">Contact Us</a>
</div>

</body>
</html>