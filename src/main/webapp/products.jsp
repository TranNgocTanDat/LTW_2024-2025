
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <title>Danh Sách Sản Phẩm</title>
  <style>
    body {
      display: flex;
      margin: 0;
      font-family: Arial, sans-serif;
    }
    .sidebar {
      width: 200px;
      background-color: #f4f4f4;
      padding: 15px;
      position: fixed;
      height: 100%;
    }
    .sidebar a {
      display: block;
      padding: 10px;
      text-decoration: none;
      color: black;
    }
    .sidebar a:hover {
      background-color: #ddd;
    }
    .content {
      margin-left: 220px; /* Khoảng cách với sidebar */
      padding: 15px;
      width: calc(100% - 220px); /* Đảm bảo nội dung không chồng lên sidebar */
    }
    table {
      width: 100%;
      border-collapse: collapse;
    }
    th, td {
      border: 1px solid #ddd;
      padding: 8px;
      text-align: left;
    }
    th {
      background-color: #f2f2f2;
    }
    img {
      width: 100px; /* Kích thước hình ảnh */
      height: auto;
    }
    .add-to-cart {
      display: flex;
      align-items: center;
      margin-bottom: 20px;
    }
    .add-to-cart select {
      margin-right: 5px;
    }
  </style>
</head>
<body>
<div class="sidebar">
  <h2>Danh Mục Sản Phẩm</h2>
  <a href="products?category=Quần">Quần</a>
  <a href="products?category=Áo">Áo</a>
  <a href="products?category=mu">Mũ</a>
  <a href="products?category=Giày">Giày</a>
</div>

<div class="content">
  <h1>Danh Sách Sản Phẩm</h1>

  <!-- Form thêm sản phẩm vào giỏ -->



  <table>
    <tr>
      <th>ID</th>
      <th>Tên Sản Phẩm</th>
      <th>Danh Mục</th>
      <th>Giá</th>
      <th>Mô Tả</th>
      <th>Kích Thước</th>
      <th>Số Lượng Tồn</th>
      <th>Ảnh</th>
      <th>Xem</th>
      <th>add</th>
    </tr>
    <c:forEach var="product" items="${products}">
      <tr>
        <td>${product.productId}</td>
        <td>${product.name}</td>
        <td>${product.category}</td>
        <td>${product.price} VNĐ</td>
        <td>${product.description}</td>
        <td>${product.size}</td>
        <td>${product.stockQuantity}</td>
        <td><img src="${product.imageUrl}" alt="${product.name}"></td>
        <td><a href="product-detail?productId=${product.productId}">Xem chi tiết</a></td>
        <td><form action="cart" method="post">
          <input type="hidden" name="userId" value="${sessionScope.userId}">
          <input type="hidden" name="productId" value="${product.productId}">
          <input type="number" name="quantity" value="1" min="1" required>
          <button type="submit">Thêm vào Giỏ</button>
        </form></td>
      </tr>
    </c:forEach>
  </table>
</div>
</body>
</html>
