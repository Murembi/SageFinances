<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Loan Management</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/styles.css?v=10">
</head>

<body>

<header class="header">
    <img src="${pageContext.request.contextPath}/images/img_1.png"
         alt="Logo"
         class="logo-img">

    <div>
        <h2 class="header-title">Admin Loans</h2>
        <p class="user-info">
            Welcome back
        </p>
    </div>
</header>

<div class="container">

    <aside class="sidebar">
        <ul class="sidebar-menu">
            <li><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/assets">Assets</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/users">Users</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/loans">Loans</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/auditlog">Audit Log</a></li>
        </ul>

        <a href="${pageContext.request.contextPath}/loginpage"
           class="btn logout-btn">
            Log out
        </a>
    </aside>

    <main class="main-content">

        <c:if test="${not empty adminLoanPageSuccessMessage}">
            <div class="toast-message">
                ${adminLoanPageSuccessMessage}
            </div>
        </c:if>

        <section class="card">
            <h2>Active Loans</h2>

            <table>
                <tr>
                    <th>Loan ID</th>
                    <th>User</th>
                    <th>Asset</th>
                    <th>Status</th>
                    <th>Request Date</th>
                    <th>Actions</th>
                </tr>

                <c:forEach items="${loans}" var="loan">
                    <tr>
                        <td>${loan.loanId}</td>
                        <td>${loan.user.name}</td>
                        <td>${loan.asset.title}</td>
                        <td><span class="status approved">${loan.status}</span></td>
                        <td>${loan.requestDate}</td>
                        <td>
                            <c:if test="${loan.status == 'APPROVED'}">
                                <form action="${pageContext.request.contextPath}/admin/loans/return/${loan.loanId}"
                                      method="post">
                                    <button type="submit">Return Asset</button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </section>

        <section class="card">
            <h2>Loan Requests</h2>

            <table>
                <tr>
                    <th>Loan ID</th>
                    <th>Image</th>
                    <th>User</th>
                    <th>Asset</th>
                    <th>Status</th>
                    <th>Request Date</th>
                    <th>Actions</th>
                </tr>

                <c:forEach items="${requests}" var="loanRequest">
                    <tr>
                        <td>${loanRequest.loanId}</td>

                        <td>
                            <c:choose>
                                <c:when test="${not empty loanRequest.asset.photoPath}">
                                    <img src="${pageContext.request.contextPath}${loanRequest.asset.photoPath}"
                                         alt="Asset Image"
                                         width="80">
                                </c:when>
                                <c:otherwise>No image</c:otherwise>
                            </c:choose>
                        </td>

                        <td>${loanRequest.user.name}</td>
                        <td>${loanRequest.asset.title}</td>
                        <td><span class="status pending">${loanRequest.status}</span></td>
                        <td>${loanRequest.requestDate}</td>

                        <td>
                            <form action="${pageContext.request.contextPath}/admin/loans/approve/${loanRequest.loanId}"
                                  method="post">
                                <button type="submit">Approve</button>
                            </form>

                            <form action="${pageContext.request.contextPath}/admin/loans/reject/${loanRequest.loanId}"
                                  method="post">
                                <button type="submit" class="btn-danger">Reject</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </section>

        <section class="card">
            <h2>Returned Assets</h2>

            <table>
                <tr>
                    <th>Loan ID</th>
                    <th>User</th>
                    <th>Asset</th>
                    <th>Status</th>
                    <th>Return Date</th>
                </tr>

                <c:forEach items="${returnedLoans}" var="loan">
                    <tr>
                        <td>${loan.loanId}</td>
                        <td>${loan.user.name}</td>
                        <td>${loan.asset.title}</td>
                        <td><span class="status returned">${loan.status}</span></td>
                        <td>${loan.returnDate}</td>
                    </tr>
                </c:forEach>
            </table>
        </section>

    </main>
</div>

<footer class="footer">
    <a href="${pageContext.request.contextPath}/terms">Terms & Conditions</a>
    <a href="${pageContext.request.contextPath}/contact">Contact Us</a>
</footer>

<script src="${pageContext.request.contextPath}/js/loanPage.js"></script>

</body>
</html>