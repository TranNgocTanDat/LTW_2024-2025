<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Giỏ hàng</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            padding: 20px;
        }

        .cart-container {
            display: flex;
            justify-content: space-between;
            gap: 20px;
        }

        .cart {
            flex: 2;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .cart h2 {
            margin-bottom: 20px;
        }

        .cart-items {
            margin-bottom: 20px;
        }

        .cart-item {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 20px;
            border-bottom: 1px solid #ddd;
            padding-bottom: 10px;
        }

        .cart-item img {
            width: 60px;
            height: 60px;
            object-fit: cover;
            border-radius: 8px;
        }

        .item-info h3 {
            font-size: 16px;
            margin-bottom: 5px;
        }

        .item-info p {
            font-size: 14px;
            color: #666;
        }

        .quantity-control {
            display: flex;
            align-items: center;
            gap: 5px;
        }

        .quantity-control button {
            padding: 5px 10px;
            border: 1px solid #ddd;
            background: #fff;
            cursor: pointer;
        }

        .quantity-control input {
            width: 40px;
            text-align: center;
            border: 1px solid #ddd;
        }

        .item-price {
            font-weight: bold;
        }

        .remove-item {
            background: none;
            border: none;
            font-size: 18px;
            color: #999;
            cursor: pointer;
        }

        .back-to-shop {
            text-decoration: none;
            color: #555;
            font-size: 14px;
        }

        .summary {
            flex: 1;
            background: #eee;
            padding: 20px;
            border-radius: 8px;
        }

        .summary h2 {
            margin-bottom: 20px;
        }

        .summary-item {
            display: flex;
            justify-content: space-between;
            margin-bottom: 15px;
        }

        .summary-item input,
        .summary-item select {
            padding: 5px;
            border: 1px solid #ddd;
            border-radius: 4px;
            width: 60%;
        }

        .summary-total {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
            font-weight: bold;
            font-size: 16px;
        }

        .checkout {
            width: 100%;
            background: #000;
            color: #fff;
            padding: 10px 0;
            border: none;
            cursor: pointer;
            font-size: 16px;
            border-radius: 4px;
        }

    </style>
</head>
<body>
<div class="cart-container">
    <c:choose>
        <c:when test="${not empty sessionScope.cartSession}">
            <div class="cart">
                <h2>Shopping Cart</h2>
                <c:forEach var="item" items="${sessionScope.cartSession}">
                    <div class="cart-items">
                        <div class="cart-item">
                            <img src="${item.product.imageUrl}" alt="${item.product.name}">
                            <div class="item-info">
                                <h3>Shirt</h3>
                                <p>${item.product.name}</p>
                            </div>
                            <div class="quantity-control">
                                <form method="post" action="cart" style="display: flex; align-items: center; gap: 10px;">
                                    <input type="hidden" name="productId" value="${item.product.productId}"/>
                                    <button type="submit" name="action" value="increase">+</button>
                                    <input type="number" name="quantity" value="${item.quantity}" min="1"/>
                                    <button type="submit" name="action" value="decrease">-</button>
                                    <button type="submit" name="action" value="remove"
                                            style="background: linear-gradient(135deg, #ff4b5c, #ff6f61);">Xóa
                                    </button>
                                </form>
                            </div>
                            <p class="item-price">${item.product.price}</p>
                            <button class="remove-item">×</button>
                        </div>

                    </div>
                </c:forEach>

                <a href="productList.jsp" class="back-to-shop">← Tiếp tục mua sắm</a>
            </div>
            <div class="summary">
                <h2>Summary</h2>
                <div class="summary-item">
                    <span>ITEMS 3</span>
                    <span>€ 132.00</span>
                </div>
                <div class="summary-item">
                    <span>SHIPPING</span>
                    <select>
                        <option>Standard-Delivery - €5.00</option>
                    </select>
                </div>
                <div class="summary-item">
                    <span>GIVE CODE</span>
                    <input type="text" placeholder="Enter your code">
                </div>
                <div class="summary-total">
                    <span>TOTAL PRICE</span>
                    <span>
                        <c:set var="total" value="0"/>
                    <c:forEach var="cartItem" items="${sessionScope.cartSession}">
                        <c:set var="product" value="${cartItem.product}"/>
                        <c:set var="total" value="${total + (product.price * cartItem.quantity)}"/>
                    </c:forEach>
                    <c:out value="${total} VNĐ"/>
                    </span>

                </div>
                <form action="checkout" method="get" style="display: inline;">
                    <button class="checkout" type="submit">Thanh toán</button>
                </form>
            </div>
        </c:when>
        <c:otherwise>
            <div class="button-container">
                <p class="empty-cart">Giỏ hàng của bạn trống.</p>
                <a href="productList.jsp">Quay lại danh sách sản phẩm</a>
            </div>
        </c:otherwise>
    </c:choose>

</div>

</body>
</html>
