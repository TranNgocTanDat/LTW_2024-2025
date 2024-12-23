<%--
  Created by IntelliJ IDEA.
  User: Danh Nguyen
  Date: 9/10/2024
  Time: 1:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Double D</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <style>
        .footer{
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            text-align: center;
            padding: 30px;
            height: 400px;
            background-color: #002D62;
            color: white;
            /*margin-top: 10px;*/
        }
        .footer__left{
            text-align: left;
        }
        ul{
            padding: 0px;
        }
        li{
            display: block;
            margin-bottom: 20px;
        }
        li:hover{
            color: brown;
            cursor: pointer;
        }
        h3{
            text-decoration: underline;
            margin-bottom: 20px;
            font-size: 30px;
            margin-top: 0px;
        }
        .footer__sp{
            margin-left: 30px;
        }
        .footer__adr{
            margin-left: 45px;
        }
        .footer__fl{
            margin-left: 60px;
        }
    </style>
</head>
<body>
    <div class="footer">
        <div class="footer__mkt footer__left">
            <div class="footer__mkt--logo">

            </div>
            <ul class="list__mkt">
                <li>
                    <i class="fa-solid fa-hand-point-right"></i>
                    Giới thiệu
                <li>
                <li>
                    <i class="fa-solid fa-hand-point-right"></i>
                    Liên hệ
                <li>
                <li>
                    <i class="fa-solid fa-hand-point-right"></i>
                    Tuyển dụng
                <li>
                <li>
                    <i class="fa-solid fa-hand-point-right"></i>
                    Tin tức
                <li>
                <li>
                    <i class="fa-solid fa-hand-point-right"></i>
                    Gmail: doubleD@gamil.com
                <li>
                <li>
                    <i class="fa-solid fa-hand-point-right"></i>
                    Hotline: 0868849543
                <li>
            </ul>
        </div>
        <div class="footer__sp footer__left">
            <h3>Hỗ trợ khách hàng</h3>
            <ul class="list__sp">
                <li><i class="fa-solid fa-forward" style="width: 15px; height: 18px; margin-right: 10px"></i>
                    Hướng dẫn đặt hàng</li>
                <li><i class="fa-solid fa-forward" style="width: 15px; height: 18px; margin-right: 10px"></i>
                    Hướng dẫn chọn size</li>
                <li><i class="fa-solid fa-forward" style="width: 15px; height: 18px; margin-right: 10px"></i>
                    Câu hỏi thường gặp</li>
                <li><i class="fa-solid fa-forward" style="width: 15px; height: 18px; margin-right: 10px"></i>
                    Chính sách khách VIP</li>
                <li><i class="fa-solid fa-forward" style="width: 15px; height: 18px; margin-right: 10px"></i>
                    Thanh toán - Giao hàng</li>
                <li><i class="fa-solid fa-forward" style="width: 15px; height: 18px; margin-right: 10px"></i>
                    Chính sách đổi hàng</li>
                <li><i class="fa-solid fa-forward" style="width: 15px; height: 18px; margin-right: 10px"></i>
                    Chính sách bảo mật</li>
                <li><i class="fa-solid fa-forward" style="width: 15px; height: 18px; margin-right: 10px"></i>
                    Chính sách cookie</li>
            </ul>
        </div>
        <div class="footer__adr footer__left">
            <h3>Hệ thống cửa hàng</h3>
            <ul class="list__adr">
                <li><i class="fa-solid fa-forward" style="width: 15px; height: 18px; margin-right: 10px"></i>
                    18 Linh Chiểu, Thủ Đức</li>
                <li><i class="fa-solid fa-forward" style="width: 15px; height: 18px; margin-right: 10px"></i>
                    12 Xô Viết Nghệ Tĩnh, Bình Thạnh</li>
                <li><i class="fa-solid fa-forward" style="width: 15px; height: 18px; margin-right: 10px"></i>
                    05 Đồng Đen, Tân Phú</li>
            </ul>
        </div>
        <div class="footer__fl footer__left">
            <h3>Kết nối với DoubleD</h3>
            <ul class="list__fl">
                <li>
                    <i class="fa-brands fa-facebook"></i>
                    Facebook: Double D Shop

                </li>
                <li>
                    <i class="fa-brands fa-instagram" style="font-size: 20px"></i>
                    Instagram: Double D Shop

                </li>
                <li>
                    <i class="fa-brands fa-youtube"></i>
                    Youtobe: Double D Review

                </li>
            </ul>
        </div>
    </div>
</body>
</html>
