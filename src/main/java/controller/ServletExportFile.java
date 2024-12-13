package controller;

import model.CartItem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@WebServlet(name = "ServletExportFile", value = "/export")
public class ServletExportFile extends HttpServlet {

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String shippingAddress = request.getParameter("shippingAddress");

            StringBuilder content = new StringBuilder();
            content.append("Địa chỉ giao hàng: ").append(shippingAddress).append("\n");

            content.append("\nSản phẩm trong giỏ hàng:\n");
            List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems"); // Hoặc lấy từ session
            double totalAmount = 0;
            for (CartItem item : cartItems) {
                content.append("Tên sản phẩm: ").append(item.getProduct().getName()).append("\n");
                content.append("Số lượng: ").append(item.getQuantity()).append("\n");
                content.append("Giá: ").append(item.getProduct().getPrice()).append("\n");
                content.append("Thành tiền: ").append(item.getQuantity() * item.getProduct().getPrice()).append("\n");
                totalAmount += item.getQuantity() * item.getProduct().getPrice();
            }
            content.append("\nTổng cộng: ").append(totalAmount).append("\n");
            // Tạo một tài liệu Word mới

            // Tạo file .docx bằng Apache POI
            XWPFDocument document = new XWPFDocument();
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.createRun().setText(content.toString());

            // Đặt kiểu content và tên file cho phản hồi HTTP
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            response.setHeader("Content-Disposition", "attachment; filename=checkoutInfo.docx");

            // Ghi file vào response
            try (OutputStream out = response.getOutputStream()) {
                document.write(out);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Đóng tài liệu để giải phóng tài nguyên
            document.close();
        }

}
