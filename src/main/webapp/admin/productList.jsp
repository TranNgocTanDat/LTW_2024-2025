<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Product List</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<h1>Product List</h1>

<table>
    <tr>
        <th>Name</th>
        <th>Description</th>
        <th>Price</th>
        <th>Category</th>
        <th>Size</th>
        <th>Color</th>
        <th>Stock Quantity</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>${product.name}</td>
            <td>${product.description}</td>
            <td>${product.price}</td>
            <td>${product.category}</td>
            <td>${product.size}</td>
            <td>${product.color}</td>
            <td>${product.stockQuantity}</td>
            <td>
                <a href="products?action=edit&id=${product.productId}">Edit</a>
                <a href="products?action=delete&id=${product.productId}" onclick="return confirm('Are you sure you want to delete this product?');">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>

<a href="product-form.jsp?action=new">Add New Product</a>
</body>
</html>