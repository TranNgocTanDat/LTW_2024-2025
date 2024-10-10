<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng Ký</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<div class="container">
    <h2>Đăng Ký</h2>
    <form action="register" method="post">
        <div class="form-group">
            <label for="username">Tên người dùng:</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">Mật khẩu:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="firstName">Họ:</label>
            <input type="text" id="firstName" name="firstName" required>
        </div>
        <div class="form-group">
            <label for="lastName">Tên:</label>
            <input type="text" id="lastName" name="lastName" required>
        </div>
        <div class="form-group">
            <label for="address">Địa chỉ:</label>
            <input type="text" id="address" name="address">
        </div>
        <div class="form-group">
            <label for="phoneNumber">Số điện thoại:</label>
            <input type="text" id="phoneNumber" name="phoneNumber">
        </div>
        <button type="submit">Đăng Ký</button>
    </form>
    <p>Đã có tài khoản? <a href="login.jsp">Đăng nhập ngay!</a></p>
</div>
</body>
</html>