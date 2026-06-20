<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
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
           <a href="${pageContext.request.contextPath}/settings">Settings</a> |
           <a href="${pageContext.request.contextPath}/loginpage">Log out</a>
        </div>



<!-- ================= DASHBOARD CONTENT ================= -->
<h3>System Overview</h3>

<table border="1">
    <tr>
        <td>Total Assets</td>
        <td>${dashboard.totalAssets}</td>
    </tr>
    <tr>
        <td>Available Assets</td>
        <td>${dashboard.availableAssets}</td>
    </tr>
    <tr>
        <td>Loaned Assets</td>
        <td>${dashboard.loanedAssets}</td>
    </tr>
    <tr>
        <td>Retired Assets</td>
        <td>${dashboard.retiredAssets}</td>
    </tr>
    <tr>
        <td>Total Users</td>
        <td>${dashboard.totalUsers}</td>
    </tr>
    <tr>
        <td>Pending Loans</td>
        <td>${dashboard.pendingLoans}</td>
    </tr>
</table>



<!-- ================= FOOTER (SHARED ACROSS ALL PAGES) ================= -->
<div>
    <a href="${pageContext.request.contextPath}/terms">Terms & Conditions</a> |
    <a href="${pageContext.request.contextPath}/contact">Contact Us</a>
</div>

</body>
</html>