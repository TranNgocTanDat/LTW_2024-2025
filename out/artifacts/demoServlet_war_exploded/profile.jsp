<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Thông tin cá nhân</title>
  <style>
    body {
      font-family: Arial, sans-serif;
    }
    .container {
      max-width: 500px;
      margin: 0 auto;
      padding: 20px;
      background-color: #f7f7f7;
      border-radius: 8px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
    h2 {
      text-align: center;
    }
    .profile-info {
      margin: 20px 0;
    }
    .profile-info label {
      font-weight: bold;
      display: block;
      margin-bottom: 5px;
    }
    .profile-info p {
      margin: 0 0 15px;
      padding: 10px;
      background-color: #f1f1f1;
      border-radius: 4px;
    }
    button {
      padding: 10px 20px;
      background-color: #4CAF50;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      display: block;
      width: 100%;
      text-align: center;
    }
    button:hover {
      background-color: #45a049;
    }
  </style>
</head>
<body>
<div class="container">
  <h2>Thông tin cá nhân</h2>

  <div class="profile-info">

    <%--@declare id="username"--%><label for="username">Tên đăng nhập:</label>
    <p>${user.username != null ? user.username : 'Chưa có tên đăng nhập'}</p>

    <label for="email">Email:</label>
    <p>${user.email != null ? user.email : 'Chưa có email'}</p>

    <label for="firstName">Họ:</label>
    <p>${user.firstName != null ? user.firstName : 'Chưa có họ'}</p>

    <label for="lastName">Tên:</label>
    <p>${user.lastName != null ? user.lastName : 'Chưa có tên'}</p>

    <label for="address">Địa chỉ:</label>
    <p>${user.address != null ? user.address : 'Chưa có địa chỉ'}</p>

    <label for="phoneNumber">Số điện thoại:</label>
    <p>${user.phoneNumber != null ? user.phoneNumber : 'Chưa có số điện thoại'}</p>

    <label for="role">Vai trò:</label>
    <p>${user.role != null ? user.role : 'Chưa có vai trò'}</p>
  </div>

  <form action="${pageContext.request.contextPath}/admin/users?action=${users != null ? "update" : "insert"}" method="post">
    <button type="submit">Cập nhật thông tin</button>
  </form>
</div>
</body>
</html>