package controller;

import dao.OrderDao;
import model.Order;
import model.OrderItem;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletUpdateOrder", value = "/updateOrder")
public class ServletUpdateOrder extends HttpServlet {
    private OrderDao orderDao;

    @Override
    public void init() {
        orderDao = new OrderDao();  // Khởi tạo DAO cho đơn hàng
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId"); // Lấy userId từ session
        String orderIdIdParam = request.getParameter("orderId");
        int orderId = Integer.parseInt(orderIdIdParam);

        if (userId == null) {
            response.sendRedirect("login.jsp"); // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
            return;
        }

        Order ordered = orderDao.getOrderById(orderId);
        List<OrderItem> orderItems = orderDao.getOrderDetails(orderId);
        request.setAttribute("ordered", ordered);
        request.setAttribute("orderItems", orderItems);
        request.getRequestDispatcher("editOrder.jsp").forward(request, response);
    }

    // Phương thức xử lý yêu cầu POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy các tham số từ form
        String orderIdParam = request.getParameter("orderId");
        int orderId = Integer.parseInt(orderIdParam); // Đảm bảo orderId là số nguyên

        String recipientName = request.getParameter("recipientName");
        String shippingAddress = request.getParameter("shippingAddress");
        String shippingPhoneNumber = request.getParameter("shippingPhoneNumber");
        String notes = request.getParameter("notes");
        String paymentMethod = request.getParameter("paymentMethod");

        // Tạo chuỗi thông tin đơn hàng
        StringBuilder orderContent = new StringBuilder();
        orderContent.append("Khách hàng: ").append(recipientName).append(System.lineSeparator());
        orderContent.append("Địa chỉ giao hàng: ").append(shippingAddress).append(System.lineSeparator());
        orderContent.append("Số điện thoại: ").append(shippingPhoneNumber).append(System.lineSeparator());
        orderContent.append("Phương thức thanh toán: ").append(paymentMethod).append(System.lineSeparator());
        orderContent.append("Ghi chú: ").append(notes).append(System.lineSeparator());
        orderContent.append("Chi tiết sản phẩm:").append(System.lineSeparator());

        // Lấy chi tiết đơn hàng từ OrderDao
        List<OrderItem> orderItems = orderDao.getOrderDetails(orderId);
        float totalAmount = 0;

        // Tính toán tổng tiền và chi tiết sản phẩm
        for (OrderItem item : orderItems) {
            double itemTotal = item.getQuantity() * item.getProduct().getPrice();
            orderContent.append(item.getProduct().getName())
                    .append(" | Số lượng: ").append(item.getQuantity())
                    .append(" | Giá: ").append(item.getProduct().getPrice())
                    .append(" | Tổng: ").append(itemTotal)
                    .append("\n");
            totalAmount += itemTotal;
        }

        orderContent.append("Tổng tiền: ").append(totalAmount).append("\n");

        // Cập nhật đơn hàng
        Order order = orderDao.getOrderById(orderId);
        order.setRecipientName(recipientName);
        order.setShippingAddress(shippingAddress);
        order.setShippingPhoneNumber(shippingPhoneNumber);
        order.setOrder_content(orderContent.toString()); // Lưu thông tin chi tiết đơn hàng

        // Cập nhật thông tin đơn hàng và các sản phẩm trong đơn
        boolean success = orderDao.updateOrderWithItems(order, orderItems);
        System.out.println(success);
        // Kiểm tra kết quả và chuyển hướng người dùng
        if (success) {
            // Chuyển hướng đến trang xác nhận đơn hàng
            response.sendRedirect("confirm?orderId=" + orderId);
        } else {
            // Nếu thất bại, chuyển hướng đến trang lỗi (có thể chỉnh sửa sau)
            response.sendRedirect("error.jsp");
        }
    }
}
