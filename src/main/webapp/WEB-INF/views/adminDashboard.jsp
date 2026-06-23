<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Asset Management System | Admin Dashboard</title>

    <!-- SAME CSS AS ASSET PAGE -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>

<div class="container">

    <!-- ================= SIDEBAR ================= -->
    <aside class="sidebar">

        <div class="profile-widget">

            <!-- Logo -->
            <div class="logo-section">
                <img src="${pageContext.request.contextPath}/images/img_1.png"
                     alt="Logo"
                     class="logo-img">
            </div>

            <!-- User Info -->
            <div class="user-info">
                <p>
                    Username: ${username} <br>
                    Role: ${userRole}
                </p>
            </div>

        </div>

        <!-- NAVIGATION (same styling system as asset page navbar links) -->
        <nav class="nav-links">
            <ul>
                <li><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/assets">Assets</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/users">Users</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/loans">Loans</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/auditlog">Audit Log</a></li>
            </ul>
        </nav>

        <!-- LOGOUT -->
        <div>
            <form action="${pageContext.request.contextPath}/logout" method="post">
                <button type="submit" class="btn btn-danger logout-btn">
                    Log Out
                </button>
            </form>
        </div>

    </aside>


    <!-- ================= MAIN CONTENT ================= -->
    <main class="main-content">

        <section class="card">

            <h2 class="page-title">System Overview</h2>

            <div class="dashboard-cards">

                <div class="stat-card">
                    <h3>Total Assets</h3>
                    <label>${dashboard.totalAssets}</label>
                </div>

                <div class="stat-card">
                    <h3>Available Assets</h3>
                    <label>${dashboard.availableAssets}</label>
                </div>

                <div class="stat-card">
                    <h3>Loaned Assets</h3>
                    <label>${dashboard.loanedAssets}</label>
                </div>

                <div class="stat-card">
                    <h3>Retired Assets</h3>
                    <label>${dashboard.retiredAssets}</label>
                </div>

                <div class="stat-card">
                    <h3>Total Users</h3>
                    <label>${dashboard.totalUsers}</label>
                </div>

                <div class="stat-card">
                    <h3>Pending Loans</h3>
                    <label>${dashboard.pendingLoans}</label>
                </div>

            </div>

        </section>

    </main>

</div>


<!-- ================= FOOTER ================= -->
<footer class="footer">
    <a href="${pageContext.request.contextPath}/terms">Terms & Conditions</a>
    <a href="${pageContext.request.contextPath}/contact">Contact Us</a>
</footer>


<script src="${pageContext.request.contextPath}/js/adminDashboard.js"></script>
</body>
</html>