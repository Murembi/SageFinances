<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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

        <img src="${pageContext.request.contextPath}/images/img_1.png"
             alt="Logo"
             class="login-logo"
             width="100">

        <h1>Asset Management System</h1>

        <p class="login-subtitle">
           Sign in to continue
        </p>

        <form action="${pageContext.request.contextPath}/auth/login"
              method="post">
<!--required-->
            <div class="form-group">
                <label>Email Address</label>
                <input type="email"
                       name="email"
                       placeholder="Enter your email"
                       >
            </div>

            <div class="form-group">
                <label>Password</label>
                <input type="password"
                       name="password"
                       placeholder="Enter your password"
                       >
                       
                 <!--2 here-->
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
        </div>

<!-- ================= FOOTER ================= -->


<!-- ================= CONTACT POPUP ================= -->
<div id="contactPopup" class="popup">
    <h3>Contact Us</h3>

    <p><i class="fa-solid fa-envelope"></i> info@mecerintered.co.za</p>
    <p><i class="fa-solid fa-phone"></i> (+27) 10 730 0860</p>


</div>


<script src="${pageContext.request.contextPath}/js/login.js"></script>
    </div>
    </div>
</body>
</html>