<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>${users != null ? "Edit" : "Add"} Product</title>
  <link rel="stylesheet" href="styles.css">
</head>
<body>
<h1>${users != null ? "Edit" : "Add New"} Product</h1>

<form action="${pageContext.request.contextPath}/admin/users?action=${users != null ? "update" : "insert"}" method="post">
  <c:if test="${users != null}">
    <input type="hidden" name="id" value="${users.userId}"/>
  </c:if>

  <label for="name">Name:</label>
  <input type="text" id="name" name="name" value="${users != null ? users.username : ''}" required><br/>


  <input type="submit" value="${users != null ? "Update" : "Add"} User">
</form>

<a href="${pageContext.request.contextPath}/admin/products">Back to Product List</a>
</body>
</html>