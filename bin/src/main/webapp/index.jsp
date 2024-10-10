<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
<a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
<!-- Form tìm kiếm -->
<form action="search" method="get">
    <input type="text" name="keyword" placeholder="Tìm kiếm sản phẩm..." required>
    <button type="submit">Tìm kiếm</button>
</form>
</body>
</html>
<!-- File dashboard.jsp -->
