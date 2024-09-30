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
  </style>
</head>
<body>
<div class="sidebar">
  <h2>Danh Mục Sản Phẩm</h2>
  <a href="products?category=d">Quần</a>
  <a href="products?category=a">Áo</a>
  <a href="products?category=mu">Mũ</a>
  <a href="products?category=giay">Giày</a>
</div>

<div class="content">
  <h1>Danh Sách Sản Phẩm</h1>
  <table border="1" style="width: 100%; border-collapse: collapse;">
    <tr>
      <th>ID</th>
      <th>Tên Sản Phẩm</th>
      <th>Danh Mục</th>
      <th>Giá</th>
      <th>Mô Tả</th>
      <th>Giá</th>

    </tr>
    <c:forEach var="product" items="${products}">
      <tr>
        <td>${product.name}</td>
        <td>${product.description}</td>
        <td>${product.price}</td>
        <td>${product.category}</td>
        <td>${product.size}</td>

        <td>${product.stockQuantity}</td>
      </tr>
    </c:forEach>
  </table>
</div>
</body>
</html>