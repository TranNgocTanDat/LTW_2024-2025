<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Danh sách sinh viên</title>
</head>
<body>
<h1>Danh sách sinh viên</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Tên</th>
        <th>Tuổi</th>
    </tr>
    <c:forEach var="user" items="${listUser}">
        <tr>
            <td>${user.userId}</td>
            <td>${user.username}</td>
            <td>${user.email}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>