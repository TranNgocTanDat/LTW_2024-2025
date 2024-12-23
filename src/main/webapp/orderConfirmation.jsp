<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmation</title>
</head>
<body>
<h2>Đơn hàng của bạn đã được tạo thành công!</h2>
<p>Thông tin đơn hàng:</p>
<ul>
    <li>Mã đơn hàng: ${order.orderId}</li>
    <li>Khách hàng:  ${order.recipientName}</li>
    <li>Địa chỉ: ${order.shippingAddress}</li>
    <li>Số điện thoại: ${order.shippingPhoneNumber}</li>
    <li>Tổng tiền thanh toán: ${order.totalAmount}</li>
    <li>${order.order_content}</li><br>
</ul>
<c:forEach var="item" items="${cartItems}">
    <p>Product: ${item.product.name}, Quantity: ${item.quantity}, Price: ${item.product.price}</p>
</c:forEach>

<button id="downloadOrderTxt">Tải về file TXT thông tin đơn hàng</button>
<button id="goToSignPage">Ký và Xác Thực Đơn Hàng</button>


<script>
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
