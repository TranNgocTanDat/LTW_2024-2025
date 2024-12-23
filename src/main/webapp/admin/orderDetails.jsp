<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Order Details</title>
</head>
<body>
<h1>Order Details</h1>
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
</body>
</html>
