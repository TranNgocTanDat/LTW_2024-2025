<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý đơn hàng</title>
</head>
<body>
<h1>Danh sách đơn hàng</h1>
<table>
    <tr>
        <th>Order ID</th>
        <th>User ID</th>
        <th>Order Date</th>
        <th>Total Amount</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>${order.orderId}</td>
            <td>${order.userId}</td>
            <td>${order.orderDate}</td>
            <td>${order.totalAmount}</td>
            <td>
                <c:if test="${order.status == 'Pending'}">
                    <form action="orders-management" method="POST" style="display:inline;">
                        <input type="hidden" name="action" value="approve" />
                        <input type="hidden" name="orderId" value="${order.orderId}" />
                        <button type="submit">Approve</button>
                    </form>
                    <form action="orders-management" method="POST" style="display:inline;">
                        <input type="hidden" name="action" value="cancel" />
                        <input type="hidden" name="orderId" value="${order.orderId}" />
                        <button type="submit">Cancel</button>
                    </form>
                </c:if>
            </td>
            <td>
                <a href="orders-management?action=details&orderId=${order.orderId}">View</a>
                <a href="orders-management?action=delete&orderId=${order.orderId}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>a
</body>
</html>
