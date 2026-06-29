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
<body class="dashboard-page">

<header class="header">
    <div class="logo">
        <img src="${pageContext.request.contextPath}/images/sage.png" alt="Logo" class="dashboard-logo">
    </div>
    <h1 class="header-title">Asset Management</h1>
</header>

<div class="container">
    <aside class="sidebar">
        <ul class="sidebar-menu">
            <li>
                <a href="${pageContext.request.contextPath}/manager/dashboard">Dashboard</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/manager/dashboard/assets">Assets</a>
            </li>
        </ul>
    </aside>

    <main class="main-content">
        <section class="card">
            <h3>Search Assets</h3>

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

        </section>

        <section class="card">
            <h3>All Assets</h3>
            <table>
                <thead>
                    <tr>
                        <th>Image</th>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Category</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${assets}" var="asset">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty asset.photoPath}">
                                        <img src="${pageContext.request.contextPath}${asset.photoPath}" alt="${asset.title}" class="asset-thumbnail">
                                    </c:when>
                                    <c:otherwise>No image</c:otherwise>
                                </c:choose>
                            </td>
                            <td>${asset.assetId}</td>
                            <td>${asset.title}</td>
                            <td>${asset.category}</td>
                            <td>${asset.status}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${asset.status == 'AVAILABLE'}">
                                        <form action="${pageContext.request.contextPath}/manager/dashboard/assets/retire" method="post">
                                            <input type="hidden" name="assetId" value="${asset.assetId}">
                                            <button type="submit">Retire</button>
                                        </form>
                                    </c:when>
                                    <c:when test="${asset.status == 'RETIRED'}">Retired</c:when>
                                    <c:when test="${asset.status == 'LOANED'}">On Loan</c:when>
                                    <c:otherwise>N/A</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </section>
    </main>
</div>

<footer class="footer">
    <a href="${pageContext.request.contextPath}/terms">Terms & Conditions</a>
    <a href="${pageContext.request.contextPath}/contact">Contact Us</a>
</footer>

</body>
</html>
