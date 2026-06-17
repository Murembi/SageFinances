<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Assets</title>
</head>

<body>

<h2>Assets Page</h2>

<!-- CREATE FORM -->
<h3>Create Asset</h3>

<form action="/jsp/assets/create" method="post">

    Title: <input type="text" name="title" /><br/>
    Category: <input type="text" name="category" /><br/>
    Serial: <input type="text" name="serialNumber" /><br/>
    Cost: <input type="number" step="0.01" name="cost" /><br/>
    Location: <input type="text" name="location" /><br/>
    Condition: <input type="text" name="condition" /><br/>

    <button type="submit">Create</button>
</form>

<hr/>

<!-- TABLE -->
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
                <form action="/jsp/assets/delete/${a.assetId}" method="post">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>

</table>

</body>
</html>