<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Xác nhận chữ ký điện tử trước khi thanh toán</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 20px auto;
            max-width: 800px;
            color: #333;
        }
        h1, h2, h3 {
            color: #007BFF;
        }
        form {
            margin-bottom: 20px;
        }
        button {
            background-color: #007BFF;
            color: white;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            border-radius: 5px;
        }
        button:hover {
            background-color: #0056b3;
        }
        textarea {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .content-section {
            margin-top: 20px;
            border: 1px solid #ddd;
            padding: 15px;
            border-radius: 5px;
            background-color: #f9f9f9;
        }
        .alert-success {
            color: green;
            margin-bottom: 20px;
        }
        .alert-error {
            color: red;
            margin-bottom: 20px;
        }
        footer {
            margin-top: 50px;
            text-align: center;
            color: #666;
            font-size: 14px;
        }

    </style>
    <script>
        function downloadKeyFile(content, filename) {
            const blob = new Blob([content], { type: "text/plain" });
            const link = document.createElement("a");
            link.href = URL.createObjectURL(blob);
            link.download = filename;
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }

        function savePrivateKey(privateKey) {
            downloadKeyFile(privateKey, "privateKey.txt");
        }

        function savePublicKey(publicKey) {
            downloadKeyFile(publicKey, "publicKey.txt");
        }
    </script>
</head>
<body>
<h1>Hệ thống xác thực chữ ký điện tử</h1>
<p>Chào mừng bạn đến với hệ thống. Trang này hỗ trợ bạn tạo và quản lý khóa RSA, cũng như xác thực chữ ký điện tử để thanh toán đơn hàng.</p>

<!-- Bước 1: Tạo khóa RSA -->
<div class="content-section">
    <h3>Bước 1: Tạo khóa RSA</h3>
    <p>Nhấn nút "Generate Key" để tạo cặp khóa RSA. Sau khi tạo, bạn có thể lưu khóa của mình.</p>
    <form method="post" action="signOrder">
        <button type="submit">Generate Key</button>
        <c:if test="${not empty successMessage}">
            <div class="alert-success">${successMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert-error">${errorMessage}</div>
        </c:if>
    </form>
</div>

<!-- Hiển thị thông tin khóa nếu đã tạo -->
<c:if test="${not empty privateKey}">
    <div class="content-section">
        <h3> Lưu khóa của bạn</h3>
        <p>Khóa của bạn đã được tạo thành công. Vui lòng lưu lại các khóa để sử dụng trong tương lai.</p>
        <p><strong>Private Key:</strong></p>
        <textarea readonly rows="5">${privateKey}</textarea>
        <button type="button" onclick="savePrivateKey('${privateKey}')">Lưu Private Key vào thư mục</button>
        <p><strong>Public Key:</strong></p>
        <textarea readonly rows="5">${publicKey}</textarea>
        <button type="button" onclick="savePublicKey('${publicKey}')">Lưu Public Key vào thư mục</button>
    </div>
</c:if>

<!-- Tải xuống công cụ hỗ trợ -->
<div class="content-section">
    <h3>Bước 2: Tải xuống công cụ hỗ trợ</h3>
    <form action="${pageContext.request.contextPath}/downloadTool" method="get">
        <button type="submit">Tải xuống Tool</button>
    </form>
</div>

<!-- Bước 3: Xác thực chữ ký -->
<div class="content-section">
    <h3>Bước 3: Xác thực chữ ký điện tử</h3>
    <p>Tải lên chữ ký điện tử để xác thực đơn hàng của bạn.</p>
    <form id="confirmSignForm" action="${pageContext.request.contextPath}/confirmSign" method="post" enctype="multipart/form-data">
        <input type="hidden" name="orderId" value="${orderId}">
        <div>
            <label for="signatureFile">Chọn file chữ kí: </label>
            <input type="file" name="signatureFile" id="signatureFile" required>
        </div>
        <div style="margin-top: 20px">
            <button type="submit">Xác Thực Đơn Hàng</button>
        </div>

    </form>
</div>



<!-- Hiển thị thông báo -->
<div>
    <c:if test="${not empty successMessage}">
        <div class="alert-success">${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert-error">${errorMessage}</div>
    </c:if>
</div>

<!-- Footer -->
<footer>
    <p>© 2024 - Hệ thống xác thực chữ ký điện tử. Mọi quyền được bảo lưu.</p>
</footer>
</body>
</html>
