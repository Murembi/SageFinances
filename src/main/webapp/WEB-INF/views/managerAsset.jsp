<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demo.entity.Asset" %>

<!DOCTYPE html>
<html>
<head>
    <title>Manager Assets</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<h1>Asset Management</h1>
<ul class="sidebar-menu">
    <li>
        <a href="${pageContext.request.contextPath}/manager/dashboard">Dashboard</a>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/manager/dashboard/assets">Assets</a>
    </li>
    </ul>


<h3>All Assets</h3>

<form method="get"
      action="${pageContext.request.contextPath}/manager/dashboard/assets" >
    <input type="text"
           name="keyword"
           placeholder="Search assets..."
           value="${keyword}">
    <select name="location">
        <option value="">All Locations</option>
        <c:forEach var="loc" items="${locations}">
            <option value="${loc}"
                ${loc == location ? 'selected' : ''}>
                ${loc}
            </option>
        </c:forEach>
    </select>
    <select name="condition">
        <option value="">All Conditions</option>
        <c:forEach var="cond" items="${conditions}">
            <option value="${cond}"
                ${cond == condition ? 'selected' : ''}>
                ${cond}
            </option>
        </c:forEach>
    </select>
    <select name="status">
        <option value="">All Status</option>
        <c:forEach var="s" items="${statuses}">
            <option value="${s}"
                ${status == s ? 'selected' : ''}>
                ${s}
            </option>
        </c:forEach>
    </select>
    <button type="submit">Search</button>
    <a href="${pageContext.request.contextPath}/manager/dashboard/assets">
        <button type="button">Reset</button>
    </a>

</form>


<table border="1">
    <tr>
        <th>Image</th>
        <th>ID</th>
        <th>Title</th>
        <th>Category</th>
        <th>Status</th>
        <th>Action</th>
    </tr>

    <%
        List<Asset> assets =
                (List<Asset>) request.getAttribute("assets");

        for (Asset asset : assets) {
    %>

    <tr>
        <td>
            <img src="<%= request.getContextPath() + asset.getPhotoPath() %>"
                 alt="<%= asset.getTitle() %>"
                 width="100">
        </td>
        <td><%= asset.getAssetId() %></td>
        <td><%= asset.getTitle() %></td>
        <td><%= asset.getCategory() %></td>
        <td><%= asset.getStatus() %></td>

        <td>
            <% if(asset.getStatus().toString().equals("AVAILABLE")) { %>

            <form action="${pageContext.request.contextPath}/manager/dashboard/assets/retire"
                  method="post">

                <input type="hidden"
                       name="assetId"
                       value="<%= asset.getAssetId() %>">

                <button type="submit">Retire</button>
            </form>

            <% } else if(asset.getStatus().toString().equals("RETIRED")) { %>

            Retired

            <% } else if(asset.getStatus().toString().equals("LOANED")) { %>

            On Loan

            <% } else { %>

            N/A

            <% } %>
        </td>
    </tr>

    <%
        }
    %>
</table>
</body>
</html>
