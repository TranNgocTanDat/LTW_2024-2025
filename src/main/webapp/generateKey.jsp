
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Generate RSA Key</title>
    <script>
        async function saveKeyToDirectory(key, filename) {
            try {
                const dirHandle = await window.showDirectoryPicker();
                const fileHandle = await dirHandle.getFileHandle(filename, { create: true });
                const writable = await fileHandle.createWritable();
                await writable.write(key);
                await writable.close();
                alert(`Lưu thành công file: ${filename}`);
            } catch (error) {
                console.error("Error saving file:", error);
                alert("Đã xảy ra lỗi khi lưu file!");
            }
        }

        async function handleKeySaveToDirectory(publicKey, privateKey) {
            if (confirm("Key của bạn đã được tạo thành công! Bạn có muốn chọn thư mục để lưu khóa không?")) {
                await saveKeyToDirectory(publicKey, "publicKey.txt");
                await saveKeyToDirectory(privateKey, "privateKey.txt");
            }
        }

        async function savePublicKeyToDirectory(publicKey) {
            await saveKeyToDirectory(publicKey, "publicKey.txt");
        }

        async function savePrivateKeyToDirectory(privateKey) {
            await saveKeyToDirectory(privateKey, "privateKey.txt");
        }
    </script>

</head>
<body>
<h1>Generate RSA Key</h1>
<form method="post" action="key">
    <button type="submit">Generate Key</button>
</form>
<c:if test="${not empty message}">
    <script>
        alert("${message}");
    </script>
    <div>
        <p>Khóa đã được tạo thành công. Dưới đây là thông tin khóa của bạn:</p>
        <p><strong>Private Key:</strong></p>
<%--        <textarea readonly rows="5" cols="80">${privateKey}</textarea>--%>
        <p>Khóa đã được tạo thành công. Bạn có thể lưu các khóa dưới đây:</p>
        <button type="button" onclick="savePublicKeyToDirectory('${publicKey}')">Lưu Public Key vào thư mục</button>
        <button type="button" onclick="savePrivateKeyToDirectory('${privateKey}')">Lưu Private Key vào thư mục</button>
    </div>
</c:if>
<form action="${pageContext.request.contextPath}/downloadTool" method="get">
    <button type="submit">Tải xuống Tool</button>

</form>
</body>
</html>
