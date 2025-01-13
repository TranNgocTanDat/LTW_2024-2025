<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Gửi OTP</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f9;
      margin: 0;
      padding: 0;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
      color: #333;
    }

    .container {
      background-color: #ffffff;
      padding: 30px;
      border-radius: 8px;
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
      width: 100%;
      max-width: 400px;
    }

    h1 {
      font-size: 24px;
      text-align: center;
      margin-bottom: 20px;
      color: #007bff;
    }

    label {
      font-size: 16px;
      margin-bottom: 8px;
      display: block;
      color: #555;
    }

    input[type="email"], input[type="text"] {
      width: 100%;
      padding: 12px;
      margin: 8px 0 16px 0;
      border: 1px solid #ccc;
      border-radius: 4px;
      box-sizing: border-box;
      font-size: 16px;
    }

    button {
      width: 100%;
      padding: 12px;
      background-color: #007bff;
      color: white;
      border: none;
      border-radius: 4px;
      font-size: 16px;
      cursor: pointer;
    }

    button:hover {
      background-color: #007bff;
    }

    .message {
      text-align: center;
      font-size: 14px;
      color: red;
    }

    .form-container {
      margin-bottom: 20px;
    }

    .form-container:last-child {
      margin-bottom: 0;
    }

    /* Cấu hình cho thông báo */
    .toast {
      visibility: hidden;
      min-width: 250px;
      margin-left: -125px;
      background-color: #333;
      color: #fff;
      text-align: center;
      border-radius: 2px;
      padding: 16px;
      position: fixed;
      z-index: 1;
      left: 50%;
      bottom: 30px;
      font-size: 17px;
    }

    .toast.show {
      visibility: visible;
      animation: slideIn 0.5s, fadeOut 1s 3.5s;
    }

    @keyframes slideIn {
      from {
        bottom: 0;
        opacity: 0;
      }
      to {
        bottom: 30px;
        opacity: 1;
      }
    }

    @keyframes fadeOut {
      from {
        opacity: 1;
      }
      to {
        opacity: 0;
      }
    }
  </style>
</head>
<body>

<div class="container">
  <h1>Nhập email để nhận OTP</h1>
  <form action="sendOTP" method="post" class="form-container">
    <input type="hidden" name="action" value="sendOTP">
    <label for="mail">Email:</label>
    <input type="email" id="mail" name="mail" required>
    <button type="submit">Gửi OTP</button>
  </form>

  <div class="toast" id="otpToast"></div>


  <form action="sendOTP" method="post" class="form-container">
    <input type="hidden" name="action" value="sendReport">
    <label for="otp">Nhập mã OTP:</label>
    <input type="text" id="otp" name="otp" required>
    <button type="submit">Xác minh</button>
  </form>
</div>

<c:if test="${not empty otpMail}">
  <script>
    var message = "${otpMail}";
    var toast = document.getElementById("otpToast");
    toast.innerText = message;
    toast.className = "toast show";

    // Hiển thị thông báo trong 3 giây
    setTimeout(function() {
      toast.className = toast.className.replace("show", "");
    }, 3000);  // Thời gian hiển thị thông báo là 3 giây

    // Kiểm tra OTP trước khi chuyển trang
    document.querySelector('form').addEventListener('submit', function(event) {
      var otpValue = document.getElementById("otp").value;
      if (otpValue.trim() === "") {
        alert("Vui lòng nhập mã OTP!");
        event.preventDefault(); // Ngừng gửi form nếu OTP chưa được nhập
      } else {
        // Nếu OTP được nhập, chuyển trang sau 3 giây
        setTimeout(function() {
          window.location.href = "<c:url value='/ordersUser' />";
        }, 3000);  // Chuyển trang sau 3 giây
      }
    });
  </script>
</c:if>
</body>
</html>
