<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
  <title>Admin Dashboard</title>
  <style>
    /* Reset một số thuộc tính mặc định */
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }

    body {
      font-family: 'Poppins', sans-serif; /* Font hiện đại hơn */
      background-color: #f8f9fa;
      color: #343a40;
      display: flex;
    }

    /* Sidebar */
    .sidebar {
      width: 250px;
      background-color: #343a40;
      padding: 20px;
      color: #fff;
      height: 100vh;
      position: fixed;
    }

    .sidebar h2 {
      text-align: center;
      margin-bottom: 20px;
    }

    .sidebar ul {
      list-style-type: none;
      padding: 0;
    }

    .sidebar ul li {
      margin: 15px 0;
    }

    .sidebar ul li a {
      color: #fff;
      text-decoration: none;
      font-weight: bold;
      padding: 10px;
      display: block;
      transition: background-color 0.3s ease;
    }

    .sidebar ul li a:hover {
      background-color: #495057;
      border-radius: 5px;
    }

    /* Nội dung chính */
    .main-content {
      margin-left: 250px;
      padding: 20px;
      flex-grow: 1;
    }

    /* Thanh chào mừng */
    .welcome-bar {
      display: flex;
      justify-content: space-between;
      align-items: center;
      background-color: #ffffff;
      padding: 10px 20px;
      border-radius: 5px;
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
      margin-bottom: 20px;
    }

    .welcome-bar h1 {
      font-size: 1.5rem;
      color: #343a40;
    }

    .user-icon {
       /* Thay bằng URL của biểu tượng người dùng */
      width: 40px;
      height: 40px;
      background-size: cover;
      border-radius: 50%;
    }

    /* Các phần tử khác */
    .dashboard-content {
      padding: 2rem;
    }

    .overview {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
      gap: 1.5rem;
      margin-bottom: 2rem;
    }

    .overview .box {
      background-color: #ffffff;
      padding: 2rem;
      border-radius: 10px;
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
      text-align: center;
      transition: transform 0.3s ease, box-shadow 0.3s ease;
    }

    .overview .box:hover {
      transform: translateY(-5px);
      box-shadow: 0 8px 15px rgba(0, 0, 0, 0.2);
    }

    .charts, .recent-activities, .quick-links {
      background-color: #ffffff;
      padding: 2rem;
      border-radius: 10px;
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
      margin-bottom: 2rem;
    }

    /* Responsive Design */
    @media (max-width: 768px) {
      .overview {
        grid-template-columns: 1fr;
      }

      .sidebar {
        width: 100%;
        position: relative;
      }

      .main-content {
        margin-left: 0;
      }
    }
  </style>
</head>
<body>
<!-- Sidebar -->
<div class="sidebar">
  <h2>Admin Panel</h2>
  <ul>
    <li><a href="admin/products">Quản lý sản phẩm</a></li>
    <li><a href="admin/users">Quản lý người dùng</a></li>
    <li><a href="admin/orders-management">Quản lý đơn hàng</a></li>
    <li><a href="reports.jsp">Báo cáo</a></li>
  </ul>
</div>

<!-- Nội dung chính -->
<div class="main-content">
  <!-- Thanh chào mừng -->
  <div class="welcome-bar">
    <h1>Chào mừng bạn đến với bảng điều khiển quản trị!</h1>
    <div class="user-icon"></div>
  </div>

  <div class="dashboard-content">
    <!-- Phần thông tin tổng quan -->
    <div class="overview">
      <div class="box">
        <h3>Tổng số sản phẩm</h3>
        <p>120</p>
      </div>
      <div class="box">
        <h3>Tổng số người dùng</h3>
        <p>250</p>
      </div>
      <div class="box">
        <h3>Tổng doanh thu</h3>
        <p>500,000,000 VNĐ</p>
      </div>
      <div class="box">
        <h3>Đơn hàng đang xử lý</h3>
        <p>10</p>
      </div>
    </div>

    <!-- Phần biểu đồ doanh thu -->
    <div class="charts">
      <h2>Biểu đồ doanh thu tháng</h2>
      <canvas id="revenueChart"></canvas>
    </div>

    <!-- Phần hoạt động gần đây -->
    <div class="recent-activities">
      <h2>Hoạt động gần đây</h2>
      <ul>
        <li>Người dùng mới: Nguyễn Văn A vừa đăng ký.</li>
        <li>Đơn hàng #1234 vừa được đặt.</li>
        <li>Sản phẩm mới: Laptop ABC vừa được thêm vào kho.</li>
      </ul>
    </div>

    <!-- Liên kết nhanh -->
    <div class="quick-links">
      <h2>Liên kết nhanh</h2>
      <button onclick="location.href='addProduct.jsp'">Thêm sản phẩm mới</button>
      <button onclick="location.href='addUser.jsp'">Thêm người dùng mới</button>
    </div>
  </div>
</div>

<!-- Chèn script để vẽ biểu đồ -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
  var ctx = document.getElementById('revenueChart').getContext('2d');
  var revenueChart = new Chart(ctx, {
    type: 'bar',
    data: {
      labels: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6'],
      datasets: [{
        label: 'Doanh thu (VNĐ)',
        data: [12000000, 15000000, 20000000, 22000000, 25000000, 30000000],
        backgroundColor: 'rgba(75, 192, 192, 0.2)',
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 1
      }]
    },
    options: {
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });
</script>
</body>
</html>