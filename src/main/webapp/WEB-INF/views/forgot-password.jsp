<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

    <title>Forgot Password</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>

<h2>Reset Password</h2>

<c:if test="${not empty error}">
    <p>${error}</p>
</c:if>

<c:if test="${not empty successMessage}">
    <p>${successMessage}</p>
</c:if>

<form action="${pageContext.request.contextPath}/forgot-password"
      method="post">

    <input type="email"
           name="email"
           placeholder="Email"
           required>

    <input type="password"
           name="newPassword"
           placeholder="New Password"
           required>

    <input type="password"
           name="confirmPassword"
           placeholder="Confirm Password"
           required>

    <button type="submit">
        Reset Password
    </button>

</form>

<a href="${pageContext.request.contextPath}/loginpage">
    Back to Login
</a>

</body>
</html>