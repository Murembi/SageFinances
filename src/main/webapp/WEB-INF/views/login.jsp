<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ASSET MANAGEMENT SYSTEM</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/styles.css">

</head>

<body>

<div id="customAlert" class="alert" style="display: none;"></div>

<!-- ================= HEADER ================= -->
<header class="header">
    <div class="logo">
        <i class="fa-brands fa-first-order-alt"></i>
        <img src="${pageContext.request.contextPath}/images/IMG-20260611-WA0000.jpg"
             alt="MECER INTER-ED Logo"
             class="logo-img">
    </div>

    <span class="header-text">ASSET MANAGEMENT SYSTEM</span>
</header>

<!-- ================= AUTH CONTAINER ================= -->
<div class="auth-container">

    <div class="toggle-buttons">
        <button class="toggle">Log In</button>
    </div>

    <!-- LOGIN FORM -->
    <form id="logInForm"
          action="${pageContext.request.contextPath}/auth/login"
          method="post"
          autocomplete="off">

        <h1>Welcome!</h1>
        <c:if test="${not empty error}">
            <p>ERROR:${error}</p>
        </c:if>

        <div class="input-group">
            <input type="email" name="email" id="loginEmail" required>
            <label>Email</label>
        </div>

        <div class="input-group">
            <input type="password" name="password" id="loginPassword" required>
            <label>Password</label>

            <i id="toggleIcon"
               class="fa fa-eye-slash toggle-password"
               onclick="togglePassword()"></i>
        </div>

        <button type="submit" id="logInBtn" class="submit-btn">
            Log In <span class="arrow">&#8594;</span>
        </button>

    </form>
</div>

<!-- ================= FOOTER ================= -->
<footer>
    <div class="footer-bottom">
        <a href="https://mecerintered.co.za/terms-and-conditions">
            Terms & Conditions
        </a>

        <a id="contactLink">Contact Us</a>
    </div>
</footer>

<!-- ================= CONTACT POPUP ================= -->
<div id="contactPopup" class="popup">
    <h3>Contact Us</h3>

    <p><i class="fa-solid fa-envelope"></i> info@mecerintered.co.za</p>
    <p><i class="fa-solid fa-phone"></i> (+27) 10 730 0860</p>

    <button onclick="closePopup()">Close</button>
</div>

</body>
</html>