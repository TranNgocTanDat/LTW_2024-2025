<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Checkout</title>
    <style>
        .checkout {
            padding-bottom: 220px; /* Khoảng trống đủ lớn để không bị footer che khuất */
            box-sizing: border-box; /* Đảm bảo tính toán đúng chiều rộng và chiều cao */
            min-height: calc(100vh - 0px); /* Đảm bảo chiều cao tối thiểu đủ lớn */

        }

        .checkout__left {
            width: 60%;
            height: 410px;
            margin-left: 30px;
            border-bottom: black 1px dashed;
        }

        .checkout_right {
            float: right;
            position: absolute;
            left: 980px;
            top: 270px;
            border-bottom: black 1px dashed;
        }

        h2 {
            margin: 0px;
        }

        label {
            color: #999999;
            font-size: 15px;
            margin-left: 10px;
        }

        .inf {
        }

        .fullname {
            display: grid;
            margin-bottom: 20px;
        }

        .inf__left {
            width: 50%;
            float: left;
        }

        .left__mail {
            display: grid;
            margin-bottom: 20px;
        }

        .left__address {
            display: grid;
            margin-bottom: 20px;
        }

        .inf__right {
            width: 50%;
            float: right;
        }

        .right__phone {
            display: grid;
            margin-bottom: 20px;
        }

        .right__city {
            display: grid;
            margin-bottom: 20px;
        }

        .right__ward {
            display: grid;
            margin-bottom: 20px;
        }

        /* Phần footer */
        /* Footer cố định */
        .footer__checkout {
            position: fixed; /* Cố định footer ở cuối màn hình */
            bottom: 0; /* Nằm sát đáy */
            width: 100%; /* Chiếm toàn bộ chiều ngang */
            height: 150px; /* Chiều cao cố định */
            background-color: #f9f9f9; /* Màu nền */
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 9; /* Ưu tiên hiển thị trên nội dung */
            box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.1); /* Đổ bóng nhẹ */
        }

        /* Nội dung nút */
        .cart,
        .pay {
            width: 330px;
            height: 60px;
            font-size: 25px;
            border: none;
            cursor: pointer;
            border-radius: 5px;
        }

        .cart {
            background-color: #f0f0f0;
            color: #000;
            display: flex;
            justify-content: center;
            align-items: center;
            margin-right: 100px;
        }

        .pay {
            background-color: #000;
            color: #fff;
        }

        .product__cart {
            width: 480px;
            height: auto;
            background-color: #d9d9d9;
            display: grid;
            justify-content: center;
            align-items: center;
            border-radius: 20px;
            box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.1);
            margin-top: 20px;
        }

        .product__cart--top {
            display: flex;
            margin-top: 20px;
            margin-bottom: 20px;
        }

        .checkout__bottom {
            width: 60%;
            text-align: center;
            margin-top: 20px;
        }

        .checkout__bottom--title {
            font-size: 30px;
            font-weight: bold;
            margin-bottom: 20px;
        }

        .radio__chooser {
            display: flex;
            justify-content: left;
            align-items: center;
            margin-left: 30px;
            margin-bottom: 20px;
        }

        img {
            margin-left: 10px;
            margin-right: 10px;
        }
    </style>

</head>

<body>
<header>
    <jsp:include page="header.jsp"></jsp:include>
