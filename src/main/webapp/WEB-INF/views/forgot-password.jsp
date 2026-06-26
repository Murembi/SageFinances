<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

    <title>Forgot Password</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body class="login-page">

<div class="login-container">
    <div class="login-card">
        <h2>Reset Password</h2>

        <c:if test="${not empty error}">
            <div class="error-box">${error}</div>
        </c:if>

        <c:if test="${not empty successMessage}">
            <div class="success-box" style="background: #D1E7DD; color: #0F5132; border: 1px solid #BADBCC; border-radius: 10px; padding: 12px; margin: 12px 0;">
                ${successMessage}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/forgot-password" method="post">
            <div class="form-group">
                <input type="email" name="email" placeholder="Email" required>
            </div>
            <div class="form-group">
                <input type="password" name="newPassword" placeholder="New Password" required>
            </div>
            <div class="form-group">
                <input type="password" name="confirmPassword" placeholder="Confirm Password" required>
            </div>

            <button type="submit" class="login-btn">Reset Password</button>
        </form>

        <div class="register-link">
            <a href="${pageContext.request.contextPath}/loginpage">Back to Login</a>
        </div>
    </div>
</div>

<footer class="footer">
    <a href="${pageContext.request.contextPath}/terms">Terms & Conditions</a>
    <a href="${pageContext.request.contextPath}/contact">Contact Us</a>
</footer>

</body>
</html>