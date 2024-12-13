<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý đơn hàng</title>
</head>
<body>
<h1>Danh sách đơn hàng</h1>
<table border="1">
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
            <td>${order.status}</td>
            <td>
                <c:if test="${order.status == 'Pending'}">
                    <form action="${pageContext.request.contextPath}/admin/orders-management" method="POST" style="display:inline;">
                        <input type="hidden" name="action" value="approve" />
                        <input type="hidden" name="orderId" value="${order.orderId}" />
                        <button type="submit">Approve</button>
                    </form>
                    <form action="${pageContext.request.contextPath}/admin/orders-management" method="POST" style="display:inline;">
                        <input type="hidden" name="action" value="cancel" />
                        <input type="hidden" name="orderId" value="${order.orderId}" />
                        <button type="submit">Cancel</button>
                    </form>
                </c:if>
                <c:if test="${order.status != 'Pending'}">
                    <!-- Disable buttons for non-pending orders -->
                    <button disabled>Approve</button>
                    <button disabled>Cancel</button>
                </c:if>
            </td>
            <td>
                <a href="<c:url value='/admin/orders-management?action=details&orderId=${order.orderId}' />">View</a>

                <!-- Delete form -->
                <form action="${pageContext.request.contextPath}/admin/orders-management" method="POST" style="display:inline;" onsubmit="return confirm('Are you sure you want to delete this order?');">
                    <input type="hidden" name="action" value="delete" />
                    <input type="hidden" name="orderId" value="${order.orderId}" />
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
