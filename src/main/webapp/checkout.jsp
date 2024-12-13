<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Checkout</title>
    <style>
        .checkout__left{
            width: 60%;
        }
        .checkout_right{
            float: right;
            position: absolute;
            left: 900px;
            top: 300px;
        }
        h1, h2{
            margin: 0px;
        }
        label{
            color: #999999;
            font-size: 15px;
            margin-left: 10px;
        }
        .inf{
        }
        .fullname{
            display: grid;
            margin-bottom: 20px;
        }
        .inf__left{
            width: 50%;
            float: left;
        }
        .left__mail{
            display: grid;
            margin-bottom: 20px;
        }
        .left__address{
            display: grid;
            margin-bottom: 20px;
        }
        .inf__right{
            width: 50%;
            float: right;
        }
        .right__phone{
            display: grid;
            margin-bottom: 20px;
        }
        .right__city{
            display: grid;
            margin-bottom: 20px;
        }
        .right__ward{
            display: grid;
            margin-bottom: 20px;
        }
    </style>
</head>

<body>

<header><jsp:include page="header.jsp"></jsp:include></header>
<h1 style="margin-bottom: 20px; text-align: center">Thông tin thanh toán</h1>
<form action="checkout" method="post" class="checkout">
    <div class="checkout__left">
        <div class="fullname">
            <label>HỌ VÀ TÊN</label>
            <input type="text" name="fullName" required style="width: 840px; height: 45px; border-radius: 10px; border: 1px solid black">
        </div>

        <div class="inf">
            <div class="inf__left">
                <div class="left__mail">
                    <label>Email</label>
                    <input type="text" name="mail" required style="width: 390px; height: 45px; border-radius: 10px; border: 1px solid black"><br>
                </div>
                <div class="left__address">
                    <label>Địa chỉ giao hàng</label>
                    <input type="text" name="shippingAddress" required style="width: 390px; height: 45px; border-radius: 10px; border: 1px solid black"><br>
                </div>
                <div class="left__district">
                    <label>QUẬN/HUYỆN</label>
                    <select id="district" style="width: 390px; height: 45px; border-radius: 10px; border: 1px solid black">
                        <option value="" >-- Chọn quận / huyện --</option>
                    </select>
                </div>

            </div>
            <div class="inf__right">
                <div class="right__phone">
                    <label>SỐ ĐIỆN THOẠI</label>
                    <input type="text" name="mail" required style="width: 390px; height: 45px; border-radius: 10px; border: 1px solid black"><br>
                </div>
                <div class="right__city">
                    <label for="city">Chọn thành phố:</label>
                    <select id="city" onchange="updateDistricts()" style="margin-bottom: 20px;width: 390px; height: 45px; border-radius: 10px; border: 1px solid black">
                        <option value="">-- Chọn thành phố --</option>
                        <option value="hcm">Thành phố Hồ Chí Minh</option>
                        <option value="hn">Hà Nội</option>
                        <option value="dn">Đà Nẵng</option>
                    </select>
                </div>
                <div class="right__ward">
                    <label>PHƯỜNG/XÃ</label>
                    <select id="ward" style="width: 390px; height: 45px; border-radius: 10px; border: 1px solid black">
                        <option value="">-- Chọn phường / xã --</option>
                    </select>
                </div>

            </div>
        </div>

    </div>

    <div class="checkout_right">
        <h2>Sản phẩm trong giỏ hàng</h2>

<h1>Thông tin thanh toán</h1>
<form action="checkout" method="post">
    <label>Địa chỉ giao hàng:</label>
    <input type="text" name="shippingAddress" required><br>

    <h2>Sản phẩm trong giỏ hàng</h2>
    <c:if test="${not empty cartItems}">
        <table border="1">
            <thead>
            <tr>
                <th>Tên sản phẩm</th>
                <th>Số lượng</th>
                <th>Giá</th>
                <th>Thành tiền</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${cartItems}">
                <tr>
                    <td>${item.product.name}</td>
                    <td>${item.quantity}</td>
                    <td>${item.product.price}</td>
                    <td>${item.quantity * item.product.price}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <h3>Tổng cộng: ${totalAmount}</h3>


    </div>


        <button type="submit">Thanh toán</button>
    </c:if>
    <c:if test="${empty cartItems}">
        <p>Giỏ hàng của bạn đang trống!</p>
    </c:if>

</form>

<script>
    document.querySelector('.nav').style.display = 'none';
</script>
<script src="chechout.js"></script>
</body>
</html>
