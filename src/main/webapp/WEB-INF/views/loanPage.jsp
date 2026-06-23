<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Loan Management</title>
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
    <h2>Admin Loans</h2>

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

    <!-- Actions -->


</div>



<!-- AVAILABLE ASSETS -->

<h2>Available Assets</h2>

<form method="get">
    <input type="text" name="assetSearch" placeholder="Search Assets">
    <button type="submit">Search</button>
</form>

<table class="table-container">

    <tr>

        <th>ID</th>
        <th>Title</th>
        <th>Category</th>
        <th>Status</th>
        <th>Action</th>
    </tr>

    <c:forEach items="${assets}" var="asset">

        <tr>

            <td>${asset.assetId}</td>
            <td>${asset.title}</td>
            <td>${asset.category}</td>
            <td>${asset.status}</td>

            <td>

                <form action="${pageContext.request.contextPath}/admin/loans/request" method="post">

                    <input type="hidden"
                           name="assetId"
                           value="${asset.assetId}">

                    <input type="hidden"
                           name="userId"
                           value="${sessionScope.user.userId}">

                    <button type="submit">
                        Request Loan
                    </button>

                </form>

            </td>

        </tr>

    </c:forEach>

</table>

<br><br>

<!-- ALL LOANS -->

<h2>All Loans</h2>

<form method="get">
    <input type="text" name="loanSearch" placeholder="Search Loans">
    <button type="submit">Search</button>
</form>

<table class="table-container">

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
            <td>${loan.status}</td>
            <td>${loan.requestDate}</td>

            <td>

                <c:if test="${loan.status == 'APPROVED'}">
                    <form action="${pageContext.request.contextPath}/admin/loans/return/${loan.loanId}"
                          method="post">

                        <button type="submit">
                            Return Asset
                        </button>

                    </form>
                </c:if>

            </td>

        </tr>

    </c:forEach>

</table>

<br><br>

<!-- PENDING REQUESTS -->

<h2>Loan Requests</h2>

<table class="table-container">

    <tr>

        <th>Loan ID</th>
        <th>User</th>
        <th>Asset</th>
        <th>Status</th>
        <th>Request Date</th>
        <th>Actions</th>
    </tr>

    <c:forEach items="${requests}" var="loanRequest">

        <tr>

            <td>${loanRequest.loanId}</td>
            <td>${loanRequest.user.name}</td>
            <td>${loanRequest.asset.title}</td>
            <td>${loanRequest.status}</td>
            <td>${loanRequest.requestDate}</td>

            <td>

                <form action="${pageContext.request.contextPath}/admin/loans/approve/${loanRequest.loanId}"
                      method="post">

                    <button type="submit">
                        Approve
                    </button>

                </form>

                <form action="${pageContext.request.contextPath}/admin/loans/reject/${loanRequest.loanId}"
                      method="post">
                    <button type="submit">
                        Reject
                    </button>

                </form>

            </td>

        </tr>

    </c:forEach>

</table>
<h2>Returned Assets</h2>

<table class="table-container">
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
            <td>${loan.status}</td>
            <td>${loan.returnDate}</td>
        </tr>
    </c:forEach>
</table>

<!-- ================= FOOTER (SHARED ACROSS ALL PAGES) ================= -->
<div>
    <a href="${pageContext.request.contextPath}/terms">Terms & Conditions</a> |
    <a href="${pageContext.request.contextPath}/contact">Contact Us</a>
</div>

</body>
</html>