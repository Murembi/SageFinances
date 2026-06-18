<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>MECER INTER-ED | ASSET MANAGEMENT SYSTEM | Admin Dashboard</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
<link rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

</head>

<body>

<div id="customAlert" class="alert"></div>

<!-- HEADER -->
<header class="header">
    <div class="logo">
        <i class="fa-brands fa-first-order-alt"></i>

        <img src="${pageContext.request.contextPath}/images/mecer-inter-ed-logo.jpg"
             alt="MECER INTER-ED Logo"
             class="logo-img">
    </div>

    <span class="header-text">ASSET MANAGEMENT SYSTEM</span>
</header>

<div class="container">

    <!-- SIDEBAR -->
    <aside id="sidebar" class="sidebar">

        <div class="profile-widget">
            <label>Username</label><br>
            <input type="text" value="${username}" readonly><br>

            <label>User Role</label><br>
            <input type="text" value="${userRole}" readonly>
        </div>

        <ul class="sidebar-menu">
            <li><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/loans">Loans</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/assets">Assets</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/users">Users</a></li>

        </ul>

        <form action="${pageContext.request.contextPath}/logout" method="post">
            <button type="submit" class="logout-btn">
                Logout <span>&#8594;</span>
            </button>
        </form>

    </aside>

    <!-- MAIN CONTENT -->
    <main class="main-content">

        <h1>Admin Dashboard</h1>

        <!-- DASHBOARD CARDS -->
        <div class="dashboard-cards">

            <div class="card">
                <h2>Total Assets</h2>
                <p>${dashboard.totalAssets}</p>
            </div>

            <div class="card">
                <h2>Available Assets</h2>
                <p>${dashboard.availableAssets}</p>
            </div>

            <div class="card">
                <h2>Loaned Assets</h2>
                <p>${dashboard.loanedAssets}</p>
            </div>

            <div class="card">
                <h2>Retired Assets</h2>
                <p>${dashboard.retiredAssets}</p>
            </div>

            <div class="card">
                <h2>Total Users</h2>
                <p>${dashboard.totalUsers}</p>
            </div>

            <div class="card">
                <h2>Pending Loan Requests</h2>
                <p>${dashboard.pendingLoans}</p>
            </div>

        </div>

        <!-- ACTION BUTTONS -->
        <div class="actions">

            <form action="${pageContext.request.contextPath}/addAsset" method="get">
                <button type="submit">Add Asset</button>
            </form>

            <form action="${pageContext.request.contextPath}/createUser" method="get">
                <button type="submit">Create User</button>
            </form>

            <form action="${pageContext.request.contextPath}/manageLoans" method="get">
                <button type="submit">Manage Loans</button>
            </form>

        </div>

        <!-- AUDIT LOG TABLE -->
        <h2>Recent Activity</h2>

        <table class="auditLogTable">

            <thead>
            <tr>
                <th>User ID</th>
                <th>Action</th>
                <th>Entity</th>
                <th>Entity ID</th>
                <th>Timestamp</th>
                <th>Old Value</th>
                <th>New Value</th>
            </tr>
            </thead>

            <tbody>

            <c:forEach var="log" items="${auditLogs}">
                <tr>
                    <td>
                        ${log.userId != null ? log.userId : "SYSTEM"}
                    </td>
                    <td>${log.action}</td>
                    <td>${log.entityType}</td>
                    <td>${log.entityId}</td>
                    <td>${log.timestamp}</td>
                    <td>${log.oldValue}</td>
                    <td>${log.newValue}</td>
                </tr>
            </c:forEach>

            </tbody>

        </table>

    </main>

</div>

</body>
</html>