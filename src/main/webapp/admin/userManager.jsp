<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User Manager</title>
  <link rel="stylesheet" href="styles.css">
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f4;
      margin: 0;
      padding: 0;
    }

    .container {
      width: 80%;
      margin: auto;
      background-color: white;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    .nav {
      display: flex;
      justify-content: space-around;
      background-color: #333;
      padding: 15px;
    }

    .nav a {
      color: white;
      text-decoration: none;
      font-weight: bold;
    }

    .nav a:hover {
      color: #ddd;
    }

    h1 {
      text-align: center;
      color: #333;
      margin-bottom: 20px;
    }

    .section {
      margin-top: 20px;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 20px;
      font-size: 14px;
    }

    table, th, td {
      border: 1px solid #ddd;
    }

    th, td {
      padding: 12px 10px;
      text-align: center;
      vertical-align: middle;
    }

    th {
      background-color: #002D62;
      color: white;
      font-weight: bold;
      text-align: center;
    }

    td {
      background-color: #fafafa;
    }

    td a {
      text-decoration: none;
      padding: 5px 10px;
      border-radius: 5px;
      font-size: 13px;
      font-weight: bold;
    }

    td a:hover {
      opacity: 0.8;
    }

    td a[href*="edit"] {
      background-color: #4CAF50;
      color: white;
    }

    td a[href*="delete"] {
      background-color: #f44336;
      color: white;
    }

    button {
      padding: 10px 15px;
      background-color: #4CAF50;
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      font-size: 14px;
      font-weight: bold;
    }

    button:active {
      background-color: #45a049;
    }

    a[href*="product-form.jsp"] {
      display: inline-block;
      margin-top: 20px;
      padding: 10px 15px;
      background-color: #007BFF;
      color: white;
      text-decoration: none;
      border-radius: 5px;
      font-size: 14px;
      font-weight: bold;
      text-align: center;
    }

    a[href*="product-form.jsp"]:hover {
      background-color: #0056b3;
    }

    .table-actions {
      display: flex;
      justify-content: space-around;
      gap: 10px;
    }

  </style>
</head>
<body>
<div class="container">
  <h1>Trang Quản Trị Admin</h1>

  <!-- Section Quản lý sản phẩm -->
  <div id="products" class="section">
    <h2>Quản lý khách hàng</h2>
    <button onclick="location.href='addProduct.jsp'">Thêm khách hàng mới</button>
    <table>
      <tr>
        <th>Tên</th>
        <th>Mật khẩu</th>
        <th>Email</th>
        <th>Tên</th>
        <th>Họ</th>
        <th>Địa chỉ</th>
        <th>Số điện thoại</th>
        <th>Vai trò</th>
        <th>Xử lý</th>
      </tr>
      <c:forEach var="user" items="${users}">
        <tr>
          <td>${user.username}</td>
          <td>${user.password}</td>
          <td>${user.email}</td>
          <td>${user.firstName}</td>
          <td>${user.lastName}</td>
          <td>${user.address}</td>
          <td>${user.phoneNumber}</td>
          <td>${user.role}</td>
          <td>
            <a href="users?action=edit&id=${user.userId}">Thay đổi</a>
            <a href="users?action=delete&id=${user.userId}" onclick="return confirm('Are you sure you want to delete this product?');">Xóa</a>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>
</div>
</body>
</html>