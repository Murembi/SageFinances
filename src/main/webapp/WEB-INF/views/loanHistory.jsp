<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Loan History Report</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/styles.css?v=10">
</head>

<body class="dashboard-page">

<header class="header">
    <div class="logo">
        <img src="${pageContext.request.contextPath}/images/sage.png" alt="Logo" class="dashboard-logo">
    </div>
    <h1 class="header-title">Loan History Report</h1>
</header>

<main class="main-content" style="flex: 1; padding: 20px;">
    <div class="card">
        <h2>Search By User</h2>
        <form action="${pageContext.request.contextPath}/manager/dashboard/loanHistory/user" method="get">
            <input type="text" name="username" placeholder="Enter username">
            <button type="submit">Search User</button>
        </form>
    </div>

    <div class="card">
        <h2>Search By Asset</h2>
        <form action="${pageContext.request.contextPath}/manager/dashboard/loanHistory/asset" method="get">
            <input type="text" name="assetTitle" placeholder="Enter asset title">
            <button type="submit">Search Asset</button>
        </form>
    </div>

    <c:choose>
        <c:when test="${not empty loans}">
            <div class="card">
                <h2>Results</h2>
                <table>
                    <tr>
                        <th>Loan ID</th>
                        <th>User</th>
                        <th>Asset</th>
                        <th>Status</th>
                    </tr>
                    <c:forEach items="${loans}" var="loan">
                        <tr>
                            <td>${loan.loanId}</td>
                            <td>${loan.user.name}</td>
                            <td>${loan.asset.title}</td>
                            <td>
                                <span class="status ${loan.status == 'APPROVED' ? 'approved' :
                                      loan.status == 'PENDING' ? 'pending' :
                                      loan.status == 'REJECTED' ? 'rejected' :
                                      'returned'}">
                                    ${loan.status}
                                </span>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:when>
        <c:otherwise>
            <div class="card">
                <p>No loan history found.</p>
            </div>
        </c:otherwise>
    </c:choose>

    <div style="margin-top: 20px;">
        <a href="${pageContext.request.contextPath}/manager/dashboard" class="btn" style="text-decoration: none; background: var(--green-dark); color: white; padding: 10px 20px; border-radius: 8px;">
            Back to Dashboard
        </a>
    </div>
</main>

<footer class="footer">
    <a href="${pageContext.request.contextPath}/terms">Terms & Conditions</a>
    <a href="${pageContext.request.contextPath}/contact">Contact Us</a>
</footer>

</body>
</html>