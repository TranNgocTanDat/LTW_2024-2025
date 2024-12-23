package controller;

import dao.CartDao;
import dao.OrderDao;
import model.CartItem;
import model.OrderItem;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ServletCheckout", value = "/checkout")
public class ServletCheckout extends HttpServlet {
    private CartDao cartDao;
    private OrderDao orderDao;

    public void init() {
        cartDao = new CartDao();
        orderDao = new OrderDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session == null || session.getId() == null) {
            throw new ServletException("Session ID is missing.");
        }

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        //Lay danh sach san pham trong gio hang
        try {
            List<CartItem> cartItems = cartDao.getCart(userId);

            double totalAmount = 0.0;
            for (CartItem item : cartItems) {
                totalAmount += item.getQuantity() * item.getProduct().getPrice();
            }

            // Gửi dữ liệu sang JSP
            request.setAttribute("cartItems", cartItems);
            request.setAttribute("totalAmount", totalAmount);
            request.getRequestDispatcher("/checkout.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session == null || session.getId() == null) {
            throw new ServletException("Session ID is missing.");
        }

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String recipientName = request.getParameter("recipientName");
        String shippingAddress = request.getParameter("shippingAddress");
        String shippingPhoneNumber = request.getParameter("shippingPhoneNumber");
        String notes = request.getParameter("notes");
        String paymentMethod = request.getParameter("paymentMethod");

        // Tạo chuỗi nội dung đơn hàng
        StringBuilder orderContent = new StringBuilder();
        orderContent.append("Khách hàng: ").append(recipientName).append(System.lineSeparator());
        orderContent.append("Địa chỉ giao hàng: ").append(shippingAddress).append(System.lineSeparator());
        orderContent.append("Số điện thoại: ").append(shippingPhoneNumber).append(System.lineSeparator());
        orderContent.append("Phương thức thanh toán: ").append(paymentMethod).append(System.lineSeparator());
        orderContent.append("Ghi chú: ").append(notes).append(System.lineSeparator());
        orderContent.append("Chi tiết sản phẩm:").append(System.lineSeparator());


        //Luu chi tiet don hang
        try {
            List<CartItem> cartItems = cartDao.getCart(userId);
            float totalAmount = 0;
            for (CartItem item : cartItems) {
                double itemTotal = item.getQuantity() * item.getProduct().getPrice();
                orderContent.append(item.getProduct().getName())
                        .append(" | Số lượng: ").append(item.getQuantity())
                        .append(" | Giá: ").append(item.getProduct().getPrice())
                        .append(" | Tổng: ").append(itemTotal)
                        .append("\n");
                totalAmount += itemTotal;
            }

            orderContent.append("Tổng tiền: ").append(totalAmount).append("\n");

            // Lưu đơn hàng vào cơ sở dữ liệu
            int orderId = orderDao.createOrder(userId, shippingAddress, recipientName, shippingPhoneNumber,  paymentMethod, totalAmount, notes, orderContent.toString());

            // Lưu chi tiết sản phẩm vào đơn hàng
            for (CartItem item : cartItems) {
                float discount = 0.0f; // Giả sử không có giảm giá, hoặc tính toán ở đây
                orderDao.addOrderItem(orderId, item.getProduct().getProductId(), item.getQuantity(), (float) item.getProduct().getPrice(), discount);
            }

            // Lưu orderId vào session
            session.setAttribute("orderId", orderId);

            // Xóa sản phẩm khỏi giỏ hàng
//            cartDao.clearCart(userId);

            // Chuyển hướng đến trang xác nhận đơn hàng
            response.sendRedirect("confirm?orderId=" + orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
