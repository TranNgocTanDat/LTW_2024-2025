<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmation</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
            line-height: 1.6;
            color: #333;
        }

        .container {
            max-width: 1000px;
            margin: 40px auto;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }

        .title {
            background-color: #007bff;
            color: #fff;
            padding: 15px;
            text-align: center;
            font-size: 1.8rem;
            font-weight: bold;
        }

        .content {
            display: flex;
            flex-wrap: wrap;
            padding: 20px;
            gap: 20px;
        }

        .order-info, .product-list {
            flex: 1;
            min-width: 300px;
            background-color: #f7f9fc;
            padding: 20px;
            border-radius: 8px;
            box-shadow: inset 0 0 10px rgba(0, 0, 0, 0.05);
        }

        h2 {
            font-size: 1.5rem;
            margin-bottom: 15px;
            color: #2c3e50;
        }

        ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        li {
            margin-bottom: 10px;
            padding: 8px;
            border-bottom: 1px dashed #ddd;
            font-size: 1rem;
        }

        li:last-child {
            border-bottom: none;
        }

        .product-item {
            display: flex;
            align-items: center;
            margin-bottom: 15px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eaeaea;
        }

        .product-item:last-child {
            border-bottom: none;
        }

        .product-item img {
            width: 60px;
            height: 60px;
            object-fit: cover;
            border-radius: 6px;
            margin-right: 15px;
        }

        .product-info {
            display: flex;
            justify-content: space-between;
            flex: 1;
            font-size: 0.9rem;
            color: #555;
        }

        .total-amount {
            margin: 20px 0;
            text-align: center;
            font-size: 1.3rem;
            color: #007bff;
            font-weight: bold;
        }

        .buttons {
            text-align: center;
            padding: 20px;
            background-color: #f1f3f6;
            border-top: 1px solid #ddd;
        }

        button {
            background-color: #007bff;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1rem;
            margin: 0 10px;
            transition: all 0.3s ease-in-out;
        }

        button:hover {
            background-color: #0056b3;
            transform: translateY(-2px);
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }

    </style>
</head>
<body>

<div class="container">
    <div class="title">
        Đơn hàng của bạn đã được tạo thành công!
    </div>

    <div class="content">
        <div class="order-info">
            <h2>Thông tin đơn hàng</h2>
            <ul>
                <li>Mã đơn hàng: ${order.orderId}</li>
                <li>Khách hàng:  ${order.recipientName}</li>
                <li>Địa chỉ: ${order.shippingAddress}</li>
                <li>Số điện thoại: ${order.shippingPhoneNumber}</li>
            </ul>
        </div>

        <div class="product-list">
            <h2>Danh sách sản phẩm</h2>
            <c:forEach var="item" items="${cartItems}">
                <div class="product-item">
                    <img src="${item.product.imageUrl}" alt="${item.product.name}">
                    <div class="product-info">
                        <span>${item.product.name}</span>
                        <span>SL: ${item.quantity}</span>
                        <span>Giá: ${item.product.price}</span>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <div class="total-amount">
        Tổng tiền thanh toán: ${order.totalAmount} VND
    </div>

    <div class="buttons">
        <button id="downloadOrderTxt">Tải về file TXT thông tin đơn hàng</button>
        <button id="openModal">Ký và Xác Thực Đơn Hàng</button>
    </div>
</div>

<script>
    var products = ${cartItemsJson};

    document.getElementById('downloadOrderTxt').addEventListener('click', function() {
        var content = `${order.order_content}\n`;
        var blob = new Blob([content], { type: "text/plain;charset=utf-8" });
        var link = document.createElement("a");
        link.href = URL.createObjectURL(blob);
        link.download = "order_12345.txt";
        link.click();
    });

    document.getElementById('openModal').addEventListener('click', function() {
        // document.getElementById('signModal').style.display = 'flex';
        window.location.href = `${pageContext.request.contextPath}/signOrder?orderId=${order.orderId}`;
    });
</script>

</body>
</html>
