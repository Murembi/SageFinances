<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Asset Management</title>
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
    <h2>Admin Assets</h2>

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

</div>


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



<!-- ================= SEARCH ================= -->

<h3>Search Assets</h3>

<form method="get" action="/assets">

    <input type="text" name="keyword" placeholder="Search assets...">

    <button type="submit">Search</button>

    <a href="/assets">Reset</a>

</form>



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