<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Chi tiết sản phẩm</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 20px;
    }
    .product-detail {
      display: flex;
      flex-direction: column;
      align-items: center;
    }
    img {
      width: 300px; /* Kích thước hình ảnh */
      height: auto;
    }
    .product-info {
      text-align: center;
    }
  </style>
</head>
<body>
<h1>Chi tiết sản phẩm</h1>
<c:if test="${not empty product}">
  <div class="product-detail">
    <h2>${product.name}</h2>
    <img src="${product.imageUrl}" alt="${product.name}">
    <div class="product-info">
      <p>${product.description}</p>
      <p>Giá: ${product.price} VNĐ</p>
      <form action="cart" method="post">
        <input type="hidden" name="action" value="add">
        <input type="hidden" name="userId" value="${sessionScope.userId}"> <!-- Lấy userId từ session -->
        <input type="hidden" name="productId" value="${product.productId}">
        <input type="number" name="quantity" value="1" min="1" max="${product.stockQuantity}" required>
        <button type="submit">Thêm vào Giỏ hàng</button>
      </form>
    </div>
  </div>
</c:if>
<c:if test="${empty product}">
  <p>Sản phẩm không tồn tại.</p>
</c:if>
</body>
</html>