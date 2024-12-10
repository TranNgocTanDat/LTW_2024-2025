<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Tải xuống Tool</title>
</head>
<body>
<h1>Tải xuống Tool</h1>

<!-- Hiển thị thông báo nếu có -->
<c:if test="${not empty message}">
    <p style="color: red;">${message}</p>
</c:if>

<!-- Nút tải xuống -->
<form action="${pageContext.request.contextPath}/downloadTool" method="get">
    <button type="submit">Tải xuống Tool</button>
</form>
</body>
</html>
