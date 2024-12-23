<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh Sách Đơn Hàng</title>
    <style>
        /* Định dạng tổng thể */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
        }

        h1 {
            text-align: center;
            color: #333;
            margin: 20px 0;
        }

        /* Container cho danh sách đơn hàng */
        .order-container {
            max-width: 1200px;
            margin: 20px auto;
            padding: 20px;
            display: flex;
            flex-direction: column;
            gap: 20px;
        }

        /* Định dạng thẻ chứa từng đơn hàng */
        .order-card {
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .order-card h2 {
            margin-top: 0;
            color: #007bff;
            font-size: 1.5em;
        }

        /* Layout 2 cột */
        .order-details {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }

        /* Phần danh sách sản phẩm */
        .product-list {
            display: flex;
            flex-direction: column;
            gap: 10px;
        }

        .product-item {
            display: flex;
            gap: 15px;
            align-items: center;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: #f8f8f8;
        }

        .product-item img {
            width: 60px;
            height: 60px;
            object-fit: cover;
            border-radius: 4px;
            border: 1px solid #ccc;
        }

        .product-info {
            flex: 1;
            display: flex;
            flex-direction: column;
            gap: 5px;
        }

        .product-info span {
            font-size: 0.9em;
            color: #555;
        }

        .product-info span:first-child {
            font-weight: bold;
            color: #333;
        }

        /* Thông tin đơn hàng */
        .order-info {
            display: flex;
            flex-direction: column;
            gap: 10px;
        }

        .order-info p {
            margin: 0;
            font-size: 1em;
            color: #333;
        }

        /* Nút toggle */
        .toggle-button {
            display: inline-block;
            margin-top: 10px;
            padding: 8px 15px;
            font-size: 0.9em;
            color: #fff;
            background-color: #007bff;
            text-align: center;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }

        .toggle-button:hover {
            background-color: #0056b3;
        }

        /* Các trạng thái đơn hàng */
        .status {
            font-weight: bold;
            margin-top: 10px;
        }

        .status.edited {
            color: #28a745;
        }

        .status.not-edited {
            color: #dc3545;
        }

        /* Responsive cho màn hình nhỏ */
        @media (max-width: 768px) {
            .order-details {
                grid-template-columns: 1fr;
            }

            .product-item {
                flex-direction: column;
                align-items: flex-start;
            }

            .product-item img {
                margin-bottom: 10px;
            }
        }

        .product-item.extra {
            display: none;
        }
    </style>
    <script>

        function toggleProductList(orderId) {
            const productList = document.getElementById(`productList${orderId}`);
            const extraProducts = productList.querySelectorAll('.product-item.extra');
            const toggleButton = document.getElementById(`toggleButton${orderId}`);

            // Kiểm tra trạng thái hiển thị của sản phẩm đầu tiên
            const isHidden = Array.from(extraProducts).some(product => product.style.display === "none" || product.style.display === "");

            extraProducts.forEach(product => {
                product.style.display = isHidden ? "block" : "none"; // Hiển thị hoặc ẩn sản phẩm
            });

            toggleButton.textContent = isHidden ? "Thu gọn" : "Xem thêm";
        }
    </script>
</head>
<body>
<h1>Danh Sách Đơn Hàng</h1>
<div class="order-container">
    <c:forEach var="order" items="${orders}">
        <div class="order-card">
            <h2>Đơn Hàng #${order.orderId}</h2>
            <div class="order-details">
                <div class="order-info">
                    <p><strong>Khách Hàng:</strong> ${order.recipientName}</p>
                    <p><strong>Địa Chỉ:</strong> ${order.shippingAddress}</p>
                    <p><strong>Số Điện Thoại:</strong> ${order.shippingPhoneNumber}</p>
                    <p class="status ${order.is_edited ? 'edited' : 'not-edited'}">
                            ${order.is_edited ? 'Đã chỉnh sửa' : 'Chưa chỉnh sửa'}
                    </p>
                </div>
                <div>
                    <div class="product-list" id="productList${order.orderId}">
                        <c:forEach var="item" items="${orderItems}" varStatus="status">
                            <div class="product-item ${status.index >= 1 ? 'extra' : ''}">
                                <img src="${item.product.imageUrl}" alt="${item.product.name}">
                                <div class="product-info">
                                    <span>${item.product.name}</span>
                                    <span>SL: ${item.quantity}</span>
                                    <span>Giá: ${item.product.price} VNĐ</span>
                                </div>
                            </div>
                        </c:forEach>
                        <a class="toggle-button" href="${pageContext.request.contextPath}/ordered?orderId=${order.orderId}">
                            Xem chi tiết
                        </a>
                    </div>

                </div>

            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>
