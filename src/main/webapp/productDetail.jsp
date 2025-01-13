<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Chi tiết sản phẩm</title>
  <style>
    /* Thiết lập chung */
    .body {
      font-family: 'Inter', Arial, sans-serif;
      margin: 0;
      padding: 0;
      background: #f5f5f5;
      color: #333;
      line-height: 1.6;
      display: flex;
      flex-direction: column;
      min-height: 100vh;
    }

    /* Phần header */
    .header {
      width: 100%;
      background-color: #333;
      color: #fff;
      padding: 20px 0;
      text-align: center;
    }

    /* Phần footer */
    .footer {
      width: 100%;
      background-color: #333;
      color: #fff;
      padding: 20px 0;
      text-align: center;
      margin-top: auto; /* Footer luôn nằm ở dưới cùng */
    }

    /* Container cho nội dung chính */
    .container {
      width: 100%;
      max-width: 1200px;
      padding: 0 15px;
      display: flex;
      flex-direction: column;
      align-items: center;
      flex-grow: 1;
      margin-bottom: 60px;
      margin-left: 160px;
    }

    /* Tiêu đề chính */
    .title {
      font-size: 2.2rem;
      margin: 30px 0;
      color: #222;
      font-weight: 600;
      text-transform: uppercase;
      text-align: center;
      letter-spacing: 1px;
      padding-bottom: 15px;
      border-bottom: 2px solid #ddd;
    }

    /* Card chi tiết sản phẩm */
    .product-detail {
      background: #fff;
      width: 100%;
      max-width: 900px;
      margin: 30px 0;
      padding: 20px;
      border-radius: 12px;
      box-shadow: 0 4px 25px rgba(0, 0, 0, 0.1);
      display: flex;
      flex-direction: column;
      align-items: flex-start;
      gap: 20px;
      animation: fadeIn 0.6s ease-out;
    }

    /* Tên sản phẩm */
    .product-name {
      font-size: 2rem;
      margin: 0 0 15px;
      color: #333;
    }

    /* Hình ảnh sản phẩm và thông tin */
    .product-info-container {
      display: flex;
      justify-content: space-between;
      width: 100%;
      gap: 30px;
    }

    /* Hình ảnh sản phẩm */
    .product-image {
      width: 50%;
      max-width: 450px;
      border-radius: 12px;
      transition: transform 0.3s ease, box-shadow 0.3s ease;
    }

    .product-image:hover {
      transform: scale(1.1);
      box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
    }

    /* Thông tin sản phẩm */
    .product-info {
      width: 50%;
      text-align: left;
      line-height: 1.5;
    }

    .product-info p {
      font-size: 1.1rem;
      color: #666;
      margin: 5px 0;
    }

    .product-price {
      font-size: 1.8rem;
      font-weight: bold;
      color: #ff4d4d;
    }

    /* Form và nút thêm vào giỏ hàng */
    .form {
      display: flex;
      flex-direction: column;
      align-items: flex-start;
      gap: 15px;
    }

    .quantity-input {
      width: 90px;
      padding: 10px;
      border: 1px solid #ddd;
      border-radius: 8px;
      text-align: center;
      font-size: 1.1rem;
      transition: border-color 0.3s;
    }

    .quantity-input:focus {
      border-color: #ff5722;
      outline: none;
    }

    .button-add {
      padding: 14px 35px;
      border: none;
      border-radius: 8px;
      background: linear-gradient(45deg, #ff5722, #ff7043);
      color: #fff;
      font-size: 1.2rem;
      font-weight: bold;
      cursor: pointer;
      transition: transform 0.3s ease, background 0.3s ease;

    }

    .button-add:hover {
      background: linear-gradient(45deg, #e64a19, #ff5722);
      transform: scale(1.05);
    }

    .button-add:active {
      transform: scale(1);
    }

    /* Hiệu ứng fadeIn */
    @keyframes fadeIn {
      from {
        opacity: 0;
        transform: translateY(15px);
      }
      to {
        opacity: 1;
        transform: translateY(0);
      }
    }

    /* Responsive Design */
    @media (max-width: 768px) {
      .product-detail {
        padding: 15px;
        gap: 20px;
      }

      .product-info-container {
        flex-direction: column;
        gap: 15px;
      }

      .product-image {
        width: 100%;
        max-width: 100%;
      }

      .product-info {
        width: 100%;
      }

      .button-add {
        width: 100%;
        padding: 12px 20px;
      }
    }

  </style>
</head>
<body>
<header>
  <jsp:include page="header.jsp"></jsp:include>
</header>

<div class="container">
  <c:if test="${not empty product}">
    <div class="product-detail">
      <h2 class="product-name">${product.name}</h2>
      <div class="product-info-container">
        <img class="product-image" src="${product.imageUrl}" alt="${product.name}">
        <div class="product-info">
          <p>${product.description}</p>
          <p class="product-price">Giá: ${product.price} VNĐ</p>
          <form class="form" action="cart" method="post">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="userId" value="${sessionScope.userId}">
            <input type="hidden" name="productId" value="${product.productId}">
            <input class="quantity-input" type="number" name="quantity" value="1" min="1" max="${product.stockQuantity}" required>
            <button class="button-add" type="submit">Thêm vào Giỏ hàng</button>
          </form>
        </div>
      </div>
    </div>
  </c:if>
  <c:if test="${empty product}">
    <p>Sản phẩm không tồn tại.</p>
  </c:if>
</div>

<footer>
  <jsp:include page="foodter.jsp"></jsp:include>
</footer>
</body>
</html>
