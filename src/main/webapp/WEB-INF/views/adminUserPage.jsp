<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin User Page</title>
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
           <a href="${pageContext.request.contextPath}/admin/auditlog">Audit Log</a> |
           <a href="${pageContext.request.contextPath}/settings">Settings</a> |
           <a href="${pageContext.request.contextPath}/loginpage">Log out</a>
        </div>

<!-- CREATE USER -->
<body>

<div id="customAlert" class="alert" style="display: none;"></div>

<div class="form-container">

    <h2>Register Users</h2>

    <!-- OPEN MODAL BUTTON -->
    <button type="button" class="btn" onclick="openUserModal()">
        + Create User
    </button>

</div>

       <div id="userModal" class="modal">

           <h3>Register User</h3>

           <form id="createUserForm"
                 action="${pageContext.request.contextPath}/users/register"
                 method="post"
                 autocomplete="off">

               <c:if test="${not empty error}">
                   <p style="color:red;">${error}</p>
               </c:if>

               <label>Full Name</label>
               <input type="text" id="name" name="name" required>

               <label>Email</label>
               <input type="email" id="email" name="email" required>

               <label>Department</label>
               <input type="text" id="department" name="department" required>

               <label>Password</label>
               <input type="password" id="passwordHash" name="passwordHash" required>

               <br><br>

               <button type="submit" class="btn">
                   Create User
               </button>

               <button type="button" class="btn-light" onclick="closeUserModal()">
                   Cancel
               </button>
       </form>
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

<script src="${pageContext.request.contextPath}/js/adminUserPage.js"></script>
</body>
</html>