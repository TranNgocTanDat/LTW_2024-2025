<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Details</title>
</head>
<body>
<h1>Chi tiết đặt hàng</h1>
<table border="1">
    <thead>
    <tr>
        <th>Product ID</th>
        <th>Quantity</th>
        <th>Price</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${orderItems}">
        <c:choose>
            <c:when test="${item.product != null}">
                <tr>
                    <td>${item.product.name}</td>
                    <td>${item.quantity}</td>
                    <td>${item.product.price* item.quantity}</td>
                </tr>
            </c:when>
            <c:otherwise>
                Product not available.
            </c:otherwise>
        </c:choose>
    </c:forEach>

    </tbody>
</table>
<script>
    // Kiểm tra nếu trang admin đã tồn tại
    window.onload = function () {
        const adminOpenKey = 'admin_page_open';
        const adminOpen = localStorage.getItem(adminOpenKey);

        if (adminOpen === 'true') {
            // alert('Trang quản trị đã được mở trong một tab khác. Vui lòng đóng tab kia trước khi truy cập.');
            // Chuyển hướng hoặc đóng tab hiện tại
            window.location.href = 'index.jsp'; // Thay bằng trang lỗi của bạn
            return;
        }

        // Đánh dấu trang admin đã mở
        localStorage.setItem(adminOpenKey, 'true');

        // Lắng nghe sự kiện đóng tab để xóa trạng thái
        window.addEventListener('beforeunload', () => {
            localStorage.removeItem(adminOpenKey);
        });
    };
</script>
</body>
</html>
<style>
    /* Reset và thiết lập font */
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
        font-family: Arial, sans-serif;
    }

    body {
        background-color: #f9f9f9;
        padding: 20px;
    }

    h1 {
        text-align: center;
        margin-bottom: 20px;
        font-size: 28px;
        color: #333;
    }

    table {
        width: 80%;
        margin: 0 auto;
        border-collapse: collapse;
        background: white;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        border-radius: 10px;
        overflow: hidden;
    }

    thead {
        background-color: #002D62;
        color: white;
    }

    th, td {
        padding: 12px 20px;
        text-align: center;
        border-bottom: 1px solid #ddd;
    }

    th {
        font-size: 16px;
        text-transform: uppercase;
        letter-spacing: 1px;
    }

    tbody tr:nth-child(even) {
        background-color: #f2f2f2;
    }

    tbody tr:hover {
        background-color: #e0eaff;
    }

    td {
        color: #555;
    }

    tbody tr:last-child td {
        border-bottom: none;
    }

    /* Tùy chỉnh responsive */
    @media (max-width: 768px) {
        table {
            width: 100%;
        }

        th, td {
            padding: 10px;
        }
    }
</style>

