<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Asset Management</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<!-- ================= HEADER (SHARED ACROSS ALL PAGES) ================= -->
<header class="header">
    <div class="logo">
        <img src="${pageContext.request.contextPath}/images/sage.png"
             alt="Logo"
             class="dashboard-logo">
    </div>

    <span class="header-text">
        ASSET MANAGEMENT SYSTEM
    </span>
</header>



<!-- ================= NAVIGATION (SHARED) ================= -->
<div class="container">

    <aside class="sidebar">

        <ul class="sidebar-menu">
            <li><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/assets">Assets</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/users">Users</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/loans">Loans</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/auditlog">Audit Log</a></li>
        </ul>

        <form action="${pageContext.request.contextPath}/logout" method="post">
            <button type="submit" class="logout-btn">
                Logout →
            </button>
        </form>

    </aside>

    <main class="main-content">
        <section class="dashboard-header">
            <h1>Admin Assets</h1>
            <p>
                Username: ${username} |
                Role: ${userRole}
            </p>
        </section>

        <section class="table-section">

<form action="${pageContext.request.contextPath}/admin/assets/create" method="post">

    <div class="form-group">
        <label>Title</label>
        <input type="text" name="title" required>
    </div>

    <div class="form-group">
        <label>Category</label>
        <input type="text" name="category" required>
    </div>

    <div class="form-group">
        <label>Serial Number</label>
        <input type="text" name="serialNumber" required>
    </div>

    <div class="form-group">
        <label>Cost</label>
        <input type="number" step="0.01" name="cost">
    </div>

    <div class="form-group">
        <label>Location</label>
        <input type="text" name="location" name="title" required>
    </div>

    <div class="form-group">
        <label>Condition</label>
        <input type="text" name="condition" name="title" required>
    </div>

    <button type="submit" class="login-btn" >
        Create Asset
    </button>

</form>
        </section>



<!-- ================= SEARCH ================= -->
        <section class="table-section">
<h3>All Assets</h3>

<form method="get" action="${pageContext.request.contextPath}/admin/assets">

    <input type="text"
           name="keyword"
           placeholder="Search assets..."
           value="${keyword}">

    <!-- Location Filter -->
    <select name="location">

        <option value="">All Locations</option>

        <c:forEach var="loc" items="${locations}">
            <option value="${loc}"
                ${loc == location ? 'selected' : ''}>
                ${loc}
            </option>
        </c:forEach>

    </select>

    <!-- Condition Filter -->
    <select name="condition">

        <option value="">All Conditions</option>

        <c:forEach var="cond" items="${conditions}">
            <option value="${cond}"
                ${cond == condition ? 'selected' : ''}>
                ${cond}
            </option>
        </c:forEach>

    </select>

    <!-- Status Filter -->
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

    <a href="${pageContext.request.contextPath}/admin/assets">
        <button type="button">Reset</button>
    </a>

</form>



<!-- ================= TABLE ================= -->

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
        <th>Action</th>
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

                <button type="button"
                        onclick="openModal(
                            '${a.assetId}',
                            '${a.title}',
                            '${a.category}',
                            '${a.serialNumber}',
                            '${a.cost}',
                            '${a.location}',
                            '${a.condition}',
                            '${a.status}'
                        )">
                    Edit
                </button>

                |

                <form action="${pageContext.request.contextPath}/admin/assets/delete/${a.assetId}"
                      method="post"
                      style="display:inline;">

                    <button type="submit">Delete</button>

                </form>

            </td>

        </tr>

    </c:forEach>

</table>
<div id="editModal" class="edit-modal">

    <h3>Edit Asset</h3>

    <form action="${pageContext.request.contextPath}/admin/assets/update"
          method="post">

        <input type="hidden" id="editId" name="assetId">

        <div class="modal-row">
            <div class="form-group">
                <label>Title</label>
                <input type="text" id="editTitle" name="title">
            </div>

            <div class="form-group">
                <label>Category</label>
                <input type="text" id="editCategory" name="category">
            </div>
        </div>

        <div class="form-group">
            <label>Serial</label>
            <input type="text" id="editSerial" name="serialNumber" readonly class="readonly-input">
        </div>

        <div class="modal-row">
            <div class="form-group">
                <label>Cost</label>
                <input type="number" step="0.01" id="editCost" name="cost">
            </div>

            <div class="form-group">
                <label>Location</label>
                <input type="text" id="editLocation" name="location">
            </div>
        </div>

        <div class="modal-row">
            <div class="form-group">
                <label>Condition</label>
                <input type="text" id="editCondition" name="condition">
            </div>

            <div class="form-group">
                <label>Status</label>
                <select id="editStatus" name="status">
                    <option value="AVAILABLE">AVAILABLE</option>
                    <option value="LOANED">LOANED</option>
                    <option value="RETIRED">RETIRED</option>
                </select>
            </div>
        </div>

        <button type="submit">Update</button>
        <button type="button" onclick="closeModal()">Cancel</button>

    </form>
</div>

        </section>

    </main>
</div>
<!-- ================= FOOTER (SHARED ACROSS ALL PAGES) ================= -->

<script src="${pageContext.request.contextPath}/js/adminAsset.js"></script>
</body>
</html>