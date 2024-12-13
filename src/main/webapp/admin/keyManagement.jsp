<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <title>Quản Lý Khóa Người Dùng</title>
  <style>
    /* CSS để cắt bớt chuỗi dài */
    .long-text {
      white-space: nowrap;      /* Ngăn chặn xuống dòng */
      overflow: hidden;         /* Ẩn phần dư thừa */
      text-overflow: ellipsis;  /* Thêm dấu '...' nếu văn bản dài */
      max-width: 200px;         /* Đặt chiều rộng tối đa cho cột */
    }
  </style>
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
    <th>Key ID</th>
    <th>User ID</th>
    <th>Public Key</th>
    <th>Private Key</th>
    <th>Key Type</th>
    <th>Creation Date</th>
    <th>Actions</th>
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
          <button type="submit">Delete</button>
        </form>
      </td>
    </tr>
  </c:forEach>
</table>
</body>
</html>
