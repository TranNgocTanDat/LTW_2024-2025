<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Thông tin cá nhân</title>
  <style>
    body {
      font-family: Arial, sans-serif;
    }
    .container {
      max-width: 500px;
      margin: 0 auto;
      padding: 20px;
      background-color: #f7f7f7;
      border-radius: 8px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
    h2 {
      text-align: center;
    }
    .profile-info {
      margin: 20px 0;
    }
    .profile-info label {
      font-weight: bold;
      display: block;
      margin-bottom: 5px;
    }
    .profile-info p {
      margin: 0 0 15px;
      padding: 10px;
      background-color: #f1f1f1;
      border-radius: 4px;
    }
    button {
      padding: 10px 20px;
      background-color: #4CAF50;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      display: block;
      width: 100%;
      text-align: center;
    }
    button:hover {
      background-color: #45a049;
    }
    .report{
      display: none;
    }
    .report__content{
      display: grid;
      margin-bottom: 20px;
    }
    .content{
      margin: 0 0 15px;
      padding: 10px;
      background-color: #f1f1f1;
      border-radius: 4px;
    }
    .inf__client{
      display: grid;
    }
    .inf__client--mail{
      display: grid;
      margin-bottom: 20px;
    }
    .report__messenger{
      display: grid;
      margin-bottom: 20px;
    }
  </style>
</head>
<body>
<div class="container">
  <h2 class="inf__title">Thông tin cá nhân</h2>
  <h2 class="report__title" style="display: none">Báo cáo của khách hàng</h2>
  <div class="profile-info">

    <%--@declare id="username"--%><label for="username">Tên đăng nhập:</label>
    <p>${user.username != null ? user.username : 'Chưa có tên đăng nhập'}</p>

    <label for="email">Email:</label>
    <p>${user.email != null ? user.email : 'Chưa có email'}</p>

    <label for="firstName">Họ:</label>
    <p>${user.firstName != null ? user.firstName : 'Chưa có họ'}</p>

    <label for="lastName">Tên:</label>
    <p>${user.lastName != null ? user.lastName : 'Chưa có tên'}</p>

    <label for="address">Địa chỉ:</label>
    <p>${user.address != null ? user.address : 'Chưa có địa chỉ'}</p>

    <label for="phoneNumber">Số điện thoại:</label>
    <p>${user.phoneNumber != null ? user.phoneNumber : 'Chưa có số điện thoại'}</p>

    <label for="role">Vai trò:</label>
    <p>${user.role != null ? user.role : 'Chưa có vai trò'}</p>
  </div>
  <c:if test="${not empty error}">
      <h3>${error}</h3>
  </c:if>


  <form action="${pageContext.request.contextPath}/report" method="post">
    <div class="report">
      <div class="report__content">
        <label style="font-weight: bold; margin-bottom: 5px">Nội dung report:</label>
        <select name="content" class="content" onchange="updateDistricts()">
          <option value="">-- Nội dung report --</option>
          <option name="content" value="exposed">Lộ key or mất key</option>
          <option name="content" value="besides">Một số vấn đề khác</option>
        </select>
      </div>
      <div class="inf__client">
        <div class="inf__client--mail">
          <label style="font-weight: bold; margin-bottom: 5px">Email của bạn</label>
          <input class="content" type="text" name="mail" placeholder="Vui lòng nhập đúng địa chỉ mail của bạn.">
        </div>
        <div class="inf__client--mail">
          <label style="font-weight: bold; margin-bottom: 5px">SDT của bạn</label>
          <input class="content" type="text" name="phone" placeholder="Vui lòng nhập đúng số điện thoại của bạn.">
        </div>
        <div class="inf__client--mail">
          <label style="font-weight: bold; margin-bottom: 5px">Mã OTP</label>
          <input class="content" type="text" name="otp"  placeholder="Nhập OTB từ mail của bạn.">

          <button onclick="showReport()" type="submit" class="btn__repOTB" name="action" value="sendOTB">Gửi</button>

        </div>
      </div>
      <div class="report__messenger">
        <label style="font-weight: bold; margin-bottom: 5px">Report</label>
        <textarea name="report" style="height: 100px; padding: 5px; box-sizing: border-box; text-align: left;" placeholder="Vui lòng nhập nội dung cần báo cáo."></textarea>
      </div>
      <button style="display: none" class="rep__report" type="submit" name="action" value="sendReport">Gửi Report</button>

    </div>
  </form>
  <c:if test="${not empty otpMail}">
    <<script>
    alert("${otpMail}");
    </script>
  </c:if>

  <c:if test="${not empty error}">
    <<script>
    alert("${error}");
    </script>
  </c:if>

  <a class="update" style="display: flex; margin-bottom: 20px" href="${pageContext.request.contextPath}/home" >
    <button type="submit">Quay lại</button>
  </a>
  <button class="btn__report" onclick="showReport()">Report</button>
  <form action="logout" method="post">
    <button onclick="logout()" class="logout" type="submit" style="margin-top: 20px; background-color: red">Đăng xuất</button>
  </form>
  </div>
</div>
<script>
  function showReport() {
    const report = document.querySelector(".report");
    const inf = document.querySelector(".profile-info");
    document.querySelector(".update").style.display = 'none';
    document.querySelector(".inf__title").style.display = 'none';
    document.querySelector(".report__title").style.display = 'block';
    document.querySelector(".rep__report").style.display = 'block';
    document.querySelector(".btn__report").style.display = 'none';
    report.style.display = 'block';
    inf.style.display = 'none';

    sessionStorage.setItem('isReportView', 'true');
  }
  function logout(){
    sessionStorage.removeItem('notification');
  }


  document.addEventListener("DOMContentLoaded", function() {
    const isReportView = sessionStorage.getItem('isReportView') === "true";

    if (isReportView) {
      showReport(); // Gọi hàm hiển thị phần báo cáo
    }

    // Lưu giá trị vào sessionStorage khi người dùng thay đổi nội dung
    document.querySelectorAll('input, textarea, select').forEach(function(input) {
      input.addEventListener('input', function() {
        sessionStorage.setItem(input.name, input.value);
      });
    });

    // Điền lại giá trị vào các trường nếu có trong sessionStorage
    document.querySelectorAll('input, textarea, select').forEach(function(input) {
      const storedValue = sessionStorage.getItem(input.name);
      if (storedValue) {
        input.value = storedValue;
      }
    });


    // Xử lý khi nhấn nút "Gửi OTP"
    const btnSendOtp = document.querySelector('.btn__repOTB');
    if (btnSendOtp) {
      btnSendOtp.addEventListener("click", function () {
        // Không thay đổi isReportView khi gửi OTP
        sessionStorage.setItem('isReportView', 'true');
      });
    }

    // Xử lý khi nhấn nút "Rep Report"
    const btnRepReport = document.querySelector('.rep__report');
    if (btnRepReport) {
      btnRepReport.addEventListener("click", function () {
        // Đặt isReportView thành false khi gửi báo cáo thành công
        sessionStorage.setItem('isReportView', 'false');
      });
    }
  });
</script>
</body>
</html>