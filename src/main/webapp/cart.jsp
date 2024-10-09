<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Giỏ hàng</title>
  <style>
    table {
      width: 100%;
      border-collapse: collapse;
    }
    th, td {
      padding: 10px;
      text-align: left;
      border: 1px solid #ddd;
    }
    th {
      background-color: #f2f2f2;
    }
  </style>
</head>
<body>

<h2>Giỏ hàng</h2>
<c:choose>
  <c:when test="${not empty cart}">
    <table>
      <tr>
        <th>Tên sản phẩm</th>
        <th>Giá</th>
        <th>Số lượng</th>
        <th>Tổng giá</th>
      </tr>
      <c:forEach var="product" items="${cart}">
        <tr>
          <td>${product.name}</td>
          <td>${product.price} VNĐ</td>
          <td>${product.stockQuantity}</td>
          <td>${product.price * product.stockQuantity} VNĐ</td> <!-- Tính tổng giá cho sản phẩm -->
        </tr>
      </c:forEach>
      <tr>
        <td colspan="3" style="text-align:right;"><strong>Tổng cộng:</strong></td>
        <td>
          <c:set var="total" value="0" />
          <c:forEach var="product" items="${cart}">
            <c:set var="total" value="${total + (product.price * product.stockQuantity)}" />
          </c:forEach>
          <c:out value="${total} VNĐ" /> <!-- Hiển thị tổng giá của giỏ hàng -->
        </td>
      </tr>
    </table>
  </c:when>
  <c:otherwise>
    <p>Giỏ hàng của bạn trống.</p>
  </c:otherwise>
</c:choose>

<a href="productList.jsp">Tiếp tục mua sắm</a> <!-- Link để quay lại danh sách sản phẩm -->

</body>
</html>