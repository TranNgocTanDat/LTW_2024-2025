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
    }
    .section {
      margin-top: 20px;
    }
    table {
      width: 100%;
      border-collapse: collapse;
    }
    table, th, td {
      border: 1px solid #ddd;
    }
    th, td {
      padding: 10px;
      text-align: center;
    }
    th {
      background-color: #f2f2f2;
    }
    button {
      padding: 10px;
      background-color: #4CAF50;
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
    }
    button:hover {
      background-color: #45a049;
    }
  </style>
</head>
<body>
<div class="container">
  <h1>Trang Quản Trị Admin</h1>

  <!-- Section Quản lý sản phẩm -->
  <div id="products" class="section">
    <h2>Quản lý User</h2>
    <button onclick="location.href='addProduct.jsp'">Thêm user mới</button>
    <table>--%>
      <tr>
        <th>Name</th>
        <th>Password</th>
        <th>Email</th>
        <th>FirstName</th>
        <th>LastName</th>
        <th>address</th>
        <th>Phone Number</th>
        <th>role</th>
        <th>Action</th>
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
            <a href="users?action=edit&id=${user.userId}">Edit</a>
            <a href="users?action=delete&id=${user.userId}" onclick="return confirm('Are you sure you want to delete this product?');">Delete</a>
          </td>
        </tr>
      </c:forEach>
    </table>

    <a href="product-form.jsp?action=new">Add New Product</a>
  </div>
</div>
</body>
</html>