<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manager Dashboard</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>

<div id="customAlert" class="alert"></div>

<!-- HEADER -->
<header class="header">
    <div class="logo">
        <img src="${pageContext.request.contextPath}/images/img.png"
             alt="Logo"
             class="login-logo"
             width="100">
    </div>

    <span class="header-text">ASSET MANAGEMENT SYSTEM</span>
</header>

<div class="container">

    <!-- SIDEBAR -->
    <aside id="sidebar" class="sidebar">

        <ul class="sidebar-menu">
            <li>
                <a href="${pageContext.request.contextPath}/manager/dashboard">Dashboard</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/manager/dashboard/assets">Retire Assets</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/manager/dashboard/assets/add">Add Assets</a>
            <li>
                <a href="${pageContext.request.contextPath}/manager/dashboard/loanHistory">Reports</a>
            </li>
        </ul>
        <form action="${pageContext.request.contextPath}/logout" method="post">
            <button type="submit" class="logout-btn">Logout →</button>
        </form>

    </aside>

    <!-- MAIN CONTENT -->
    <main id="mainContent" class="main-content">

        <c:if test="${not empty ManagerDashboardSuccessMessage}">
            <div class="toast-message">
                ${ManagerDashboardSuccessMessage}
            </div>
        </c:if>

        <section class="dashboard-header">
            <h1>Manager Dashboard</h1>
        </section>

        <!-- DASHBOARD CARDS -->
                <section class="manager-dashboard-cards">
        <div class = "dashboard-grid" >
                    <div class="stat-card">
                        <h2>Total Assets</h2>
                        <p>${totalAssets}</p>
                    </div>

                    <div class="stat-card">
                        <h2>Available Assets</h2>
                        <p>${availableAssets}</p>
                    </div>

                    <div class="stat-card">
                        <h2>Loaned Assets</h2>
                        <p>${loanedAssets}</p>
                    </div>

                    <div class="stat-card">
                        <h2>Retired Assets</h2>
                        <p>${retiredAssets}</p>
                    </div>

                    <div class="stat-card">
                        <h2>Total Loans</h2>
                        <p>${totalLoans}</p>
                    </div>

                    <div class="stat-card">
                        <h2>Approved Loans</h2>
                        <p>${approvedLoans}</p>
                    </div>

                    <div class="stat-card">
                        <h2>Pending Loans</h2>
                        <p>${pendingLoans}</p>
                    </div>
                    <div class="stat-card">
                        <h2>Overdue Loans</h2>
                        <p>${overdueLoans}</p>
                    </div>
              </div>

        </section>

        <!-- APPROVED LOANS TABLE -->
        <section class="table-section">

            <h2>Approved Loans</h2>

            <table>
                <thead>
                <tr>
                    <th>Loan ID</th>
                    <th>Image</th>
                    <th>Asset</th>
                    <th>Borrower</th>
                    <th>Checkout Date</th>
                    <th>Due Date</th>
                    <th>Action</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${approvedLoanList}" var="loan">
                    <tr>
                        <td>${loan.loanId}</td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty loan.asset.photoPath}">
                                    <img src="${pageContext.request.contextPath}${loan.asset.photoPath}"
                                         alt="Asset Image"
                                         width="80">
                                </c:when>
                                <c:otherwise>
                                    No image
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${loan.asset.title}</td>
                        <td>${loan.user.name}</td>
                        <td>${loan.checkoutDate}</td>
                        <td>${loan.dueDate}</td>

                        <td>
                            <form action="${pageContext.request.contextPath}/manager/dashboard/return/${loan.loanId}"
                                  method="post">
                                <button type="submit" class="return-btn">
                                    Return Asset
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </section>



        <!-- LOANS OVERDUE TABLE -->
        <section class="table-section">

        <h2>Overdue Loans</h2>

        <table border="1">
            <tr>
                <th>Loan ID</th>
                <th>User</th>
                <th>Asset</th>
                <th>Request Date</th>
                <th>Due Date</th>
                <th>Status</th>
            </tr>

            <c:forEach items="${overdueLoanList}" var="loan">
                <tr>
                    <td>${loan.loanId}</td>
                    <td>${loan.user.name}</td>
                    <td>${loan.asset.title}</td>
                    <td>${loan.requestDate}</td>
                    <td>${loan.dueDate}</td>
                    <td>${loan.status}</td>
                </tr>
            </c:forEach>
</table>
<table>
            <h2>Loan Requests Queue</h2>

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

                                <td>${req.assetTitle}</td>
                                <td>${req.borrowerName}</td>
                                <td>${req.requestDate}</td>
                                <td>${req.dueDate}</td>
                                <td>${req.status}</td>

                                <td>

                                    <form action="${pageContext.request.contextPath}/manager/dashboard/approve"
                                          method="post"
                                          >

                                        <input type="hidden" name="loanId" value="${req.id}" />

                                        <button type="submit" class="approve-btn">
                                            Approve
                                        </button>

                                    </form>

                                    <form action="${pageContext.request.contextPath}/manager/dashboard/reject"
                                          method="post"
                                          >

                                        <input type="hidden" name="loanId" value="${req.id}" />

                                        <button type="submit" class="reject-btn">
                                            Reject
                                        </button>

                                    </form>

                                </td>

                            </tr>
                        </c:forEach>

                        </tbody>
        </table>
    </section>
    </main>

</div>

<!--  FOOTER -->
<footer class="footer">
    <a href="${pageContext.request.contextPath}/terms">Terms & Conditions</a> |
    <a href="${pageContext.request.contextPath}/contact">Contact Us</a>
</footer>


<script src="${pageContext.request.contextPath}/js/manager-dashboard.js"></script>
</body>
</html>