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
        <img src="${pageContext.request.contextPath}/images/img_1.png"
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

<!-- CREATE ASSET -->
<div class="form-container">

    <h2>Add Asset</h2>

    <!-- OPEN MODAL BUTTON -->
        <button type="button" class="btn" onclick="openAddAssetModal()">
            + Add New Asset
        </button>

</div>

<!-- MODAL OUTSIDE CONTAINER -->
<div id="addAssetModal" class="modal">

    <h3>Add New Asset</h3>

    <form action="${pageContext.request.contextPath}/admin/assets/create"
          method="post">

        <label>Asset Title</label>
        <input type="text" name="title" required>

        <label>Category</label>
        <input type="text" name="category" required>

        <label>Serial Number</label>
        <input type="text" name="serialNumber" required>

        <label>Acquisition Date</label>
        <input type="date" name="acquisitionDate" required>

        <label>Cost</label>
        <input type="number" step="0.01" name="cost" required>

        <label>Location</label>
        <input type="text" name="location" required>

        <label>Condition</label>
        <select name="condition" required>
            <option value="">Select</option>
            <option value="NEW">New</option>
            <option value="GOOD">Good</option>
            <option value="FAIR">Fair</option>
            <option value="DAMAGED">Damaged</option>
        </select>

        <label>Photo Path</label>
        <input type="text" name="photoPath">

        <br><br>

        <button type="submit">Add Asset</button>
        <button type="button" onclick="closeAddAssetModal()">Cancel</button>

    </form>

</div>

<script src="${pageContext.request.contextPath}/js/addAsset.js"></script>

<!-- SEARCH ASSERT -->

<h3>All Assets</h3>

<form method="get" action="${pageContext.request.contextPath}/admin/assets">

    <input type="text"
           name="keyword"
           placeholder="Search assets..."
           value="${keyword}">

    <button type="submit">Search</button>

    <button type="button" onclick="clearSearch()">Reset</button>

</form>



<!-- TABLE -->

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
                <div class="action-buttons">
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
                      method="post">

                    <button type="submit">Delete</button>

                </form>
            </div>
            </td>

        </tr>

    </c:forEach>

</table>

<!-- EDIT ASSET -->
<div id="editModal" class="modal">

    <h3>Edit Asset</h3>

    <form action="${pageContext.request.contextPath}/admin/assets/update"
          method="post">

        <input type="hidden" id="editId" name="assetId">

        Title:
        <input type="text" id="editTitle" name="title"><br><br>
        Category:
        <input type="text" id="editCategory" name="category"><br><br>
        Serial:
        <input type="text" id="editSerial" name="serialNumber" readonly class="readonly-field"><br><br>
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
<div id="termsModal" class="modal">
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
<div id="contactModal" class="modal">
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
</html>