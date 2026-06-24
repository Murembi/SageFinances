<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register User</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<div id="customAlert" class="alert" style="display: none;"></div>

<div class="auth-container">
    <form id="createUserForm"
          action="${pageContext.request.contextPath}/users/register"
          method="post"
          autocomplete="off">
        <h1>Register User</h1>
        <c:if test="${not empty error}">
            <p>${error}</p>
        </c:if>
        
        <!--required-->
        <div class="input-group">
            <input type="text" id="name" name="name" >
            <label for="name">Full Name *</label>
        </div>

        <div class="input-group">
            <input type="email" id="email" name="email" >
            <label for="email">Email *</label>
        </div>

        <div class="input-group">
            <input type="text" id="department" name="department" >
            <label for="department">Department *</label>
        </div>

        <div class="input-group">
        </div>

        <div class="input-group">
            <input type="password" id="passwordHash" name="passwordHash" >
            <label for="passwordHash">Password *</label>
        </div>
<!--2 HERE-->

        <button type="submit" id="createUserBtn" class="submit-btn">
            Create User <span class="arrow">&#8594;</span>
        </button>

        <p class="auth-switch">
            Already have an account?
            <a href="${pageContext.request.contextPath}/loginpage">Sign in</a>
        </p>
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



<script src="${pageContext.request.contextPath}/js/register.js"></script>
</body>
</html>