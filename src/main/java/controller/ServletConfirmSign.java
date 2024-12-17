package controller;

import dao.OrderDao;
import dao.UserKeyDao;
import model.ChuKi_model;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

import static model.ChuKi_model.generateHash;

@WebServlet(name = "ServletConfirmSign", value = "/confirmSign")
public class ServletConfirmSign extends HttpServlet {
    private OrderDao orderDao;
    private UserKeyDao userKeyDao;

    public void init() {
        orderDao = new OrderDao();
        userKeyDao = new UserKeyDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();// Lưu orderId vào request hoặc session để gửi sang JSP
        Integer orderId = (Integer) session.getAttribute("orderId");
        request.setAttribute("orderId", orderId);

        // Chuyển tiếp yêu cầu sang JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/orderSign.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);

        //check session
        if (session == null || session.getAttribute("userId") == null) {
            response.getWriter().write("Vui lòng đăng nhập trước khi tạo key");
            return;
        }

        int userId = (int) session.getAttribute("userId");
       // Lưu orderId vào request hoặc session để gửi sang JSP
        Integer orderId = (Integer) session.getAttribute("orderId");

        Part signatureFilePart = request.getPart("signatureFile");

        System.out.println("signatureFilePart: " + signatureFilePart);

        if (signatureFilePart == null) {
            out.write("Thiếu dữ liệu cần thiết.");
            return;
        }

        try {
            // Đọc chữ ký tải lên
            byte[] uploadedSignature = readBytesFromPart(signatureFilePart);
            String signatureBase64 = Base64.getEncoder().encodeToString(uploadedSignature); // Chuyển sang Base64

            byte[] decodedBytes = base64StringToBytes(signatureBase64);
            String decodedString = bytesToString(decodedBytes);
            System.out.println("Chuỗi đã giải mã từ Base64: " + decodedString);
//            String signatureBase64 = "ccYXi1zIvRWp+gu2avP+OY4H8PuU+p50/ZxJdgtCJRdz6S8wfbVtw1N5niHR0v5hgs2CmGaT09qnTuzc9nyvV4VH+dsIL2Vta+APIYGFdwiJTVlzfakuZmfFMlnNEqvcz4+hV7AKM8VsFSsDttx3t+SCc4IbjVs6wykQZEPanIYOtlbeUw5RlsNEFoxVukVh2oZIZtIqoekthEcJAJZxeUKa7erzNd7HdiiDT05UIoQ5Ot7SLGSUhsoopuw1UCj8RzyzfFBKQaQz3Qyx1rS7qrIVkmHHfyeXHsyKildVa6L12AgZ52ToCqkycf2EJMW+KLJ9MByGNF58FqmqWBQ1UQ==";
            System.out.println("File name: " + signatureFilePart.getSubmittedFileName());
            System.out.println("File size: " + signatureFilePart.getSize());


            String contentOrder = orderDao.getOrderContentById(orderId);
            if (contentOrder == null) {
                out.write("Không tìm thấy đơn hàng.");
                return;
            }

            // Tính hash của content_order
            byte[] contentHash = hashContent(contentOrder);
//            System.out.println(contentHash);

            // Tải khóa công khai (public key)
            PublicKey publicKey = userKeyDao.getPublicKeyById(userId);

            // Xác minh chữ ký
            boolean isValid = verifySignature(contentOrder, decodedString, publicKey);

            if (isValid) {
                // Xác minh thành công -> Lưu chữ ký vào database
                orderDao.saveSignature(orderId, decodedString);
                out.write("<h3 style='color:green;'>Xác thực chữ ký thành công và đã lưu vào hệ thống!</h3>");
                System.out.println("sucess");
            } else {
                // Chữ ký không hợp lệ
                out.write("<h3 style='color:red;'>Chữ ký không hợp lệ!</h3>");
            }

            request.setAttribute("orderId", orderId);
            response.sendRedirect("confirm?orderId=" + orderId);
        } catch (Exception e) {
            e.printStackTrace();
            out.write("<h3 style='color:red;'>Lỗi trong quá trình xác thực: " + e.getMessage() + "</h3>");
        }

    }

    // Đọc bytes từ file tải lên
    private byte[] readBytesFromPart(Part part) throws IOException {
        try (InputStream inputStream = part.getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        }
    }

    // Chuyển từ String sang byte[] sử dụng Base64 encoding
    public static byte[] base64StringToBytes(String base64) {
        return Base64.getDecoder().decode(base64);
    }

    // Chuyển từ byte[] sang String với UTF-8 decoding
    public static String bytesToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public String normalizeContent(String content) {
        return content.replace("\r\n", "\n").trim(); // Thay \r\n bằng \n và loại bỏ khoảng trắng đầu/cuối
    }
    // Hash nội dung chuỗi (SHA-256)
    private byte[] hashContent(String content) throws NoSuchAlgorithmException {
        content = normalizeContent(content);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(content.getBytes());
    }
    // Xác minh chữ ký
    private boolean verifySignature(String message, String signatured, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        if (publicKey == null) {
            throw new Exception("Cặp khóa chưa được khởi tạo.");
        }

        if (signatured == null || signatured.isEmpty()) {
            throw new IllegalArgumentException("Chuỗi chữ ký không thể rỗng.");
        }

        byte[] signatureBytes = Base64.getDecoder().decode(signatured);
        byte[] hashedMessage = generateHash(message);

        signature.initVerify(publicKey);
        signature.update(hashedMessage);

        return signature.verify(signatureBytes);
    }
}

