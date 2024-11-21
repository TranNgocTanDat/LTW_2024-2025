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
    .list__item--product{
      display: grid;
      grid-template-columns: repeat(4, minmax(200px, 1fr));
      /*gap: px;*/
    }

    .item__product{
      text-align: center;
      background-color: #F5F5F5;
      margin-left: 30px;
      margin-right: 30px;
    }
    .item__product:hover .product__img{
      transform: scale(1.1);
    }
    .btn__price{
      width: 80px;
      height: 30px;
      margin-right: 20px;
      margin-bottom: 10px;
      border: 0px;
      font-size: 20px;
    }
    .btn__price:hover{
      background-color: red;
    }
    .addToCard{
      width: 80px;
      height: 30px;
      margin-left: 20px;
      margin-bottom: 10px;
      border: 0px;
      font-size: 20px;
    }
    .addToCard:hover{
      background-color: red;
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

<div class="sidebar">
  <h2>Danh Mục Sản Phẩm</h2>
  <a href="products?category=Quần">Quần</a>
  <a href="products?category=Áo">Áo</a>
  <a href="products?category=mu">Mũ</a>
  <a href="products?category=Giày">Giày</a>
</div>


<div class="container__product">
  <div class="list__newProduct">
    <h3 class="title__newProduct">Flast Sale</h3>
    <div class="list__item--product">
      <c:forEach var="product" items="${products}">
        <div class="item__product">
          <div class="item__product--img">
            <img src="${product.imageUrl}" class="product__img" style="width: 300px; height: 400px">
          </div>
          <div class="title__product">
              ${product.name}
          </div>
          <div class="price__product">
              ${product.price}
          </div>
          <div class="btn__product">
            <button class="btn__price">Mua</button>
            <button class="btn__price" onclick="viewProduct(${product.productId})">Xem</button>
            <form action="cart" method="post">
              <input type="hidden" name="userId" value="${sessionScope.userId}">
              <input type="hidden" name="productId" value="${product.productId}">
              <input type="number" name="quantity" value="1" min="1" required>
              <input type="hidden" name="action" value="add">
              <button type="submit">Thêm vào Giỏ</button>
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
