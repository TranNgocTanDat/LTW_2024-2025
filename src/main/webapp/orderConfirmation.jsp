<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" />
    <title>Order Confirmation</title>
    <style>
        .container__title{
            display: grid;
            justify-content: center;
            align-items: center;
        }
        .btn__bottom{
            position: fixed; /* Cố định footer ở cuối màn hình */
            bottom: 0;       /* Nằm sát đáy */
            width: 100%;     /* Chiếm toàn bộ chiều ngang */
            height: 150px;   /* Chiều cao cố định */
            background-color: #f9f9f9; /* Màu nền */
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 9;   /* Ưu tiên hiển thị trên nội dung */
            box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.1); /* Đổ bóng nhẹ */
        }
        .down, .sign{
            width: 330px;
            height: 60px;
            font-size: 20px;
            border: none;
            cursor: pointer;
            border-radius: 5px;
        }
        .sign{
            background-color: #4CAF50;
            color: #fff;
        }
        .down{
            background-color: #f0f0f0;
            color: #000;
            display: flex;
            justify-content: center;
            align-items: center;
            margin-right: 100px;
        }

    </style>
</head>
<body>
    <header><jsp:include page="header.jsp"></jsp:include></header>
    <div class="container__title">
        <i class="fa-solid fa-circle-check icon" style="color: #4CAF50; text-align: center; font-size: 50px"></i>
        <h2>Đơn hàng của bạn đã được tạo thành công!</h2>
    </div>

<p>Thông tin đơn hàng:</p>
<ul>
    <li>Mã đơn hàng: ${order.orderId}</li>
    <li>Khách hàng:  ${order.recipientName}</li>
    <li>Địa chỉ: ${order.shippingAddress}</li>
    <li>Số điện thoại: ${order.shippingPhoneNumber}</li>
    <li>Tổng tiền thanh toán: ${order.totalAmount}</li>
</ul>
<c:forEach var="item" items="${cartItems}">
    <p>Product: ${item.product.name}, Quantity: ${item.quantity}, Price: ${item.product.price}</p>
</c:forEach>
    <p style="color: #f54545; font-weight: bold">Cám ơn bạn đã mua hàng tại Double D, vui lòng kí xác thực đơn hàng !</p>
    <div class="btn__bottom">
        <button id="downloadOrderTxt" class="down">Tải về file TXT thông tin đơn hàng</button>
        <button id="goToSignPage" class="sign">Ký và Xác Thực Đơn Hàng</button>
    </div>


<script>
    document.querySelector('.nav').style.display = 'none';
    var products = ${cartItemsJson};
    // Hàm để tạo file TXT
    document.getElementById('downloadOrderTxt').addEventListener('click', function() {

        // Tạo nội dung file TXT
        var content =
            `${order.order_content}\n` ;
        console.log(content)
        // Tạo Blob và tải xuống file TXT
        var blob = new Blob([content], { type: "text/plain;charset=utf-8" });
        var link = document.createElement("a");
        link.href = URL.createObjectURL(blob);
        link.download = "order_12345.txt";
        link.click();
    });
    document.getElementById('goToSignPage').addEventListener('click', function() {
        // Chuyển hướng sang trang ký và xác thực đơn hàng
        window.location.href = `${pageContext.request.contextPath}/signOrder?orderId=${order.orderId}`;
    });
</script>

</body>
</html>
