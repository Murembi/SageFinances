<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Asset Management</title>
</head>
<body>

<!-- ================= HEADER (COMMON) ================= -->

<div>

    <!-- Logo -->
    <div>
        <img src="${pageContext.request.contextPath}/images/logo.png"
             alt="Logo"
             width="100">
    </div>

    <!-- User Info -->
    <div>
        <p>Username: ${sessionScope.user.name}</p>
        <p>Role: ${sessionScope.user.role}</p>
    </div>

    <!-- Navigation -->
    <div>
        <a href="/admin/dashboard">Dashboard</a> |
        <a href="/assets">Assets</a> |
        <a href="/loan-page">Loans</a> |
        <a href="/admin/users">Users</a> |
        <a href="/settings">Settings</a> |
        <a href="/logout">Logout</a>
    </div>

</div>

<hr>

<!-- ================= PAGE TITLE ================= -->

<h2>Asset Management</h2>

<hr>

<!-- ================= CREATE ASSET ================= -->

<h3>Create Asset</h3>

<form action="/jsp/assets/create" method="post">

    Title: <input type="text" name="title"><br>
    Category: <input type="text" name="category"><br>
    Serial Number: <input type="text" name="serialNumber"><br>
    Cost: <input type="number" step="0.01" name="cost"><br>
    Location: <input type="text" name="location"><br>
    Condition: <input type="text" name="condition"><br>

    <button type="submit">Create</button>
</form>

<hr>

<!-- ================= SEARCH ================= -->

<h3>Search Assets</h3>

<form method="get" action="/assets">

    <input type="text" name="keyword" placeholder="Search assets...">

    <button type="submit">Search</button>

    <a href="/assets">Reset</a>

</form>

<hr>

<!-- ================= TABLE ================= -->

<h3>All Assets</h3>

<table border="1">

    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Category</th>
        <th>Serial</th>
        <th>Cost</th>
        <th>Location</th>
        <th>Condition</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>

    <c:forEach var="a" items="${assets}">

        <tr>

            <td>${a.assetId}</td>
            <td>${a.title}</td>
            <td>${a.category}</td>
            <td>${a.serialNumber}</td>
            <td>${a.cost}</td>
            <td>${a.location}</td>
            <td>${a.condition}</td>
            <td>${a.status}</td>

            <td>

                <a href="/assets/edit/${a.assetId}">
                    Edit
                </a>

                |

                <form action="/jsp/assets/delete/${a.assetId}"
                      method="post"
                      style="display:inline;">

                    <button type="submit">Delete</button>

                </form>

            </td>

        </tr>

    </c:forEach>

</table>

<hr>

<!-- ================= FOOTER (COMMON) ================= -->

<div>

    <a href="/terms">Terms & Conditions</a> |
    <a href="/contact">Contact Us</a>

</div>

</body>
</html>