<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<h1>Login</h1>

<form action="login" method="post">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required><br/>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br/>

    <input type="submit" >
</form>

<c:if test="${not empty errorMessage}">
    <p style="color: red;">${errorMessage}</p>
</c:if>

</body>
</html>
