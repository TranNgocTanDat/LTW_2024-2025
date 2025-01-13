package controller;

import dao.OrderDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@WebServlet(name = "ServletVerifySignUpdate", value = "/verifySignUpdate")
public class ServletVerifySignUpdate extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private OrderDao orderDao;

    @Override
    public void init() {
        orderDao = new OrderDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String orderIdIdParam = request.getParameter("orderId");
        int orderId = Integer.parseInt(orderIdIdParam);
        PrintWriter out = response.getWriter();

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

            boolean isSignatureValid = orderDao.verifySignature(orderId, decodedString);
            if (isSignatureValid) {
                request.setAttribute("orderId", orderId);
                response.sendRedirect("updateOrder?orderId=" + orderId);
            } else {
                request.setAttribute("error", "Chữ ký không đúng. Vui lòng thử lại.");
                request.getRequestDispatcher("viewOrder.jsp").forward(request, response);
            }
        }catch (Exception e) {
            e.printStackTrace();
            out.write("<h3 style='color:red;'>Lỗi trong quá trình xác thực: " + e.getMessage() + "</h3>");
        }


    }

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

}
