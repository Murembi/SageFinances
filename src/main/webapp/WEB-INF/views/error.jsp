<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<div class="error-container">
    <h1>Oops! Something went wrong</h1>

    <p>${errorMessage}</p>

</div>

</body>
</html>