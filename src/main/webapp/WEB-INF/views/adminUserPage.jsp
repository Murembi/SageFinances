<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin User Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>

<!-- ================= HEADER (SHARED ACROSS ALL PAGES) ================= -->
<div>

    <!-- Logo -->
    <div>
        <img src="${pageContext.request.contextPath}/images/mecer-inter-ed-logo.jpg"
             alt="Logo"
             width="120">
    </div>

    <!-- System Title -->
    <h2>Admin Users</h2>

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



<h3>Search Users</h3>

<form method="get" action="/admin/users">

    <input type="text"
           name="keyword"
           placeholder="Search by name, email, department, role"
           value="${keyword}">

    <button type="submit">Search</button>

    <button type="button" onclick="clearSearch()">Reset</button>

</form>

<!-- USERS TABLE -->
<h3>All Users</h3>

<table border="1">

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
            <td>${user.status}</td>

            <td>
                <!-- UPDATE ROLE -->
                <form action="${pageContext.request.contextPath}/admin/users/update-role/${user.userId}"
                      method="post"
                      >

                    <select name="role">
                        <option value="BORROWER">Borrower</option>
                        <option value="MANAGER">Manager</option>
                        <option value="ADMIN">Admin</option>
                    </select>

                    <button type="submit">
                        Update Role
                    </button>

                </form>

                <!-- DELETE -->
                <form action="${pageContext.request.contextPath}/admin/users/delete/${user.userId}"
                      method="post"
                      onsubmit="return confirm('Deactivate this user?');"
                      >

                    <button type="submit">
                        Deactivate
                    </button>

                </form>

            </td>

        </tr>

    </c:forEach>

</table>



<!-- REGISTER USER -->
<h3>Register New User</h3>

<form action="${pageContext.request.contextPath}/users/register" method="post">

    <p>
        Name: <input type="text" name="name" required>
    </p>

    <p>
        Email: <input type="email" name="email" required>
    </p>

    <p>
        Department: <input type="text" name="department" required>
    </p>

    <p>
        Role:
        <select name="role">
            <option value="ADMIN">ADMIN</option>
            <option value="MANAGER">MANAGER</option>
            <option value="BORROWER">BORROWER</option>
        </select>
    </p>

    <p>
        Password: <input type="password" name="passwordHash" required>
    </p>

    <button type="submit">Create User</button>

</form>

<!-- ================= FOOTER (SHARED ACROSS ALL PAGES) ================= -->
<div>
    <a href="${pageContext.request.contextPath}/terms">Terms & Conditions</a> |
    <a href="${pageContext.request.contextPath}/contact">Contact Us</a>
</div>

<script>
    function clearSearch() {
        document.querySelector('input[name="keyword"]').value = '';
    }
</script>

</body>
</html>