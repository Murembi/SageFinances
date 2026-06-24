<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Users</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/styles.css?v=10">
</head>

<body>

<header class="header">
    <div>
        <img src="${pageContext.request.contextPath}/images/img_1.png"
             alt="Logo"
             class="logo-img">
    </div>

    <div>
        <h2 class="header-title">Admin Users</h2>
        <p class="user-info">
            Welcome, ${username}
        </p>
    </div>


</header>

<div class="container">

    <aside class="sidebar">

        <ul class="sidebar-menu">

            <li>
                <a href="${pageContext.request.contextPath}/admin/dashboard">
                    Dashboard
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/admin/assets">
                    Assets
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/admin/users">
                    Users
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/admin/loans">
                    Loans
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/admin/auditlog">
                    Audit Log
                </a>
            </li>


        </ul>

        <a href="${pageContext.request.contextPath}/loginpage"
           class="btn logout-btn">
            Log Out
        </a>

    </aside>

    <main class="main-content">

        <c:if test="${not empty generatedPassword}">
            <div class="popup success">
                <strong>User Created Successfully</strong><br>
                Email: ${generatedEmail}<br>
                Password: ${generatedPassword}
            </div>
        </c:if>
<section class="card">
    <h3>Search Users</h3>

    <form method="get" action="${pageContext.request.contextPath}/admin/users">
        <label>Search</label>
        <input type="text"
               name="keyword"
               placeholder="Search by name, email, department, role"
               value="${keyword}">

        <button type="submit">Search</button>
        <button type="button" class="btn-light" onclick="clearSearch()">Reset</button>
    </form>
</section>

<section class="card">
    <h3>All Users</h3>

    <table>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Department</th>
            <th>Role</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>

        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.userId}</td>
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>${user.department}</td>
                <td>${user.role}</td>
                <td>
                    <span class="status available">${user.status}</span>
                </td>

                <td>
                    <form action="${pageContext.request.contextPath}/admin/users/update-role/${user.userId}"
                          method="post">
                        <select name="role">
                            <option value="BORROWER">Borrower</option>
                            <option value="MANAGER">Manager</option>
                            <option value="ADMIN">Admin</option>
                        </select>

                        <button type="submit">Update Role</button>
                    </form>

                    <form action="${pageContext.request.contextPath}/admin/users/delete/${user.userId}"
                          method="post"
                          onsubmit="return confirm('Deactivate this user?');">
                        <button type="submit" class="btn-danger">Deactivate</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</section>

<section class="card">
    <h3>Register New User</h3>

    <form action="${pageContext.request.contextPath}/admin/users/create" method="post">

        <label>Name</label>
        <input type="text" name="name" required>

        <label>Email</label>
        <input type="email" name="email" required>
        <small>Must be a company email (@sageassets.co.za)</small>

        <label>Department</label>
        <input type="text" name="department" required>

        <label>Role</label>
        <select name="role">
            <option value="ADMIN">ADMIN</option>
            <option value="MANAGER">MANAGER</option>
            <option value="BORROWER">BORROWER</option>
        </select>

        <button type="submit">Create User</button>
    </form>
</section>

    </main>
</div>

<footer class="footer">
    <a href="${pageContext.request.contextPath}/terms">Terms & Conditions</a>
    <a href="${pageContext.request.contextPath}/contact">Contact Us</a>
</footer>

<script src="${pageContext.request.contextPath}/js/adminUserPage.js"></script>

</body>
</html>