<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng Ký</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        body{
            margin: 0px;
        }
        .container__register{
            width: 100%;
            height: 1000px;
            background-image: url("https://cmsv2.yame.vn/uploads/32096354-5a50-44fe-8ffd-b5bb147afcee/BST_SEVENTY_SEVEN_DANH_M%e1%bb%a4C.jpg?quality=80&w=1280&h=0");
            /*cursor: pointer;*/
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .register__item{
            width: 400px;
            height: 900px;
            padding: 20px;
            text-align: center;
            background-color: #F5F5F5;
            border-radius: 5px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1), 0 10px 20px rgba(0, 0, 0, 0.5);
        }
        .text__item{
            width: 340px;
            height: 16px;
            outline: none;
            padding: .75rem;
            filter: none;
            margin-bottom: 30px;
            margin-bottom: 30px;
        }
        .btn__register{
            width: 365px;
            height: 40px;
            margin-bottom: 30px;
            background-color: coral;
            box-shadow: 0 1px 1px rgba(0, 0, 0, .09);
            color: #fff;
            border: solid 1px black;
            border-radius: 2px;
            font-weight: bold;
            font-size: 20px;
        }
        .register__or {
            display: flex;
            align-items: center;
            text-align: center;
            margin: 20px 0;
            color: #ccc;
        }
        .register__or::before,
        .register__or::after {
            content: "";
            flex: 1;
            border-bottom: 1px solid #ccc;
            margin-left: 20px;
            margin-right: 20px;
        }
        .register__or:not(:empty)::before {
            margin-right: 10px;
        }
        .register__or:not(:empty)::after {
            margin-left: 10px;
        }
        .register__or span {
            font-size: 20px;
            font-weight: bold;
            color: #777;
        }
        .register__social{
            height: 50px;
            margin-bottom: 30px;
        }
        .social__fb{
            width: 165px;
            height: 40px;
            float: left;
            border: solid 1px black;
            background-color: #F5F5F5;
            margin-left: 15px;
        }
        .social__google{
            width: 165px;
            height: 40px;
            float: right;
            border: solid 1px black;
            background-color: #F5F5F5;
            margin-right: 15px;
        }
        .register__login{
            color: brown;
        }
    </style>
</head>
<body>
<header><jsp:include page="header.jsp"></jsp:include></header>
<div class="container__register">
    <div class="register__item">
        <h1>Đăng ký</h1>
        <form action="register" method="post">
        <input name="username" class="text__item" type="text" placeholder="Tên đăng nhập">
        <input name="password" class="text__item" type="password" placeholder="Mật khẩu">
        <input name="" class="text__item" type="password" placeholder="Nhập lại mật khẩu">
        <input name="email" class="text__item" type="text" placeholder="Email">
        <input name="firstName" class="text__item" type="text" placeholder="Họ">
        <input name="lastName" class="text__item" type="text" placeholder="Tên">
        <input name="address" class="text__item" type="text" placeholder="Địa chỉ">
        <input name="phoneNumber" class="text__item" type="text" placeholder="Số điện thoại">
        <button type="submit" class="btn__register">Đăng ký</button>
        </form>
        <div class="register__or">Hoặc</div>
        <div class="register__social">
            <button class="social__fb">
                <i class="fa-brands fa-facebook" style="color: blue"></i>
                Facebook
            </button>
            <button class="social__google">
                <i class="fa-brands fa-google" style="color: red"></i>
                Google
            </button>
        </div>
        <a href="login.jsp" class="register__login">
            Đăng nhập
        </a>
    </div>
</div>
<footer><jsp:include page="foodter.jsp"></jsp:include></footer>
</body>
</html>