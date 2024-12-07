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
      margin-bottom: 20px;
      border-radius: 5px;
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
      font-size: 13px;
      margin-left: 10px;
      border-radius: 10px;
      border: solid 1px black;
    }
    .btn__price:hover{
      background-color: red;
    }
    .btn__view:hover{
      background-color: #45a049;
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
    .btn__product{
      display: flex;
    }
    .add__product{
      display: flex;
    }
    .btn__add{
      height: 30px;
      border-radius: 10px;
      font-size: 13px;
    }
    .btn__add:hover{
      background-color: #fc870c;
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
          <div class="title__product">
              ${product.name}
          </div>
          <div class="price__product">
              ${product.price}
          </div>
          <div class="btn__product">
            <button class="btn__price">Mua</button>
            <button class="btn__price btn__view" onclick="viewProduct(${product.productId})">Xem</button>
            <form action="cart" method="post" class="add__product">
              <input type="hidden" name="userId" value="${sessionScope.userId}">
              <input type="hidden" name="productId" value="${product.productId}">
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
