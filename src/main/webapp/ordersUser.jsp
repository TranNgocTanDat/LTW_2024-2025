<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh Sách Đơn Hàng</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f9;
            margin: 0;
            padding: 20px;
        }

        h1 {
            text-align: center;
            color: #333;
        }

        .order-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 20px;
        }

        .order-card {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            width: 400px;
            padding: 20px;
            position: relative;
        }

        .order-card h2 {
            margin-top: 0;
            color: #007bff;
        }

        .order-details {
            margin: 10px 0;
        }

        .order-details p {
            margin: 5px 0;
            color: #555;
        }

        .status {
            font-weight: bold;
        }

        .status.edited {
            color: green;
        }

        .status.not-edited {
            color: red;
        }

        .actions {
            display: flex;
            justify-content: space-between;
            margin-top: 15px;
        }

        .actions button, .actions a {
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            color: #fff;
            transition: background-color 0.3s ease;
            font-size: 0.9rem;
        }

        .edit-button {
            background-color: #28a745;
        }

        .edit-button:hover {
            background-color: #218838;
        }

        .delete-button {
            background-color: #dc3545;
        }

        .delete-button:hover {
            background-color: #c82333;
        }

        /* Thông báo */
        .message {
            text-align: center;
            margin-bottom: 20px;
            padding: 10px;
            border-radius: 5px;
        }

        .message.success {
            background-color: #d4edda;
            color: #155724;
        }

        .message.error {
            background-color: #f8d7da;
            color: #721c24;
        }


        .product-list {
            max-height: 150px;
            overflow: hidden;
            transition: max-height 0.3s ease-in-out;
        }

        .product-list.expanded {
            max-height: 1000px; /* Giá trị lớn để hiển thị tất cả sản phẩm */
        }

        .product-item {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }

        .product-item img {
            width: 50px;
            height: 50px;
            object-fit: cover;
            margin-right: 10px;
        }

        .product-item .product-info {
            display: flex;
            flex-direction: column;
        }

        .product-item .product-info span {
            font-size: 14px;
            color: #555;
        }

        /* Modal Styles */
        .modal {
            display: none; /* Ẩn modal mặc định */
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.4); /* Màu nền trong suốt */
            justify-content: center;
            align-items: center;
        }

        .modal-content {
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            width: 400px;
            position: relative;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
        }

        .close {
            position: absolute;
            top: 15px;
            right: 20px;
            color: #aaa;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
        }

        .close:hover, .close:focus {
            color: #000;
            text-decoration: none;
        }
        .toggle-button {
             margin-top: 10px;
             display: block;
             text-align: center;
             background-color: #007bff;
             color: white;
             padding: 10px;
             border: none;
             border-radius: 5px;
             cursor: pointer;
         }

        .toggle-button:hover {
            background-color: #0056b3;
        }

        .product-item.extra {
            display: none; /* Đảm bảo rằng sản phẩm được ẩn ban đầu */
        }
    </style>
    <script>
        // Hiển thị modal
        function showModal(orderId) {
            var modal = document.getElementById('signatureModal' + orderId);
            modal.style.display = 'flex';
        }

        // Ẩn modal
        function closeModal(orderId) {
            var modal = document.getElementById('signatureModal' + orderId);
            modal.style.display = 'none';
        }

        // Đóng modal khi nhấp ngoài nội dung modal
        window.onclick = function (event) {
            var modals = document.getElementsByClassName('modal');
            for (var i = 0; i < modals.length; i++) {
                if (event.target == modals[i]) {
                    modals[i].style.display = "none";
                }
            }
        }

        function toggleProductList(orderId) {
            const productList = document.getElementById(`productList${orderId}`);
            const extraProducts = productList.querySelectorAll('.product-item.extra');
            const toggleButton = document.getElementById(`toggleButton${orderId}`);

            console.log(extraProducts);

            extraProducts.forEach(product => {
                if (product.style.display === "none" || product.style.display === "") {
                    product.style.display = "block"; // Hiển thị các sản phẩm
                    toggleButton.textContent = "Thu gọn";
                } else {
                    product.style.display = "none"; // Ẩn các sản phẩm
                    toggleButton.textContent = "Xem thêm";
                }
            });
        }
    </script>
</head>
<body>
<h1>Danh Sách Đơn Hàng</h1>

<!-- Hiển thị thông báo -->
<c:if test="${not empty message}">
    <div class="message <c:choose>
                                <c:when test="${message == 'Đơn hàng đã được chỉnh sửa thành công!'}">success</c:when>
                                <c:otherwise>error</c:otherwise>
                            </c:choose>">
            ${message}
    </div>
</c:if>

<div class="order-container">
    <c:forEach var="order" items="${orders}">
        <div class="order-card">
            <h2>Đơn Hàng #${order.orderId}</h2>
            <div class="order-details">
                <div>
                    <h3>Danh sách sản phẩm</h3>
                    <div class="product-list" id="productList${order.orderId}">
                        <c:forEach var="item" items="${orderItems}" varStatus="status">
                            <c:if test="${status.index < 1}">
                                <!-- Hiển thị sản phẩm đầu tiên -->
                                <div class="product-item">
                                    <img src="${item.product.imageUrl}" alt="${item.product.name}">
                                    <div class="product-info">
                                        <span>${item.product.name}</span>
                                        <span>SL: ${item.quantity}</span>
                                        <span>Giá: ${item.product.price} VNĐ</span>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${status.index >= 1}">
                                <!-- Các sản phẩm còn lại -->
                                <div class="product-item extra">
                                    <img src="${item.product.imageUrl}" alt="${item.product.name}">
                                    <div class="product-info">
                                        <span>${item.product.name}</span>
                                        <span>SL: ${item.quantity}</span>
                                        <span>Giá: ${item.product.price} VNĐ</span>
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                    <button class="toggle-button" id="toggleButton${order.orderId}" onclick="toggleProductList(${order.orderId})">
                        Xem thêm
                    </button>
                </div>
                <p><strong>Khách Hàng:</strong> ${order.recipientName}</p>
                <p><strong>Địa Chỉ:</strong> ${order.shippingAddress}</p>
                <p><strong>Số Điện Thoại:</strong> ${order.shippingPhoneNumber}</p>
                <p><strong>Phương Thức Thanh Toán:</strong> ${order.paymentMethod}</p>
                <p><strong>Ngày Đặt:</strong> ${order.orderDate}</p>
                <p class="status ${order.is_edited ? 'edited' : 'not-edited'}">
                        ${order.is_edited ? 'Đã chỉnh sửa' : 'Chưa chỉnh sửa'}
                </p>
            </div>
            <div class="actions">
                <button class="edit-button" onclick="showModal(${order.orderId})">Chỉnh sửa</button>
            </div>

            <!-- Modal Popup -->
            <div id="signatureModal${order.orderId}" class="modal">
                <div class="modal-content">
                    <span class="close" onclick="closeModal(${order.orderId})">&times;</span>
                    <h3>Nhập chữ ký để xác nhận</h3>
                    <form action="verifySignUpdate" method="POST" enctype="multipart/form-data">
                        <input type="hidden" name="orderId" value="${order.orderId}"/>
                        <div>
                            <label for="signatureFile${order.orderId}">Chọn file chữ ký:</label>
                            <input type="file" name="signatureFile" id="signatureFile${order.orderId}" accept=".png, .jpg, .jpeg, .pdf" required>
                        </div>
                        <button type="submit">Xác nhận</button>
                    </form>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>
