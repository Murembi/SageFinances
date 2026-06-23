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

<table class="table-container">
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
