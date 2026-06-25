<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome to Sage Asset Management System | Login</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body class="login-page">

<div class="login-container">

    <div class="login-card">

        <img src="${pageContext.request.contextPath}/images/sage.png"
             alt="Sage logo"
             class="login-logo"
             >

        <h1>Welcome</h1>

        <p class="login-subtitle">
           Sign in to continue
        </p>

        <form action="${pageContext.request.contextPath}/auth/login"
              method="post">

            <div class="form-group">
                <label>Email Address</label>
                <input type="email"
                       name="email"
                       placeholder="Enter your email"
                       required>
            </div>

            <div class="form-group">
                <label>Password</label>
                <input type="password"
                       name="password"
                       placeholder="Enter your password"
                       required>
            </div>
            <c:if test="${not empty error}">
                <div class="error-box">
                        ${error}
                </div>
            </c:if>

            <button type="submit" class="login-btn">
                Log In
            </button>

        </form>

        <div class="register-link">
            <p>
                Don't have an account?
                <a href="${pageContext.request.contextPath}/users/register">
                    Create Account
                </a>
            </p>
            <p>
                <a href="${pageContext.request.contextPath}/forgot-password">
                    Forgot Password?
                </a>
            </p>
        </div>



    </div>
</div>

<footer class="footer">
    <a href="${pageContext.request.contextPath}/terms">Terms & Conditions</a>
    <a href="${pageContext.request.contextPath}/contact">Contact Us</a>
</footer>

    <script src="${pageContext.request.contextPath}/js/login.js"></script>
</body>
</html>