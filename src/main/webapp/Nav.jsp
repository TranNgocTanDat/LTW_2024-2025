<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        body {

    }
    .nav{
      width: 100%;
      height: 50px;
      display: flex;
      align-items: center;
      justify-content: center;
      margin-bottom: 20px;
    }
    .nav__left{
        height: 40px;
        position: absolute;
        left: 120px;
        font-size: 25px;
        background-color: #002D62;
        border: solid 1px black;
        border-radius: 5px;
        color: #F5F5F5;
        display: flex;
        align-items: center;
        justify-content: center;
        box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
        /*display: none;*/
    }
    .sidebar{
        opacity: 0; /* Ẩn sidebar ban đầu */
        visibility: hidden;
        position: absolute;
        top: 45px;
        left: 20px;
        z-index: 1;
        width: 200px;
        height: 200px;
        text-align: center;
        background-color: #002D62;
        color: white;
        border-radius: 20px;
        border: 1px solid black;
        transition: opacity 0.5s ease, visibility 0.5s ease;
    }
    ul{
        margin: 0px;
        padding: 0px;
    }
    .list{
        border-bottom:1px solid  black;
        margin-bottom: 0px;
        height: 50px;
        padding: 0px;
    }
    .shoes{
        border-bottom: none;
    }
    .nav__left:hover .sidebar {
        opacity: 1; /* Hiển thị sidebar */
        visibility: visible; /* Bật lại */
    }

    .nav__search{
      width: 600px;
      display: flex;
      align-items: center;
      justify-content: center;
        margin-left: -15px;
    }
    .search{
      width: 500px;
      height: 50px;
      border-radius: 50px;
    }
    .bnt__search{
      margin-left: 20px;
      font-size: 20px;
      margin-left: -40px;
      border: 0px;
      background-color: white;
    }
    .bnt__search:hover{
      cursor: pointer;
    }
    .nav__card{
      display: flex;
      align-items: center;
      justify-content: center;
        position: absolute;
        left: 1200px;
    }
    .login{
      margin-left: 50px;
    }
    .cart{
        font-weight: bold;
        color: black;
        text-decoration: none;
    }

  </style>

</head>
<body>
<div class="nav">
    <div class="nav__left">
        <i class="fa-solid fa-list"></i>
        Danh Mục Sản Phẩm
        <div class="sidebar">
            <ul>
                <li class="list">
                    <a href="products?category=Quần" style="color: white; font-size: 20px">Quần</a>
                </li>
                <li class="list">
                    <a href="products?category=Áo" style="color: white; font-size: 20px">Áo</a>
                </li>
                <li class="list">
                    <a href="products?category=mu" style="color: white; font-size: 20px">Mũ</a>
                </li>
                <li class="list shoes">
                    <a href="products?category=Giày" style="color: white; font-size: 20px">Giày</a>
                </li>
            </ul>




        </div>
    </div>
    <div class="nav__search">
        <form action="search" method="get">
            <input type="text" name="keyword" placeholder="Search..." class="search" style="padding-left: 20px; font-size: 15px" required>
            <button type="submit" class="bnt__search">
                <i class="fa-solid fa-magnifying-glass"></i>
            </button>
        </form>
    </div>
    <div class="nav__card">
        <div class="card__item">
            <i class="fa-solid fa-cart-shopping"></i>
            <a href="cart" class="cart">Giỏ hàng</a>
        </div>
        <div class="login">
            <i class="fa-solid fa-user"></i>
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <!-- Nếu đã đăng nhập, hiển thị "Xem Profile" -->
                    <a href="profile" class="cart">Xem Profile</a>
                </c:when>
                <c:otherwise>
                    <!-- Nếu chưa đăng nhập, hiển thị "Đăng nhập" -->
                    <a href="login" class="cart">Đăng nhập</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

  </div>

<script src="nav.js"></script>
</body>
</html>