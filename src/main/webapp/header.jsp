<%--
  Created by IntelliJ IDEA.
  User: Danh Nguyen
  Date: 9/10/2024
  Time: 1:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Double D</title>
  <style>
    body{
      display: block;
    }
    .header{
      width: 100%;
      height: 150px;
      background: #002D62;
      margin-bottom: 30px;
    }
    .header__left{
      width: 40%;
      height: 150px;
      float: left;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    /*.img__footer{*/
    /*  width: 500px;*/
    /*  height: 500px;*/
    /*  display: flex;*/
    /*  text-align: center;*/
    /*  justify-content: center;*/
    /*}*/
    .header__right{
      width: 60%;
      height: 150px;
      float: right;
      color: white;
      display: flex;
      align-items: center;
      justify-content: center;
      font-family:  "Fira Sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
      font-size: 15px;
    }
    .list{
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .list__item{
      width: 150px;
      height: 50px;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .list__item:hover.list__item{
      background-color: #bf9369;
      color: white;
    }
    a{
      font-weight: bold;
      color: #F5F5F5;
      text-decoration: none;
    }
  </style>
</head>
<body>
  <div class="header">
    <div class="header__left">
      <img src="" class="img__footer">
    </div>
    <div class="header__right">
      <ul class="list">
        <a href="home">
          <li class="list__item active">
            Trang chủ
          </li>
        </a>
        <a href="products">
          <li class="list__item header__product">
            Sản Phẩm
          </li>
        </a>
        <li class="list__item">Cửa hàng</li>
        <li class="list__item">?Help?</li>
        <li class="list__item">Tin tức &  Sự kiện</li>
      </ul>
    </div>
  </div>
  <div class="nav">
    <jsp:include page="Nav.jsp"></jsp:include>
  </div>
</body>
</html>
