<%--
  Created by IntelliJ IDEA.
  User: Danh Nguyen
  Date: 9/10/2024
  Time: 1:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Double D</title>
    <style>
        body {
            margin: 0;
            font-family: "Fira Sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
            background-color: #f5f5f5;
        }

        .header {
            width: 100%;
            height: 100px;
            background: #002D62;
            margin-bottom: 30px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0 20px;
            box-sizing: border-box;
        }

        .header__left {
            width: 40%;
            height: 100%;
            display: flex;
            align-items: center;
        }

        .header__left img {
            max-height: 80%;
            width: auto;
        }

        .header__right {
            width: 60%;
            height: 100%;
            display: flex;
            align-items: center;
            justify-content: flex-end;
        }

        .list {
            display: flex;
            gap: 20px;
            list-style: none;
            padding: 0;
            margin: 0;
            border: none;
        }

        .list__item {
            padding: 10px 15px;
            cursor: pointer;
            border-radius: 5px;
            transition: background-color 0.3s, color 0.3s;
            font-size: 18px; /* Phù hợp với chiều cao header */
            font-weight: bold;
        }

        .list__item:hover {
            background-color: #bf9369;
            color: #fff;
        }

        a {
            font-weight: bold;
            color: #F5F5F5;
            text-decoration: none;
        }

        a .list__item.active {
            background-color: #bf9369;
            color: #fff;
        }
        .notification-dot {
            width: 15px;
            height: 15px;
            background-color: red;
            border-radius: 50%;
            position: absolute;
            top: 40px;
            right: 220px;
            /*display: none;*/
        }
    </style>
</head>
<body>
<div class="header">
    <div class="header__left">
        <img src="https://incucdep.com/wp-content/uploads/2014/12/logo-thoi-trang.jpg" alt="Logo" class="img__footer">
    </div>
    <div class="header__right">
        <ul class="list">
            <a href="home">
                <li class="list__item active">
                    Trang chủ
                </li>
            </a>
            <a href="products">
                <li class="list__item">
                    Sản Phẩm
                </li>
            </a>
            <a href="">
                <li class="list__item">Cửa hàng</li>
            </a>
            <a href="${pageContext.request.contextPath}/ServletNotification">
                <li class="list__item">Thông báo
                    <c:if test="${not empty sessionScope.user and not empty sessionScope.user.status}">
                        <span class="notification-dot"></span>
                    </c:if>
                </li>
            </a>
            <a href="">
                <li class="list__item">Tin tức & Sự kiện</li>
            </a>
        </ul>

    </div>
</div>
<div class="nav">
    <jsp:include page="Nav.jsp"></jsp:include>
</div>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const notificationStatus = sessionStorage.getItem('notification');

        if (notificationStatus) {
            // Thêm dấu chấm đỏ vào header nếu có thông báo
            const notificationDot = document.createElement('span');
            notificationDot.classList.add('notification-dot');
            document.querySelector('.header__right .list__item:last-child').appendChild(notificationDot);

            // Đặt lại sessionStorage sau khi đã xử lý
            // sessionStorage.removeItem('notification');
        }
    });
</script>
</body>
</html>
