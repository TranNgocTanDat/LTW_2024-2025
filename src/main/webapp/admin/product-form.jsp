<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>${product != null ? "Thay đổi thông tin" : "Thêm"} Sản phẩm</title>
  <link rel="stylesheet" href="styles.css">
</head>
<body>
<h1>${product != null ? "Thay đổi" : "Thêm mới"} Sản phẩm</h1>

<form action="${pageContext.request.contextPath}/admin/products?action=${product != null ? "update" : "insert"}" method="post">
  <c:if test="${product != null}">
    <input type="hidden" name="id" value="${product.productId}"/>
  </c:if>

  <label for="name">Tên sản phẩm:</label>
  <input type="text" id="name" name="name" value="${product != null ? product.name : ''}" required><br/>

  <label for="description">Mô tả:</label>
  <input type="text" id="description" name="description" value="${product != null ? product.description : ''}" required><br/>

  <label for="price">Giá:</label>
  <input type="number" id="price" name="price" step="0.01" value="${product != null ? product.price : ''}" required><br/>

  <label for="category">Loại:</label>
  <input type="text" id="category" name="category" value="${product != null ? product.category : ''}" required><br/>

  <label for="size">Kích cỡ:</label>
  <input type="text" id="size" name="size" value="${product != null ? product.size : ''}" required><br/>

  <label for="color">Màu:</label>
  <input type="text" id="color" name="color" value="${product != null ? product.color : ''}" required><br/>

  <label for="stockQuantity">Còn trong kho:</label>
  <input type="number" id="stockQuantity" name="stockQuantity" value="${product != null ? product.stockQuantity : ''}" required><br/>

  <label for="imageUrl">Đường dẫn ảnh:</label>
  <input type="text" id="imageUrl" name="imageUrl" value="${product != null ? product.imageUrl : ''}" required><br/>

  <input type="submit" value="${product != null ? "Cập nhật" : "Thêm"} Sản phẩm">
</form>

<a href="${pageContext.request.contextPath}/admin/products">Quay lại</a>
</body>
<style>/* Reset some default styles */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: Arial, sans-serif;
  background-color: #f4f4f4;
  color: #333;
  line-height: 1.6;
}

h1 {
  text-align: center;
  margin-top: 20px;
  color: #333;
}

form {
  background-color: #fff;
  width: 50%;
  margin: 20px auto;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

label {
  display: block;
  margin: 10px 0 5px;
  font-weight: bold;
}

input[type="text"],
input[type="number"] {
  width: 100%;
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 16px;
}

input[type="submit"] {
  width: 100%;
  padding: 10px;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

input[type="submit"]:active {
  background-color: #004085;
}

a {
  text-decoration: none;
  color: #007bff;
  font-size: 14px;
  display: block; /* Để căn giữa với text-align */
  text-align: center; /* Căn giữa nội dung bên trong */
  margin: 20px auto; /* Căn giữa theo chiều ngang và thêm khoảng cách trên/dưới */
  width: fit-content; /* Đặt chiều rộng tự động vừa nội dung */
}

a:hover {
  text-decoration: underline;
}

</style>
</html>