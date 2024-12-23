<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Page</title>
    <link rel="stylesheet" href="styles.css">
    <style>
        /* Tổng thể */
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
            color: #333;
        }

        .container {
            width: 90%;
            max-width: 1200px;
            margin: 20px auto;
            background-color: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        h1, h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        /* Navigation Bar */
        .nav {
            display: flex;
            justify-content: space-around;
            background-color: #004085;
            padding: 10px 20px;
        }

        .nav a {
            color: white;
            text-decoration: none;
            font-weight: bold;
            font-size: 14px;
            transition: color 0.3s ease;
        }

        .nav a:hover {
            color: #ddd;
        }

        button:hover {
            background-color: #0056b3;
        }

        a.btn-edit, a.btn-delete {
            display: inline-block;
            padding: 8px 15px;
            text-decoration: none;
            border-radius: 5px;
            font-size: 14px;
            font-weight: bold;
            color: white;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }

        /* Nút Edit */
        a.btn-edit {
            background-color: #4CAF50;
            margin-bottom: 10px;
            width: 60px;
        }
        a.btn-edit:active {
            background-color: #3a873d;
        }


        a.btn-delete {
            background-color: #f44336;
            width: 60px;
        }
        a.btn-delete:active {
            background-color: #d63531;
        }

        /* Tables */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: white;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 15px;
            text-align: center;
            border: 1px solid #ddd;
        }

        th {
            background-color: #002D62;
            color: white;
            font-weight: 600;
        }

        td {
            font-size: 14px;
            color: #333;
        }

        tr:nth-child(even) {
            background-color: #f8f9fa;
        }

        tr:hover {
            background-color: #e2e6ea;
            transition: background-color 0.3s ease;
        }

        /* Images */
        td img {
            border-radius: 5px;
            transition: transform 0.3s ease;
        }

        td img:hover {
            transform: scale(1.1);
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .container {
                padding: 10px;
            }

            table, th, td {
                font-size: 12px;
            }

            th, td {
                padding: 10px;
            }


            button {
                padding: 8px 10px;
                font-size: 12px;
            }
        }
        .description {
            display: block;
            font-size: 14px;
            line-height: 1.5;
            font-family: Arial, sans-serif;
            text-align: justify;
            max-height: 80px;
            max-width: 300px;
            padding: 8px;
            border: 1px solid #ddd;
            overflow-y: auto;
            word-break: break-word;
            border-radius: 5px;
            scroll-behavior: smooth;
            scrollbar-width: thin;
        }

        a.btn-add {
            display: inline-block;
            padding: 10px 20px;
            text-decoration: none;
            background-color: #007BFF;
            color: white;
            font-size: 16px;
            font-weight: bold;
            border-radius: 5px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            transition: background-color 0.3s ease, transform 0.2s ease;
        }

        /* Khi bấm giữ nút */
        a.btn-add:active {
            background-color: #004085;
        }



    </style>

</head>
<body>
<div class="container">
    <h1>Trang Quản Trị Admin</h1>

    <!-- Section Quản lý sản phẩm -->
    <div id="products" class="section">
        <h2>Quản lý sản phẩm</h2>
        <a href="product-form.jsp?action=new" class="btn-add">Thêm sản phẩm mới</a>
        <table>
                <tr>
                    <th>Tên sản phẩm</th>
                    <th>Mô tả</th>
                    <th>Giá</th>
                    <th>Loại sản phẩm</th>
                    <th>Kích cỡ</th>
                    <th>Màu</th>
                    <th>Còn trong kho</th>
                    <th>Đường dẫn ảnh</th>
                    <th>Xử lý</th>

                </tr>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td>${product.name}</td>
                        <td class="description">${product.description}</td>
                        <td>${product.price}</td>
                        <td>${product.category}</td>
                        <td>${product.size}</td>
                        <td>${product.color}</td>
                        <td>${product.stockQuantity}</td>
                        <td><img src="${product.imageUrl}" width="50px" height="50px"></td>
                        <td>
                            <a href="products?action=edit&id=${product.productId}" class="btn-edit">Thay đổi</a>
                            <a href="products?action=delete&id=${product.productId}" class="btn-delete" onclick="return confirm('Are you sure you want to delete this product?');">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
    </div>
</div>
</body>
</html>