<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Checkout</title>
</head>
<body>
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
        <button type="submit">Thanh toán</button>
    </c:if>
    <c:if test="${empty cartItems}">
        <p>Giỏ hàng của bạn đang trống!</p>
    </c:if>
</form>
</body>
</html>