</header>
<h1 style="margin-bottom: 20px; text-align: center">Thông tin thanh toán</h1>
<form action="checkout" method="post" class="checkout">
    <div class="checkout__left">
        <div class="fullname">
            <label for="recipientName">Người nhận:</label>
            <input type="text" id="recipientName" name="recipientName" required style="width: 840px; height: 45px; border-radius: 10px; border: 1px solid black"><br><br>
        </div>

        <div class="inf">
            <div class="inf__left">
                <div class="left__mail">
                    <label>Email</label>
                    <input type="text" name="mail" required style="width: 390px; height: 45px; border-radius: 10px; border: 1px solid black"><br>
                </div>
                <div class="left__address">
                    <label for="shippingAddress">Địa Chỉ Giao Hàng:</label>
                    <input type="text" id="shippingAddress" name="shippingAddress" required
                           style="width: 390px; height: 45px; border-radius: 10px; border: 1px solid black"><br><br>
                </div>
                <div class="left__district">
                    <label>QUẬN/HUYỆN</label>
                    <select id="district" style="width: 390px; height: 45px; border-radius: 10px; border: 1px solid black">
                        <option value="" >-- Chọn quận / huyện --</option>
                    </select>
                </div>
                <div class="left__note">
                    <label for="notes">Ghi Chú:</label>
                    <textarea id="notes" name="notes"></textarea><br><br>
                </div>

            </div>
            <div class="inf__right">
                <div class="right__phone">
                    <label for="shippingPhoneNumber">Số Điện Thoại:</label>
                    <input type="text" id="shippingPhoneNumber" name="shippingPhoneNumber" required
                           style="width: 390px; height: 45px; border-radius: 10px; border: 1px solid black"><br><br>
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

        <h2 style="text-align: center; font-size: 30px">Sản phẩm trong giỏ hàng</h2>
        <div class="product__cart">
            <c:forEach var="item" items="${cartItems}">
                <c:if test="${empty sessionScope.cartSession}">
                    <p>Giỏ hàng của bạn đang trống!</p>
                </c:if>
                <div class="product__cart--top">
                    <img class="product__img" src="${item.product.imageUrl}"
                         style="width: 80px; height: 80px; background-color: #999999; border-radius: 10px; margin-right: 40px">
                    <div class="cart__top--left">
                        <div class="product__name">${item.product.name}</div>
                        <div class="product__quantity" style="margin-top: 10px">Số lượng: ${item.quantity}</div>
                        <div class="peoduct__price"
                             style="margin-top: 10px; font-weight: bold; font-size: 20px">${item.quantity * item.product.price}</div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="totalAmount" style="display: flex; justify-content: space-between">
            <h3 style="font-size: 30px">Tổng cộng: </h3>
            <h3 style="font-size: 30px">${totalAmount}</h3>
        </div>
    </div>
    <div class="checkout__bottom">
        <div class="checkout__bottom--title">Phương thức thanh toán</div>

        <div class="radio__chooser">
            <input type="radio" id="momo" name="paymentMethod" value="Credit Card">
            <img src="https://file.hstatic.net/1000284478/file/momo-45_eee48d6f0f9e41f1bd2c5f06ab4214a2.svg"
                 style="width: 40px; height: 40px">
            <div class="radio__chooser--titel">
                <label for="momo">Thẻ Tín Dụng</label><br>
            </div>
        </div>
        <div class="radio__chooser">
            <input type="radio" id="creditCard" name="paymentMethod" value="Credit Card">
            <img src="https://file.hstatic.net/1000284478/file/visa-43_bc000e2615304f8690da9e32431cb099.svg"
                 style="width: 40px; height: 40px">
            <div class="radio__chooser--titel">
                <label for="creditCard">Thẻ Tín Dụng</label><br>
            </div>
        </div>
        <div class="radio__chooser">
            <input type="radio" id="cashOnDelivery" name="paymentMethod" value="Cash On Delivery" checked>
            <img src="https://file.hstatic.net/1000284478/file/cod_icon-47_a8768752c1a445da90d600ca0a94675c.svg"
                 style="width: 40px; height: 40px">
            <div class="radio__chooser--titel">
                <label for="cashOnDelivery">Thanh Toán Khi Nhận Hàng</label><br>
            </div>
        </div>
    </div>
    <div class="footer__checkout">
        <a href="cart" class="cart">
            Quay lại giỏ hàng
        </a>
        <button type="submit" class="pay">Thanh toán</button>
    </div>
</form>
<script>
    document.querySelector('.nav').style.display = 'none';
</script>
<script src="chechout.js"></script>
</body>
</html>