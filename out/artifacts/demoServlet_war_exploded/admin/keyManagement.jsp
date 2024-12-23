<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Quản Lý Khóa Người Dùng</title>
</head>
<body>
<h1>Quản Lý Khóa Người Dùng</h1>

<c:if test="${not empty param.message}">
  <div style="color:green;">${param.message}</div>
</c:if>

<c:if test="${not empty param.error}">
  <div style="color:red;">${param.error}</div>
</c:if>

<table border="1">
  <tr>
    <th>Mã Key</th>
    <th>Mã khách hàng</th>
    <th>Public Key</th>
    <th>Private Key</th>
    <th>Loại Key</th>
    <th>Ngày tạo</th>
    <th></th>
  </tr>

  <c:forEach var="key" items="${userKeys}">
    <tr>
      <td>${key.keyId}</td>
      <td>${key.userId}</td>
      <td class="long-text">${key.publicKey}</td> <!-- Áp dụng lớp cho publicKey -->
      <td class="long-text">${key.privateKey}</td> <!-- Áp dụng lớp cho privateKey -->
      <td>${key.keyType}</td>
      <td>${key.creationDate}</td>
      <td>
        <!-- Xóa khóa -->
        <form action="${pageContext.request.contextPath}/admin/key-management" method="GET" style="display:inline;" onsubmit="return confirm('Are you sure you want to delete this key?');">
          <input type="hidden" name="action" value="delete" />
          <input type="hidden" name="keyId" value="${key.keyId}" />
          <button type="submit">Xóa</button>
        </form>
      </td>
    </tr>
  </c:forEach>
</table>
</body>
</html>
<style>
  /* Tổng thể */
  body {
    font-family: Arial, sans-serif;
    background-color: #f9f9f9;
    padding: 20px;
    margin: 0;
  }

  h1 {
    text-align: center;
    color: #333;
    margin-bottom: 20px;
  }

  /* Thông báo */
  div[style="color:green;"],
  div[style="color:red;"] {
    max-width: 600px;
    margin: 10px auto;
    padding: 10px;
    border-radius: 5px;
    font-weight: bold;
    text-align: center;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }

  div[style="color:green;"] {
    background-color: #e9f5e9;
    border: 1px solid #57c257;
    color: #2e7d2e;
  }

  div[style="color:red;"] {
    background-color: #fdecea;
    border: 1px solid #e74c3c;
    color: #c0392b;
  }

  /* Bảng */
  table {
    width: 100%;
    border-collapse: collapse;
    margin: 20px 0;
    background: white;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  }

  th, td {
    padding: 12px 15px;
    text-align: left;
    border: 1px solid #ddd;
  }

  th {
    background-color: #002D62;
    color: white;
    font-weight: bold;
    text-align: center;
  }

  td {
    color: #333;
    text-align: center;
  }

  tr:nth-child(even) {
    background-color: #f8f9fa;
  }

  tr:hover {
    background-color: #e2e6ea;
  }

  /* Nút */
  button {
    padding: 8px 12px;
    background-color: #dc3545;
    border: none;
    color: white;
    border-radius: 5px;
    cursor: pointer;
    font-weight: bold;
    text-transform: uppercase;
  }

  button:hover {
    background-color: #bd2130;
    transition: background-color 0.3s ease;
  }

  /* Chuỗi dài */
  .long-text {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    max-width: 200px;
  }

  /* Hình dạng nút */
  form {
    display: inline;
  }

  form button {
    margin: 0;
  }

  /* Responsive */
  @media (max-width: 768px) {
    table {
      font-size: 14px;
    }

    th, td {
      padding: 10px;
    }

    .long-text {
      max-width: 150px;
    }
  }
</style>

