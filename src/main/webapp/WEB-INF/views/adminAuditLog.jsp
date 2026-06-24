<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Audit Log</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/styles.css?v=10">
</head>

<body>

<header class="header">
    <img src="${pageContext.request.contextPath}/images/img_1.png"
         alt="Logo"
         class="logo-img">

    <div>
        <h2 class="header-title">Admin Audit Log</h2>
        <p class="user-info">
            Welcome back, ${username} | ${userRole}
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
            <li><a href="${pageContext.request.contextPath}/settings">Settings</a></li>
        </ul>

        <a href="${pageContext.request.contextPath}/loginpage"
           class="btn logout-btn">
            Log out
        </a>
    </aside>

    <main class="main-content">

        <section class="card">
            <h3>Search Audit Logs</h3>

            <form method="get"
                  action="${pageContext.request.contextPath}/admin/auditlog">

                <label>Search</label>
                <input type="text"
                       name="keyword"
                       placeholder="Search by user, entity type, action"
                       value="${keyword}">

                <button type="submit">Search</button>
                <button type="button" class="btn-light" onclick="clearSearch()">Reset</button>
            </form>
        </section>

        <section class="card">
            <h3>Audit Logs</h3>

            <table>
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
                        <td>
                            <span class="status returned">${log.action}</span>
                        </td>
                        <td>${log.timestamp}</td>
                        <td>${log.oldValue}</td>
                        <td>${log.newValue}</td>
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

<script src="${pageContext.request.contextPath}/js/adminAuditLog.js"></script>

</body>
</html>