<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Audit Log</title>
</head>
<body>

<!-- ================= HEADER ================= -->
<div>

    <div>
        <img src="${pageContext.request.contextPath}/images/mecer-inter-ed-logo.jpg"
             alt="Logo"
             width="120">
    </div>

    <h2>Admin Audit Log</h2>

    <p>
        Username: ${username} <br>
        Role: ${userRole}
    </p>

</div>

<!-- ================= NAVIGATION ================= -->
<div>
    <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a> |
    <a href="${pageContext.request.contextPath}/admin/assets">Assets</a> |
    <a href="${pageContext.request.contextPath}/admin/users">Users</a> |
    <a href="${pageContext.request.contextPath}/admin/loans">Loans</a> |
    <a href="${pageContext.request.contextPath}/admin/auditlog">Audit Log</a> |
    <a href="${pageContext.request.contextPath}/settings">Settings</a> |
    <a href="${pageContext.request.contextPath}/loginpage">Log out</a>
</div>


<!-- ================= SEARCH BAR ================= -->
<h3>Search Audit Logs</h3>

<form method="get" action="${pageContext.request.contextPath}/admin/auditlog">

    <input type="text"
           name="keyword"
           placeholder="Search by user, entity type, action"
           value="${keyword}">

    <button type="submit">Search</button>

    <button type="button" onclick="clearSearch()">Reset</button>

</form>


<!-- ================= TABLE ================= -->

<table border="1">

    <tr>
        <th>ID</th>
        <th>User</th>
        <th>Entity Type</th>
        <th>Entity ID</th>
        <th>Action</th>
        <th>Timestamp</th>
        <th>Old Value</th>
        <th>New Value</th>
    </tr>

    <c:forEach var="log" items="${logs}">

        <tr>
            <td>${log.logId}</td>
            <td>
                <c:choose>
                    <c:when test="${log.user != null}">
                        ${log.user.name}
                    </c:when>
                    <c:otherwise>
                        SYSTEM
                    </c:otherwise>
                </c:choose>
            </td>

            <td>${log.entityType}</td>
            <td>${log.entityId}</td>
            <td>${log.action}</td>
            <td>${log.timestamp}</td>
            <td>${log.oldValue}</td>
            <td>${log.newValue}</td>
        </tr>

    </c:forEach>

</table>

<!-- ================= FOOTER ================= -->
<div>
    <a href="${pageContext.request.contextPath}/terms">Terms & Conditions</a> |
    <a href="${pageContext.request.contextPath}/contact">Contact Us</a>
</div>


<script src="${pageContext.request.contextPath}/js/adminAuditLog.js"></script>
</body>
</html>
