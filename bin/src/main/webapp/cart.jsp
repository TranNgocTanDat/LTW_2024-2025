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
        <th>Hành động</th>
      </tr>
      <c:forEach var="item" items="${cart}">
        <tr>
          <td>${item.product.name}</td>
          <td>${item.product.price} VNĐ</td>
          <<td>
          <input type="number" name="quantity" value="${item.quantity}" min="1" />
          <input type="hidden" name="productId" value="${item.product.productId}" />
        </td>
          <td>${item.product.price * item.quantity} VNĐ</td> <!-- Tính tổng giá cho sản phẩm -->
          <td>
            <button type="submit" name="action" value="update">Cập nhật</button>
            <button type="submit" name="action" value="remove">Xóa</button>
          </td>
        </tr>
      </c:forEach>
      <tr>
        <td colspan="3" style="text-align:right;"><strong>Tổng cộng:</strong></td>
        <td>
          <c:set var="total" value="0" />
          <c:forEach var="cartItem" items="${cart}">
            <c:set var="product" value="${cartItem.product}" />
            <c:set var="total" value="${total + (product.price * cartItem.quantity)}" />
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