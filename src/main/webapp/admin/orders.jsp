<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" />
    <title>Quản lý đơn hàng</title>
    <style>
        * {
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }
        body {
            background-color: #f4f4f4;
            padding: 20px;
        }
        h1 {
            text-align: center;
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            background: white;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        th, td {
            padding: 15px;
            text-align: center;
            position: relative;
        }

        th {
            background-color: #002D62;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #e9e9e9;
        }
        .view_btn {
            padding: 9px 18px;
            border: none;
            color: white;
            font-weight: 500;
            background: #007bff;
            text-decoration: none;
            border-radius: 5px;
            cursor: pointer;
            margin: 5px;
        }

        .approve_btn{
            padding: 10px 18px;
            border: none;
            color: white;
            font-weight: 600;
            background: #10c20a;
            text-decoration: none;
            border-radius: 5px;
            cursor: pointer;
            margin: 5px;
        }

        .cancel_btn{
            padding: 10px 18px;
            border: none;
            color: white;
            font-weight: 600;
            background: #ff0000;
            text-decoration: none;
            border-radius: 5px;
            cursor: pointer;
            margin: 5px;
        }

        button[disabled] {
            background: gray;
            cursor: not-allowed;
        }
        form {
            display: inline;
        }
        .view_btn:hover {
            background: #0059ba;
        }
        .approve_btn:hover {
            background: #159800;
        }
        .cancel_btn:hover {
            background: #b60000;
        }

        td.delete {
            position: relative;
            overflow: hidden;
        }

        td.delete::after {
            content: "";
            position: absolute;
            top: 0;
            right: 0;
            width: 0;
            height: 100%;
            background: rgba(255, 0, 0, 0.8);
            transform-origin: right;
            transition: width 0.3s ease-in-out;
            z-index: 1;
        }

        td.delete:hover::after {
            width: 100%;

        }

        td.delete .delete-btn {
            opacity: 0;
            transform: translateX(100%);
            transition: transform 0.3s ease-in-out, opacity 0.3s ease-in-out;
            position: absolute;
            right: 15px;
            top: 50%;
            transform: translateY(-50%) translateX(100%);
            z-index: 2;
            font-size: 24px;
            background-color: transparent;
            color: white;
            border: none;
            padding: 8px 12px;
            border-radius: 5px;
            cursor: pointer;
        }

        td.delete:hover .delete-btn {
            opacity: 1;
            transform: translateY(-50%) translateX(0);
        }

        td.delete:active .delete-btn {
            color: #dcdcdc;
        }


        .delete{
            width: 80px;
        }

    </style>
</head>
<body>
<h1>Danh sách đơn hàng</h1>
<table>
    <tr>
        <th>Order ID</th>
        <th>User ID</th>
        <th>Order Date</th>
        <th>Total Amount</th>
        <th>Status</th>
        <th>Actions</th>
        <th></th>
    </tr>

    <c:choose>
        <c:when test="${empty orders}">
            <tr>
                <td colspan="7" style="text-align: center; font-weight: bold; color: #888888; font-size: 40px; cursor: none; pointer-events: none">
                    Chưa có đơn hàng nào
                </td>
            </tr>
        </c:when>
        <c:otherwise>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td>${order.orderId}</td>
                    <td>${order.userId}</td>
                    <td>${order.orderDate}</td>
                    <td>${order.totalAmount}</td>
                    <td>${order.status}</td>
                    <td class="option">
                        <form action="${pageContext.request.contextPath}/admin/orders-management" method="POST">
                            <input type="hidden" name="orderId" value="${order.orderId}" />
                            <button class="approve_btn" type="submit" name="action" value="approve">Approve</button>
                            <button class="cancel_btn" type="submit" name="action" value="cancel">Cancel</button>
                        </form>
                        <a class="view_btn" href="${pageContext.request.contextPath}/admin/orders-management?action=details&orderId=${order.orderId}">View</a>
                    </td>
                    <td class="delete" onclick="confirmDelete('${order.orderId}')">
                        <form action="${pageContext.request.contextPath}/admin/orders-management" method="POST" id="deleteForm-${order.orderId}">
                            <input type="hidden" name="orderId" value="${order.orderId}" />
                            <input type="hidden" name="action" value="delete" />
                            <button type="submit" class="delete-btn" onclick="event.stopPropagation();">
                                <i class="fa-solid fa-trash-can"></i>
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</table>
</body>
</html>