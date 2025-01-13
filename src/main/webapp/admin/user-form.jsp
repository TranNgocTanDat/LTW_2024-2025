<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Edit User</title>
  <link rel="stylesheet" href="styles.css">
</head>
<body>
<h1>Edit User</h1>

<form action="${pageContext.request.contextPath}/admin/users?action=update" method="post">
  <c:if test="${users != null}">
    <input type="hidden" name="id" value="${users.userId}"/>
  </c:if>

  <label for="role">Role:</label>
  <input type="text" id="role" name="role" value="${users != null ? users.role : ''}" required><br/>

  <input type="submit" value="Update User">
</form>

<a href="${pageContext.request.contextPath}/admin/users">Back to User List</a>

<script>
  window.onload = function () {
    const adminOpenKey = 'admin_page_open';
    const adminOpen = localStorage.getItem(adminOpenKey);

    if (adminOpen === 'true') {
      window.location.href = 'index.jsp'; // Redirect to an error page if admin is already open
      return;
    }

    // Mark the admin page as open
    localStorage.setItem(adminOpenKey, 'true');

    // Remove the admin page mark when closing the tab
    window.addEventListener('beforeunload', () => {
      localStorage.removeItem(adminOpenKey);
    });
  };
</script>
</body>

<style>
  /* Reset default styles */
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }

  body {
    font-family: Arial, sans-serif;
    background-color: #f9f9f9;
    color: #333;
    line-height: 1.6;
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 20px;
  }

  /* Title */
  h1 {
    margin-bottom: 20px;
    color: #444;
    font-size: 24px;
    text-align: center;
  }

  /* Form container */
  form {
    background-color: #fff;
    width: 100%;
    max-width: 400px;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
  }

  /* Label styles */
  label {
    display: block;
    margin-bottom: 8px;
    font-weight: bold;
    color: #555;
  }

  /* Input styles */
  input[type="text"],
  input[type="submit"] {
    width: 100%;
    padding: 10px;
    margin-bottom: 15px;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 16px;
  }

  /* Placeholder and input focus styles */
  input[type="text"]::placeholder {
    color: #bbb;
  }

  input[type="text"]:focus {
    border-color: #007bff;
    outline: none;
    box-shadow: 0 0 4px rgba(0, 123, 255, 0.5);
  }

  /* Submit button styles */
  input[type="submit"] {
    background-color: #007BFF;
    color: #fff;
    border: none;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }

  input[type="submit"]:active {
    background-color: #004085;
  }

  /* Link styles */
  a {
    text-decoration: none;
    color: #007bff;
    font-size: 14px;
  }

  a:hover {
    text-decoration: underline;
  }
</style>
</html>
