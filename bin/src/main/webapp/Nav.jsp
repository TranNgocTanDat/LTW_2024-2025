<%--
  Created by IntelliJ IDEA.
  User: Danh Nguyen
  Date: 9/14/2024
  Time: 8:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
  <style>
    body{

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
    </div>
    <div class="nav__search">
      <input type="text" placeholder="Search..." class="search" style="padding-left: 20px; font-size: 15px">
      <button class="bnt__search">
        <i class="fa-solid fa-magnifying-glass"></i>
      </button>
    </div>
    <div class="nav__card">
      <div class="card__item">
          <i class="fa-solid fa-cart-shopping"></i>
          <a href="cartProducts.jsp" class="cart">Giỏ hàng</a>
      </div>
      <div class="login">
          <i class="fa-solid fa-user"></i>
          <a href="login.jsp" class="cart">Đăng nhập</a>
      </div>
    </div>
  </div>
<script src="nav.js"></script>
</body>
</html>
