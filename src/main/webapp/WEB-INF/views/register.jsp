<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register User</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/styles.css?v=10">
</head>

<body class="login-page">

<div class="login-container">

    <div class="login-card">

        <img src="${pageContext.request.contextPath}/images/sage.png"
             alt="Logo"
             class="login-logo">

        <h1>Create Account</h1>

        <p class="login-subtitle">
            Register to access Sage Assets
        </p>

        <c:if test="${not empty error}">
            <div class="error-box">
                    ${error}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/users/register"
              method="post">

            <div class="form-group">
                <label>Full Name</label>
                <input type="text"
                       name="name"
                       required>
            </div>

            <div class="form-group">
                <label>Email Address</label>
                <input type="email"
                       name="email"
                       required>
            </div>

            <div class="form-group">
                <label>Department</label>
                <input type="text"
                       name="department"
                       required>
            </div>

            <div class="form-group">
                <label>Password</label>
                <input type="password"
                       name="passwordHash"
                       required
                       minlength="8"
                       pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&!#^()_+\-=]).{8,}$"
                       title="Password must be at least 8 characters and include uppercase, lowercase, number, and special character.">

                <small class="password-hint">
                    Use 8+ characters with uppercase, lowercase, number, and special character.
                </small>
            </div>

            <button type="submit" class="login-btn">
                Create Account
            </button>

        </form>

        <div class="register-link">
            Already have an account?
            <a href="${pageContext.request.contextPath}/loginpage">
                Sign In
            </a>
        </div>

    </div>

</div>

<footer class="footer">
    <a href="${pageContext.request.contextPath}/terms">Terms & Conditions</a>
    <a href="${pageContext.request.contextPath}/contact">Contact Us</a>
</footer>

<script src="${pageContext.request.contextPath}/js/register.js"></script>

</body>
</html>