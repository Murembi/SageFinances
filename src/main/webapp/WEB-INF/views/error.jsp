<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body class="dashboard-page">

<header class="header">
    <div class="logo">
        <img src="${pageContext.request.contextPath}/images/sage.png" alt="Logo" class="dashboard-logo">
    </div>
    <h1 class="header-title">Error</h1>
</header>

<main class="main-content" style="flex: 1; display: flex; align-items: center; justify-content: center; text-align: center;">
    <div class="card" style="max-width: 500px;">
        <h1 style="color: #dc3545;">Oops! Something went wrong</h1>
        <p style="margin: 20px 0; font-size: 1.1em;">${errorMessage}</p>
        <a href="${pageContext.request.contextPath}/" class="btn" style="text-decoration: none; background: var(--green-dark); color: white; padding: 10px 20px; border-radius: 8px;">
            Return Home
        </a>
    </div>
</main>

<footer class="footer">
    <a href="${pageContext.request.contextPath}/terms">Terms & Conditions</a>
    <a href="${pageContext.request.contextPath}/contact">Contact Us</a>
</footer>

</body>
</html>