<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chỉnh sửa đơn hàng</title>
    <style>
        /* CSS cho modal */
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
        }

        .modal-content {
            background-color: #fff;
            margin: 15% auto; /* Canh giữa màn hình */
            padding: 20px;
            border: 1px solid #888;
            width: 30%; /* Độ rộng modal */
            border-radius: 8px;
            text-align: center;
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }

        button {
            margin-top: 10px;
        }
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
    <script>
        // Hiển thị modal
        function showModal() {
            document.getElementById('signatureModal').style.display = 'block';
        }

        // Ẩn modal
        function closeModal() {
            document.getElementById('signatureModal').style.display = 'none';
        }

        // Hiển thị modal
        function showModalDelete() {
            document.getElementById('deleteModal').style.display = 'block';
        }

        // Ẩn modal
        function closeModal() {
            document.getElementById('deleteModal').style.display = 'none';
        }
    </script>
    </script>
</head>
<body>
<div class="container">
    <div class="title">
        Đơn hàng của bạn đã được đặt thành công!
    </div>
    <div class="content">
        <div class="order-info">
            <h2>Thông tin đơn hàng</h2>
            <ul>
                <li>Mã đơn hàng: ${ordered.orderId}</li>
                <li>Khách hàng:  ${ordered.recipientName}</li>
                <li>Địa chỉ: ${ordered.shippingAddress}</li>
                <li>Số điện thoại: ${ordered.shippingPhoneNumber}</li>
            </ul>
        </div>
        <div class="product-list">
            <h2>Danh sách sản phẩm</h2>
            <c:forEach var="item" items="${orderItems}">
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
        Tổng tiền thanh toán: ${ordered.totalAmount} VND
    </div>

    <!-- Nút mở modal -->
    <div style="margin-bottom: 20px; margin-left: 10px">
        <button type="button" onclick="showModal()">Chỉnh sửa đơn hàng</button>
    </div>

    <div style="margin-bottom: 20px; margin-left: 10px">
        <button type="button" onclick="showModalDelete()">Hủy đơn hàng</button>
    </div>
    <!-- Modal Popup -->
    <div id="signatureModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal()">&times;</span>
            <h3>Nhập chữ ký để xác nhận</h3>
            <form action="verifySignUpdate" method="POST" enctype="multipart/form-data">
                <input type="hidden" name="orderId" value="${ordered.orderId}"/>
                <div>
                    <label for="signatureFile">Chọn file chữ kí: </label>
                    <input type="file" name="signatureFile" id="signatureFile" required>
                </div>
                <br>
                <div style="margin-top: 20px">
                    <button type="submit" >Xác nhận</button>
                    <button type="button" onclick="closeModal()">Hủy</button>
                </div>
            </form>
        </div>
    </div>


</div>
</body>
</html>
