<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register User</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>

<div id="customAlert" class="alert" style="display: none;"></div>

<div class="auth-container">
    <form id="createUserForm"
          action="${pageContext.request.contextPath}/users/register"
          method="post"
          autocomplete="off">
        <h1>Register User</h1>

        <div class="input-group">
            <input type="text" id="name" name="name" required>
            <label for="name">Full Name *</label>
        </div>

        <div class="input-group">
            <input type="email" id="email" name="email" required>
            <label for="email">Email *</label>
        </div>

        <div class="input-group">
            <input type="text" id="department" name="department" required>
            <label for="department">Department *</label>
        </div>

        <div class="input-group">
            <select id="role" name="role" required>
                <option value="">Select Role</option>
                <option value="MANAGER">Manager</option>
                <option value="BORROWER">Borrower</option>
                <option value="ADMIN">Admin</option>
            </select>
        </div>

        <div class="input-group">
            <input type="password" id="passwordHash" name="passwordHash" required>
            <label for="passwordHash">Password *</label>
        </div>

        <button type="submit" id="createUserBtn" class="submit-btn">
            Create User <span class="arrow">&#8594;</span>
        </button>


    </form>
</div>

<footer>
    <div class="footer-bottom">
        <a href="https://mecerintered.co.za/terms-and-conditions">
            Terms &amp; Conditions
        </a>
        <a href="#" id="contactLink">Contact Us</a>
    </div>
</footer>


</body>
</html>