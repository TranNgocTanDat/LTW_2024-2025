<%--
  Created by IntelliJ IDEA.
  User: Danh Nguyen
  Date: 12/22/2024
  Time: 5:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            display: grid;
        }

        .notification__title {
            font-size: 50px;
            font-weight: bold;
            color: #333;
            margin-bottom: 20px;
            margin-top: -200px;
        }

        .notification__message {
            max-width: 400px;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            background-color: #fff;
            font-size: 16px;
            line-height: 1.5;
            color: #444;
            margin-top: -800px;
            margin-left: 10px;
        }

        .notification__message--error {
            border-left: 5px solid #e74c3c; /* Đỏ cho lỗi */
            background-color: #fef2f2;
        }

        .notification__message--success {
            border-left: 5px solid #2ecc71; /* Xanh lá cho thành công */
            background-color: #f2fcf3;
        }
        .notification-dot {
            width: 15px;
            height: 15px;
            background-color: red;
            border-radius: 50%;
            position: absolute;
            top: 10px;
            right: 10px;
        }
    </style>
</head>
<body>
<div class="notification__title">
    Thông báo của bạn
</div>

<c:if test="${user == null || user.status == null}">
    <div class="notification__message notification__message--error">
        Không có thông báo nào
    </div>
</c:if>

<c:if test="${user != null && user.status != null}">
    <div class="notification__message notification__message--success">
        Status: ${user.status}
    </div>
    <script>
        // Kiểm tra xem user có status không và lưu vào sessionStorage
        if ('${user.status}' !== 'null' && '${user.status}' !== '') {
            // Lưu thông báo vào sessionStorage
            sessionStorage.setItem('notification', 'Bạn có thông báo mới!');
        }
    </script>
</c:if>
</body>
</html>
