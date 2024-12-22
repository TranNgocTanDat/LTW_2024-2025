<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kết quả tìm kiếm</title>
</head>
<body>
<h1>Kết quả tìm kiếm cho: <%= request.getParameter("keyword") %></h1>

<c:choose>
    <c:when test="${not empty products}">
        <c:forEach var="product" items="${products}">
            <div class="product">
                <h2>
                    <a href="productDetail?productId=${product.productId}">${product.name}</a>
                </h2>
                <p>${product.description}</p>
                <p>Giá: ${product.price} VNĐ</p>
                <img src="${product.imageUrl}" alt="${product.name}" style="width: 100px; height: 100px;">
            </div>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <p>Không tìm thấy sản phẩm nào.</p>
    </c:otherwise>
</c:choose>
</body>
</html>