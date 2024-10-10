<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
<%--    <link rel="stylesheet" href="styles.css">--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        body{
            margin: 0px;
        }
        .container__login{
            width: 100%;
            height: 800px;
            background-image: url("https://4menshop.com/images/thumbs/slides/slide-2-trang-chu-slide-2.png?t=1726737059");
            margin-top: -50px;
        }
        .nav{
            display: none;
        }
        .login__item{
            width: 400px;
            height: 500px;
            padding: 20px;
            background-color: #F5F5F5;
            text-align: center;
            position: absolute;
            top: 240px;
            left: 100px;
            border: solid 1px black;
            border-radius: 2px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1), 0 10px 20px rgba(0, 0, 0, 0.5);
        }
        .login__texx{
            width: 340px;
            height: 16px;
            outline: none;
            padding: .75rem;
            filter: none;
            margin-bottom: 30px;
        }
        .login__texx:hover{
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1), 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .btn__login{
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
        .login__or {
            display: flex;
            align-items: center;
            text-align: center;
            margin: 20px 0;
            color: #ccc;
        }
        .login__or::before,
        .login__or::after {
            content: "";
            flex: 1;
            border-bottom: 1px solid #ccc;
            margin-left: 20px;
            margin-right: 20px;
        }
        .login__or:not(:empty)::before {
            margin-right: 10px;
        }
        .login__or:not(:empty)::after {
            margin-left: 10px;
        }
        .login__or span {
            font-size: 20px;
            font-weight: bold;
            color: #777;
        }
        .login__logo{
            height: 50px;
            margin-bottom: 30px;
        }
        .btn__fb{
            width: 165px;
            height: 40px;
            float: left;
            border: solid 1px black;
            background-color: #F5F5F5;
            margin-left: 15px;
        }
        .btn__gmail{
            width: 165px;
            height: 40px;
            float: right;
            border: solid 1px black;
            background-color: #F5F5F5;
            margin-right: 15px;
        }
        .login__dk{
            color: brown;
        }
    </style>
</head>
<body>

<%--    <label for="username">Username:</label>--%>
<%--    <input type="text" id="username" name="username" required><br/>--%>

<%--    <label for="password">Password:</label>--%>
<%--    <input type="password" id="password" name="password" required><br/>--%>

<%--    <input type="submit" >--%>
    <header><jsp:include page="header.jsp"></jsp:include></header>
    <div class="container__login">
        <div class="login__item">
            <h1>Đăng nhập</h1>
            <form action="login" method="post">
            <input type="text" name="username" placeholder="Email/Số điện thoại/ Tên đăng nhập" class="login__texx">
            <input type="password" name="password" placeholder="Mật Khẩu" class="login__texx">
            <button type="submit" class="btn__login">Đăng nhập</button>
            </form>
            <div class="login__sp">
                <div class="login__sp--left">Quên mật khẩu</div>
            </div>
            <div class="login__or">Hoặc</div>
            <div class="login__logo">
                <button class="btn__fb">
                    <i class="fa-brands fa-facebook" style="color: blue"></i>
                    Facebook
                </button>
                <button class="btn__gmail">
                    <i class="fa-brands fa-google" style="color: red"></i>
                    Google
                </button>
            </div>
            <a href="register">
                <div class="login__dk ">
                Đăng kí
                </div>
            </a>
        </div>
    </div>
    <footer><jsp:include page="foodter.jsp"></jsp:include></footer>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            const currentPage = window.location.pathname; // Lấy đường dẫn hiện tại
            const nav = document.querySelector('.nav'); // lấy ra thẻ nav

            // Kiểm tra nếu đang ở trang login.jsp
            if (currentPage.includes("login")) {
                nav.style.display = 'none'; // Ẩn thẻ nav
            }
            console.log(currentPage); // Kiểm tra đường dẫn hiện tại trong console
        });
    </script>


<c:if test="${not empty errorMessage}">
    <p style="color: red;">${errorMessage}</p>
</c:if>

</body>
</html>
