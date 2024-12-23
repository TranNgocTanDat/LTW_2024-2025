<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Double D</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body{
            width: auto;
            height: auto;
        }
        .main__test{
            width: 100%;
            height: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-bottom: 10px;
        }
        .list__img{
            width: 1200px;
            max-width: 100%;
            height: 300px;
            overflow: hidden;
        }
        .img{
            width: 1200px;
            display: flex;
            transition: 0.5s;
            background-size: contain;
        }
        .img__item{
            flex-shrink: 0;
            width: 1200px;
            max-width: 100%;
            height: 500px;
            object-fit: cover;
        }
        .container__mid{
            height: 430px;
            display: flex;
            margin-top: 10px;
            margin-bottom: 10px;
            align-items: center;
            justify-content: center;
            background-color: #002D62;
        }
        .list__newProduct{

            margin-bottom: 10px;
        }
        .title__newProduct{
            text-align: center;
            font-size: 30px;
            margin-bottom: 20px;
        }
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
        .btn__product{
            display: flex;
        }
        .btn__price{
            width: 80px;
            height: 30px;
            margin-right: 20px;
            margin-bottom: 10px;
            border: 0px;
            font-size: 20px;
            border-radius: 10px;
            border: 1px solid black;
            font-size: 13px;
        }
        .btn__price:hover{
            background-color: red;
        }
        .view:hover{
            background-color: #45a049;
        }
        .active{
            background-color: #bf9369;
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
</head>
<body>
<a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
<header>
    <jsp:include page="header.jsp"></jsp:include>
</header>
<div class="main__test">
    <div class="list__img">
        <div class="img">
            <img src="https://images.pexels.com/photos/298863/pexels-photo-298863.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1" class="img__item" >
            <img src="https://images.pexels.com/photos/102129/pexels-photo-102129.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1" class="img__item" >
            <img src="https://images.pexels.com/photos/28271086/pexels-photo-28271086/free-photo-of-giay-adidas-buty-sklep-z-butami-c-a-hang-ban-l-giay.jpeg?auto=compress&cs=tinysrgb&w=600&lazy=load" class="img__item">
            <img src="https://images.pexels.com/photos/3875430/pexels-photo-3875430.jpeg?auto=compress&cs=tinysrgb&w=600" class="img__item">
        </div>
    </div>
</div>
<div class="list__newProduct">
    <h3 class="title__newProduct">Giảm giá</h3>
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
                    <button class="btn__price view" onclick="viewProduct(${product.productId})">Xem</button>
                    <form action="cart" method="post">
                        <input type="hidden" name="userId" value="${sessionScope.userId}">
                        <input type="hidden" name="productId" value="${product.productId}">
                        <input type="number" name="quantity" value="1" min="1" required style="display: none">
                        <input type="hidden" name="action" value="add">
                        <button type="submit" class="addToCard btn__add">Thêm vào Giỏ</button>
                    </form>
                </div>
            </div>
        </c:forEach>

    </div>
</div>
<div class="container__mid">
    <div class="img__left">
        <img src="./accset/home.jpg" style="width: 349px; height: 382px; margin-right: 10px">
    </div>
    <div class="img__center">
        <img src="./accset/home3.jpg" style="width: 727px; height: 380px">
    </div>
    <div class="img__right">
        <img src="./accset/home2.jpg" style="width: 349px; height: 382px; margin-left: 10px">
    </div>
</div>
<div class="list__newProduct">
    <h3 class="title__newProduct">Sảm Phẩm Mới</h3>
    <div class="list__item--product">
        <c:forEach var="product" items="${productsNewProduct}">
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
                    <button class="btn__price view" onclick="viewProduct(${product.productId})">Xem</button>
                    <form action="cart" method="post">
                        <input type="hidden" name="userId" value="${sessionScope.userId}">
                        <input type="hidden" name="productId" value="${product.productId}">
                        <input type="number" name="quantity" value="1" min="1" required style="display: none">
                        <button type="submit" class="btn__add">Thêm vào Giỏ</button>
                    </form>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<footer>
    <jsp:include page="foodter.jsp"></jsp:include>
</footer>
<script src="index.js"></script>
</body>
</html>
<!-- File dashboard.jsp -->
