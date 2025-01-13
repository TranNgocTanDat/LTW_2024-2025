<%--
  Created by IntelliJ IDEA.
  User: Danh Nguyen
  Date: 10/1/2024
  Time: 11:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Double D</title>
  <style>
    body{
      margin: 0px;
    }
    .nav__left{
      display: block;
    }
    .header__product{
      background-color: #bf9369;
    }

    /*test*/
    .list__item--product {
      display: grid;
      grid-template-columns: repeat(4, 1fr); /* Four equal columns */
      gap: 20px; /* Space between grid items */
      margin: 20px;
    }


    .item__product {
      text-align: center;
      background-color: #f5f5f5;
      padding: 15px;
      border-radius: 10px;
      transition: transform 0.3s, box-shadow 0.3s;
    }

    .item__product:hover {
      transform: translateY(-5px);
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .product__img {
      width: 100%;
      height: 300px;
      object-fit: cover;
      border-radius: 5px;
      transition: transform 0.3s;
    }

    .item__product:hover .product__img {
      transform: scale(1.05);
    }

    .title__product {
      font-size: 18px;
      font-weight: bold;
      margin: 10px 0;
      text-align: center;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      width: 200px; /* Example: Adjust based on your layout */
      display: block;
    }

    .price__product {
      color: #e63946;
      font-size: 16px;
      margin-bottom: 10px;
    }

    .btn__product {
      display: flex;
      justify-content: center;
      gap: 10px;
    }

    .btn__price, .btn__add {
      padding: 10px 15px;
      font-size: 14px;
      border-radius: 5px;
      border: 1px solid #ddd;
      cursor: pointer;
      transition: background-color 0.3s, color 0.3s;
    }

    .btn__price:hover {
      background-color: red;
      color: white;
    }

    .btn__add:hover {
      background-color: #fc870c;
      color: white;
    }

  </style>
  <script>
    function viewProduct(productId) {
      window.location.href = 'product-detail?productId=' + productId;
    }
  </script>
</head>
<body>
<header>
  <jsp:include page="header.jsp"></jsp:include>
</header>

<div class="container__product">
  <div class="list__newProduct">
    <div class="list__item--product">
      <c:forEach var="product" items="${products}">
        <div class="item__product">
          <div class="item__product--img">
            <img src="${product.imageUrl}" class="product__img" style="width: 320px; height: 400px; border-radius: 10px">
          </div>
          <div class="title__product" title="${product.name}">
              ${product.name}
          </div>

          <div class="price__product">
              ${product.price}
          </div>
          <div class="btn__product">
            <button class="btn__price">Mua</button>
            <button class="btn__price btn__view" onclick="viewProduct(${product.productId})">Xem</button>
            <form action="cart" method="post">
              <input type="hidden" name="userId" value="${sessionScope.userId}">
              <input type="hidden" name="productId" value="${product.productId}">

              <input type="number" name="quantity" value="1" min="1" required style="display: none">
              <input type="hidden" name="action" value="add">
              <button type="submit" class="btn__add">Thêm vào Giỏ</button>

            </form>
          </div>
        </div>
      </c:forEach>
    </div>
  </div>
</div>
<footer>
  <jsp:include page="foodter.jsp"></jsp:include>
</footer>
<script src="index.js"></script>
</body>
</html>
