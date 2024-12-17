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
            <label for="recipientName">Tên Người Nhận:</label>
            <input type="text" id="recipientName" name="recipientName" required style="width: 840px; height: 45px; border-radius: 10px; border: 1px solid black"><br><br>
        </div>

        <div class="inf">
            <div class="inf__left">

                <div class="left__address">
                    <label for="shippingAddress">Địa Chỉ Giao Hàng:</label>
                    <input type="text" id="shippingAddress" name="shippingAddress" required style="width: 390px; height: 45px; border-radius: 10px; border: 1px solid black"><br><br>
                </div>
                <div class="left__district">
                    <label for="notes">Ghi Chú:</label>
                    <textarea id="notes" name="notes"></textarea><br><br>
                </div>

            </div>
            <div class="inf__right">
                <div class="right__phone">
                    <label for="shippingPhoneNumber">Số Điện Thoại:</label>
                    <input type="text" id="shippingPhoneNumber" name="shippingPhoneNumber" required style="width: 390px; height: 45px; border-radius: 10px; border: 1px solid black"><br><br>
                </div>
            </div>
        </div>

    </div>

    <h3>Chọn Phương Thức Thanh Toán</h3>
    <div>
        <input type="radio" id="creditCard" name="paymentMethod" value="Credit Card">
        <label for="creditCard">Thẻ Tín Dụng</label><br>

        <input type="radio" id="cashOnDelivery" name="paymentMethod" value="Cash On Delivery" checked>
        <label for="cashOnDelivery">Thanh Toán Khi Nhận Hàng</label><br>
    </div>

    <div class="checkout_right">
        <h2>Sản phẩm trong giỏ hàng</h2>

<h1>Thông tin thanh toán</h1>


    <h2>Sản phẩm trong giỏ hàng</h2>
    <c:if test="${not empty sessionScope.cartSession}">
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
            <c:forEach var="item" items="${sessionScope.cartSession}">
                <tr>
                    <td>${item.product.name}</td>
                    <td>${item.quantity}</td>
                    <td>${item.product.price}</td>
                    <td>${item.quantity * item.product.price}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <p>${orderDate}</p>
        <h3>Tổng cộng: ${totalAmount}</h3>


    </div>
        <button type="submit">Thanh toán</button>
    </c:if>
    <c:if test="${empty sessionScope.cartSession}">
        <p>Giỏ hàng của bạn đang trống!</p>
    </c:if>

</form>
<script>
    document.querySelector('.nav').style.display = 'none';
</script>
<script src="chechout.js"></script>
</body>
</html>
