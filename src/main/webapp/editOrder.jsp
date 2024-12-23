<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 12/12/2024
  Time: 10:44 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <h1>Edit Order</h1>
  <form action="/admin/orders-management" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="orderId" value="${order.orderId}">
    <label for="userId">User ID:</label>
    <input type="text" id="userId" name="userId" value="${order.userId}"><br>
    <label for="orderDate">Order Date:</label>
    <input type="date" id="orderDate" name="orderDate" value="${order.orderDate}"><br>
    <label for="totalAmount">Total Amount:</label>
    <input type="text" id="totalAmount" name="totalAmount" value="${order.totalAmount}"><br>
    <label for="shippingAddress">Shipping Address:</label>
    <input type="text" id="shippingAddress" name="shippingAddress" value="${order.shippingAddress}"><br>
    <label for="status">Status:</label>
    <input type="text" id="status" name="status" value="${order.status}"><br>

    <h2>Order Items</h2>
    <c:forEach var="item" items="${orderItems}">
      <input type="hidden" name="orderItemId" value="${item.orderItemId}">
      <label for="productId">Product ID:</label>
      <input type="text" id="productId" name="productId" value="${item.productId}"><br>
      <label for="quantity">Quantity:</label>
      <input type="text" id="quantity" name="quantity" value="${item.quantity}"><br>
      <label for="price">Price:</label>
      <input type="text" id="price" name="price" value="${item.price}"><br>
      <hr>
    </c:forEach>

    <input type="submit" value="Update Order">
  </form>
</head>
<body>

</body>
</html>
