<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Reports</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

    <h1>Loan History Report</h1>

    <h2>Search By User</h2>
    <form action="${pageContext.request.contextPath}/manager/dashboard/loanHistory/user" method="get">
        <input type="text" name="username" placeholder="Enter username">
        <button type="submit">Search User</button>
    </form>

    <h2>Search By Asset</h2>
    <form action="${pageContext.request.contextPath}/manager/dashboard/loanHistory/asset" method="get">
        <input type="text" name="assetTitle" placeholder="Enter asset title">
        <button type="submit">Search Asset</button>
    </form>

    <hr>

<c:choose>

    <c:when test="${not empty loans}">
    <!-- Results Table -->
    <table border="1">

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
                <td>${loan.status}</td>
            </tr>
        </c:forEach>

    </table>

    </c:when>

    <c:otherwise>

        <p>No loan history found.</p>

    </c:otherwise>

    </c:choose>

    <a href="${pageContext.request.contextPath}/manager/dashboard" class="back-btn">
        Back to Dashboard
    </a>

</body>
</html>
