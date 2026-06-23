<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Asset Management System | Admin Dashboard</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboards.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>



    <container id = "container" class="container">

        <nav class = "navMenu">

            



                <!-- Logo -->
                <div class="container-logo">
                    <img src="${pageContext.request.contextPath}/images/img_1.png"
                        alt="Logo"
                        class="login-logo"
                        width="100">
                </div>

<!-- ================= NAVIGATION (SHARED) ================= -->




            <ProfileWidget class="container-userinfo">
                <!-- User Info -->
                <p>
                Username: ${username} <br>
                Role: ${userRole}
                </p>
            </ProfileWidget>


            <div class="container-links">
                <!-- ================= NAVIGATION (SHARED) ================= -->
            <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
                       <a href="${pageContext.request.contextPath}/admin/assets">Assets</a>
                       <a href="${pageContext.request.contextPath}/admin/users">Users</a>
                       <a href="${pageContext.request.contextPath}/admin/loans">Loans</a>
                       <a href="${pageContext.request.contextPath}/admin/auditlog">Audit Log</a>
                        <form action="${pageContext.request.contextPath}/logout"
                              method="post">

                    <button type="submit">
                        Log Out
                    </button>

                </form>
            </div>



        </nav>

        <main id = "mainContent" class="main-content">

            <!-- ================= DASHBOARD CONTENT ================= -->
            <h3>System Overview</h3>

            <div class="dashboard-cards">

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
        </main>
        

    </container>



<!-- ================= FOOTER (SHARED ACROSS ALL PAGES) ================= -->
<footer>
    <a href="${pageContext.request.contextPath}/terms">Terms & Conditions</a> |
    <a href="${pageContext.request.contextPath}/contact">Contact Us</a>
</footer>

<script src="${pageContext.request.contextPath}/js/adminDashboard.js"></script>
</body>
</html>