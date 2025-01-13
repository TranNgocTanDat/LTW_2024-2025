<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kết quả tìm kiếm</title>
    <style>
        /* Tổng thể */
        body {
            font-family: 'Poppins', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
            color: #333;
        }

        /* Tiêu đề */
        h1 {
            /*text-align: center;*/
            color: #222;
            font-size: 2rem;
            margin-left: 30px;
            margin-top: 20px;
            margin-bottom: 40px;
        }

        /* Container sản phẩm */
        .products-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
            gap: 20px;
            padding: 20px;
            margin-bottom: 50px;
        }

        /* Card sản phẩm */
        .product {
            background-color: #fff;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .product:hover {
            transform: translateY(-10px);
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
        }

        .product img {
            width: 100%;
            height: auto;
            display: block;
        }

        .product-details {
            padding: 15px;
            text-align: center;
        }

        .product h2 {
            font-size: 1.2rem;
            color: #555;
            margin: 10px 0;
        }

        .product h2 a {
            text-decoration: none;
            color: #007BFF;
        }

        .product h2 a:hover {
            color: #0056b3;
            text-decoration: underline;
        }

        .product p {
            font-size: 0.9rem;
            color: #666;
            margin: 5px 0;
        }

        .product p.price {
            font-size: 1rem;
            color: #e74c3c;
            font-weight: bold;
        }

        /* Active buttons */
        .active {
            display: flex;
            justify-content: center;
            gap: 20px;
            margin-top: 10px;
        }

        .btn__price, .btn__view {
            padding: 10px 20px;
            font-size: 0.9rem;
            color: #fff;
            background-color: #007BFF;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .btn__price:hover, .btn__view:hover {
            background-color: #0056b3;
        }

        .btn__price {
            background-color: #e74c3c;
        }

        .btn__price:hover {
            background-color: #c0392b;
        }

        form .btn-submit {
            padding: 10px 20px;
            font-size: 0.9rem;
            color: #fff;
            background-color: #27ae60;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        form .btn-submit:hover {
            background-color: #1e8449;
        }

        /* Thông báo không tìm thấy */
        .no-results {
            text-align: center;
            font-size: 1.2rem;
            color: #777;
            margin-top: 50px;
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
<h1>Kết quả tìm kiếm cho: <%= request.getParameter("keyword") %></h1>

<c:choose>
    <c:when test="${not empty products}">
        <div class="products-container">
            <c:forEach var="product" items="${products}">
                <div class="product">
                    <img src="${product.imageUrl}" alt="${product.name}">
                    <div class="product-details">
                        <h2>${product.name}</h2>
                        <p>${product.description}</p>
                        <p class="price">${product.price} VNĐ</p>
                        <div class="active">
                            <button class="btn__view" onclick="viewProduct(${product.productId})">Xem</button>
                            <form action="cart" method="post">
                                <input type="hidden" name="action" value="add">
                                <input type="hidden" name="userId" value="${sessionScope.userId}">
                                <input type="hidden" name="productId" value="${product.productId}">
                                <input type="number" name="quantity" value="1" min="1" max="${product.stockQuantity}" required hidden>
                                <button class="btn-submit" type="submit">Thêm vào Giỏ hàng</button>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:when>
    <c:otherwise>
        <p class="no-results">Không tìm thấy sản phẩm nào. Hãy thử tìm kiếm với từ khóa khác.</p>
    </c:otherwise>
</c:choose>
</body>
</html>
