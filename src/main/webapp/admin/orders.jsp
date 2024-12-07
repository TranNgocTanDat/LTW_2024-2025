<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý đơn hàng</title>
</head>
<body>
<h1>Danh sách đơn hàng</h1>
<table border="1">
    <thead>
    <tr>
        <th>ID đơn hàng</th>
        <th>ID khách hàng</th>
        <th>Địa chỉ giao hàng</th>
        <th>Trạng thái</th>
        <th>Ngày tạo</th>
        <th>Thao tác</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>${order.orderId}</td>
            <td>${order.userId}</td>
            <td>${order.shippingAddress}</td>
            <td>${order.status}</td>
            <td>${order.orderDate}</td>
            <td>
                <a href="order-details?orderId=${order.orderId}">Xem chi tiết</a>
                <a href="update-order-status?orderId=${order.orderId}">Cập nhật trạng thái</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
