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
<div>

    <!-- Logo -->
    <div>
        <img src="${pageContext.request.contextPath}/images/img.png"
             alt="Logo"
             class="login-logo"
             width="100">
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
           <a href="${pageContext.request.contextPath}/admin/auditlog">Audit Log</a> |
           <a href="${pageContext.request.contextPath}/settings">Settings</a> |
           <a href="${pageContext.request.contextPath}/loginpage">Log out</a>
        </div>

</div>


<!-- ================= CREATE ASSET ================= -->

<h3>Create Asset</h3>

<form action="${pageContext.request.contextPath}/admin/assets/create" method="post">

    Title: <input type="text" name="title"><br>
    Category: <input type="text" name="category"><br>
    Serial Number: <input type="text" name="serialNumber"><br>
    Cost: <input type="number" step="0.01" name="cost"><br>
    Location: <input type="text" name="location"><br>
    Condition: <input type="text" name="condition"><br>

    <button type="submit">Create</button>
</form>



<!-- ================= SEARCH ================= -->

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
<div id="editModal"
     style="display:none;
            position:fixed;
            top:20%;
            left:35%;
            background:white;
            border:1px solid black;
            padding:20px;">

    <h3>Edit Asset</h3>

    <form action="${pageContext.request.contextPath}/admin/assets/update"
          method="post">

        <input type="hidden" id="editId" name="assetId">

        Title:
        <input type="text" id="editTitle" name="title"><br><br>

        Category:
        <input type="text" id="editCategory" name="category"><br><br>

        Serial:
        <input type="text" id="editSerial" name="serialNumber" readonly
               style="background-color:#e9ecef; color:#6c757d;"><br><br>

        Cost:
        <input type="number" step="0.01" id="editCost" name="cost"><br><br>

        Location:
        <input type="text" id="editLocation" name="location"><br><br>

        Condition:
        <input type="text" id="editCondition" name="condition"><br><br>

        Status:
        <select id="editStatus" name="status">
            <option value="AVAILABLE">AVAILABLE</option>
            <option value="LOANED">LOANED</option>
            <option value="RETIRED">RETIRED</option>
        </select>
        <br><br>

        <button type="submit">Update</button>
        <button type="button" onclick="closeModal()">Cancel</button>

    </form>
</div>

<!-- ================= FOOTER (SHARED ACROSS ALL PAGES) ================= -->

<!--Terms Modal-->
<div id="termsModal" style="display:none; position:fixed; top:15%; left:25%; width:50%; background:white; border:1px solid #000; padding:20px; z-index:9999;">
    <h2>Terms & Conditions</h2>

    <p>
        Users must handle assets responsibly. All actions are logged and monitored.
    </p>

    <ul>
        <li>No unauthorized asset edits</li>
        <li>Data must be accurate</li>
        <li>System misuse may result in access removal</li>
    </ul>

    <button onclick="closeTerms()">Close</button>
</div>

<!--Contact Modal-->
<div id="contactModal" style="display:none; position:fixed; top:15%; left:25%; width:50%; background:white; border:1px solid #000; padding:20px; z-index:9999;">
    <h2>Contact Us</h2>

    <p>Email: support@sageassets.com</p>
    <p>Phone: +27 11 000 0000</p>

    <button onclick="closeContact()">Close</button>
</div>
<div>
    <a href="#" onclick="openTerms()">Terms & Conditions</a> |
    <a href="#" onclick="openContact()">Contact Us</a>
</div>


<script src="${pageContext.request.contextPath}/js/adminAsset.js"></script>
</body>
</body>
</html>