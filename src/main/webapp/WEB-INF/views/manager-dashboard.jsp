<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Asset Management System | Manager Dashboard</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>

<div id="customAlert" class="alert"></div>

<!-- HEADER -->
<header class="header">
    <div class="logo">
        <img src="${pageContext.request.contextPath}/resources/images/mecer-inter-ed-logo.jpg"
             alt="Logo"
             class="logo-img">
    </div>

    <span class="header-text">ASSET MANAGEMENT SYSTEM</span>
</header>

<div class="container">

    <!-- SIDEBAR -->
    <aside id="sidebar" class="sidebar">

        <div class="profile-widget">
            <label>Username</label>
            <input type="text" value="${manager.username}" readonly>

            <label>User Role</label>
            <input type="text" value="Manager" readonly>
        </div>

        <ul class="sidebar-menu">
            <li>
                <a href="${pageContext.request.contextPath}/manager/dashboard">Dashboard</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/manager/dashboard/assets">Assets</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/manager/requests">Requests</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/manager/settings">Settings</a>
            </li>
        </ul>

        <form action="${pageContext.request.contextPath}/logout" method="post">
            <button type="submit" class="logout-btn">Logout →</button>
        </form>

    </aside>

    <!-- MAIN CONTENT -->
    <main id="mainContent" class="main-content">

        <section class="dashboard-header">
            <h1>Manager Dashboard</h1>
        </section>

        <!-- DASHBOARD CARDS -->
        <section class="manager-dashboard-cards">

            <div class="card">
                <h2>Total Assets</h2>
                <span>${totalAssets}</span>
            </div>

            <div class="card">
                <h2>Available Assets</h2>
                <span>${availableAssets}</span>
            </div>

            <div class="card">
                <h2>Loaned Assets</h2>
                <span>${loanedAssets}</span>
            </div>

            <div class="card">
                <h2>Retired Assets</h2>
                <span>${retiredAssets}</span>
            </div>

            <div class="card">
                <h2>Total Loans</h2>
                <span>${totalLoans}</span>
            </div>

            <div class="card">
                <h2>Approved Loans</h2>
                <span>${approvedLoans}</span>
            </div>

            <div class="card">
                <h2>Pending Loans</h2>
                <span>${pendingLoans}</span>
            </div>

        </section>

        <!-- ASSETS TABLE -->
        <section class="table-section">

            <h2>Assets</h2>

            <table>
                <thead>
                <tr>
                    <th>Asset ID</th>
                    <th>Title</th>
                    <th>Category</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${assetList}" var="asset">
                    <tr>
                        <td>${asset.assetId}</td>
                        <td>${asset.title}</td>
                        <td>${asset.category}</td>
                        <td>${asset.status}</td>

                        <td>
                            <c:if test="${asset.status ne 'RETIRED' && asset.status ne 'LOANED'}">
                                <form action="${pageContext.request.contextPath}/manager/assets/retire/${asset.assetId}"
                                      method="post">
                                    <button type="submit" class="retire-btn">
                                        Retire
                                    </button>
                                </form>
                            </c:if>

                            <c:if test="${asset.status eq 'RETIRED'}">
                                <span>Retired</span>
                            </c:if>

                            <c:if test="${asset.status eq 'LOANED'}">
                                <span>On Loan</span>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </section>

        <!-- APPROVED LOANS TABLE -->
        <section class="table-section">

            <h2>Approved Loans</h2>

            <table>
                <thead>
                <tr>
                    <th>Loan ID</th>
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

        <!-- RETURNED ASSETS TABLE -->
        <section class="table-section">

            <h2>Returned Assets</h2>

            <table>
                <thead>
                <tr>
                    <th>Loan ID</th>
                    <th>Asset</th>
                    <th>Borrower</th>
                    <th>Return Date</th>
                    <th>Status</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${returnedLoans}" var="loan">
                    <tr>
                        <td>${loan.loanId}</td>
                        <td>${loan.asset.title}</td>
                        <td>${loan.user.name}</td>
                        <td>${loan.returnDate}</td>
                        <td>${loan.status}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </section>

    </main>

</div>

</body>
</html>