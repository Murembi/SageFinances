<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Asset</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body class="dashboard-page">

<header class="header">
    <div class="logo">
        <img src="${pageContext.request.contextPath}/images/sage.png" alt="Logo" class="dashboard-logo">
    </div>
    <h1 class="header-title">Add Asset</h1>
</header>

<main class="main-content" style="flex: 1; padding: 20px;">
    <div class="form-container">

        <h2>Add New Asset</h2>

        <form action="${pageContext.request.contextPath}/manager/dashboard/assets/add"
              method="post"
              enctype="multipart/form-data"
              class="asset-form">

            <div class="form-group">
                <label>Asset Title</label>
                <input type="text"
                       name="title"
                       required>
            </div>

            <div class="form-group">
                <label>Category</label>
                <input type="text"
                       name="category"
                       required>
            </div>

            <div class="form-group">
                <label>Serial Number</label>
                <input type="text"
                       name="serialNumber"
                       required>
            </div>

            <div class="form-group">
                <label>Acquisition Date</label>
                <input type="date"
                       name="acquisitionDate"
                       max="<%= java.time.LocalDate.now() %>"
                       required>
            </div>

            <div class="form-group">
                <label>Cost</label>
                <input type="number"
                       step="0.01"
                       min="0"
                       name="cost"
                       required>
            </div>

            <div class="form-group">
                <label>Location</label>
                <input type="text"
                       name="location"
                       required>
            </div>

            <div class="form-group">
                <label>Condition</label>
                <select name="assetCondition" required>
                    <option value="">Select Condition</option>
                    <option value="NEW">New</option>
                    <option value="GOOD">Good</option>
                    <option value="FAIR">Fair</option>
                    <option value="DAMAGED">Damaged</option>
                </select>
            </div>

            <div class="form-group">
                <label>Asset Photo</label>
                <input type="file"
                       name="imageFile"
                       accept="image/*"
                        required>
            </div>

            <button type="submit" class="btn-primary">
                Add Asset
            </button>
        </form>

    </div>

    <div style="margin-top: 20px; text-align: center;">
        <a href="${pageContext.request.contextPath}/manager/dashboard" class="btn" style="text-decoration: none; background: var(--green-dark); color: white; padding: 10px 20px; border-radius: 8px;">
            Back to Dashboard
        </a>
    </div>
</main>

<footer class="footer">
    <a href="${pageContext.request.contextPath}/terms">Terms & Conditions</a>
    <a href="${pageContext.request.contextPath}/contact">Contact Us</a>
</footer>

<script src="${pageContext.request.contextPath}/js/addAsset.js"></script>
</body>
</html>
