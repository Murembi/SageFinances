<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin User Page</title>
</head>
<body>

<!-- HEADER -->
<h2>Admin User Management</h2>

<p>
    Role: ADMIN <br>
    Username: Admin
</p>

<hr>

<!-- NAVIGATION -->
<p>
    <a href="${pageContext.request.contextPath}/admin/dashboard">Admin Dashboard</a> |
    <a href="${pageContext.request.contextPath}/assets">Asset Page</a> |
    <a href="${pageContext.request.contextPath}/loans">Loan Page</a> |
    <a href="${pageContext.request.contextPath}/admin/users">User Page</a>
</p>

<hr>

<h3>Search Users</h3>

<form method="get" action="/admin/users">

    <input type="text"
           name="keyword"
           placeholder="Search by name, email, department, role"
           value="${keyword}">

    <button type="submit">Search</button>

    <a href="/admin/users">Reset</a>

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

                <!-- EDIT -->
                <a href="/admin/users/edit/${user.userId}">
                    Edit
                </a>

                |

                <!-- DELETE -->
                <form action="${pageContext.request.contextPath}/api/users/${user.userId}"
                      method="post"
                      style="display:inline;">

                    <input type="hidden" name="_method" value="delete"/>

                    <button type="submit">
                        Delete
                    </button>

                </form>

            </td>

        </tr>

    </c:forEach>

</table>

<hr>

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

<hr>

<!-- FOOTER LINKS -->
<p>
    <a href="#">Terms & Conditions</a> |
    <a href="#">Contact Us</a>
</p>

</body>
</html>