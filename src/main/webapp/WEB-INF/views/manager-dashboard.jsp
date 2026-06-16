<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Asset Management System | Manager Dashboard</title>

<!-- External CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">

</head>

<body>

<div id="customAlert" class="alert"></div>

<!-- HEADER -->
<header class="header">
    <div class="logo">
        <img src="${pageContext.request.contextPath}/resources/images/mecer-inter-ed-logo.jpg"
             alt="Logo" class="logo-img">
    </div>
    <span class="header-text">ASSET MANAGEMENT SYSTEM</span>
</header>

<div class="container">

    <!-- SIDEBAR -->
    <aside id="sidebar" class="sidebar">

        <div class="profile-widget">
            <label>Username</label><br>
            <input type="text" value="${manager.username}" readonly><br>

            <label>User Role</label><br>
            <input type="text" value="Manager" readonly>
        </div>

        <ul class="sidebar-menu">
            <li><a href="${pageContext.request.contextPath}/manager/dashboard">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/manager/assets">Assets</a></li>
            <li><a href="${pageContext.request.contextPath}/manager/requests">Requests</a></li>
            <li><a href="${pageContext.request.contextPath}/manager/settings">Settings</a></li>
        </ul>

        <form action="${pageContext.request.contextPath}/logout" method="post">
            <button type="submit" class="logout-btn">Logout →</button>
        </form>

    </aside>

    <!-- MAIN CONTENT -->
    <main id="mainContent" class="main-content">

        <h1>Manager Dashboard</h1>

        <!-- CARDS -->
        <div class="manager-dashboard-cards">

            <div class="card">
                <h2>Total Assets</h2>
                <label>${totalAssets}</label>
            </div>

            <div class="card">
                <h2>Available Assets</h2>
                <label>${availableAssets}</label>
            </div>

            <div class="card">
                <h2>Approved Loans</h2>
                <label>${approvedLoans}</label>
            </div>

            <div class="card">
                <h2>Total Loans</h2>
                <label>${totalLoans}</label>
            </div>

            <div class="card">
                <h2>Loaned Assets</h2>
                <label>${loanedAssets}</label>
            </div>

            <div class="card">
                <h2>Pending Loans</h2>
                <label>${pendingLoans}</label>
            </div>
            <!--
            <div class="card">
                <h2>Overdue Loans</h2>
                <label>${overdueLoans}</label>
            </div> -->

            <div class="card">
                <h2>Retired Assets</h2>
                <label>${retiredAssets}</label>
            </div>

        </div>

        <!-- TABLE -->
        <table class="ManagerDashboard-table">
            <caption><h2>Loan Requests Queue</h2></caption>

            <thead>
                <tr>
                    <th>Asset Name</th>
                    <th>Borrower</th>
                    <th>Request Date</th>
                    <th>Due Date</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>

            <tbody>

                <c:forEach items="${loanRequests}" var="req">
                    <tr>
                        <td>${req.assetName}</td>
                        <td>${req.borrowerName}</td>
                        <td>${req.requestDate}</td>
                        <td>${req.dueDate}</td>
                        <td>${req.status}</td>

                        <td>
                            <form action="${pageContext.request.contextPath}/manager/request/approve/${req.id}"
                                  method="post" style="display:inline;">
                                <button type="submit" class="approve-btn">Approve</button>
                            </form>

                            <form action="${pageContext.request.contextPath}/manager/request/reject/${req.id}"
                                  method="post" style="display:inline;">
                                <button type="submit" class="reject-btn">Reject</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>

    </main>

</div>

</body>
</html>