<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <title>${product != null ? "Edit" : "Add"} Product</title>
  <link rel="stylesheet" href="styles.css">
</head>
<body>
<h1>${product != null ? "Edit" : "Add New"} Product</h1>

<form action="${pageContext.request.contextPath}/admin/products?action=${product != null ? "update" : "insert"}" method="post">
  <c:if test="${product != null}">
    <input type="hidden" name="id" value="${product.productId}"/>
  </c:if>

  <label for="name">Name:</label>
  <input type="text" id="name" name="name" value="${product != null ? product.name : ''}" required><br/>

  <label for="description">Description:</label>
  <input type="text" id="description" name="description" value="${product != null ? product.description : ''}" required><br/>

  <label for="price">Price:</label>
  <input type="number" id="price" name="price" step="0.01" value="${product != null ? product.price : ''}" required><br/>

  <label for="category">Category:</label>
  <input type="text" id="category" name="category" value="${product != null ? product.category : ''}" required><br/>

  <label for="size">Size:</label>
  <input type="text" id="size" name="size" value="${product != null ? product.size : ''}" required><br/>

  <label for="color">Color:</label>
  <input type="text" id="color" name="color" value="${product != null ? product.color : ''}" required><br/>

  <label for="stockQuantity">Stock Quantity:</label>
  <input type="number" id="stockQuantity" name="stockQuantity" value="${product != null ? product.stockQuantity : ''}" required><br/>

  <input type="submit" value="${product != null ? "Update" : "Add"} Product">
</form>

<a href="${pageContext.request.contextPath}/admin/products">Back to Product List</a>
</body>
</html>