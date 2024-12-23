package controller;

import dao.OrderDao;
import model.CartItem;
import model.Order;
import model.OrderItem;
import model.Product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ServletUpdateOrder", value = "/updateOrder")
public class ServletUpdateOrder extends HttpServlet {
    private OrderDao orderDao;

    @Override
    public void init() {
        orderDao = new OrderDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId"); // Lấy userId từ session
        Integer orderId = (Integer) session.getAttribute("orderId");

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer orderId = (Integer) session.getAttribute("orderId");
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

        List<OrderItem> orderItems = orderDao.getOrderDetails(orderId);
        float totalAmount = 0;
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

        // Cập nhật Order
        Order order = orderDao.getOrderById(orderId);
        order.setOrderId(orderId);
        order.setRecipientName(recipientName);
        order.setShippingAddress(shippingAddress);
        order.setShippingPhoneNumber(shippingPhoneNumber);
        order.setOrder_content(String.valueOf(orderContent));  // Lưu thông tin chi tiết đơn hàng

        boolean success = orderDao.updateOrderWithItems(order, orderItems);

        if (success) {
            response.sendRedirect("confirm?orderId=" + orderId);
        } else {
            response.sendRedirect("error.jsp");
        }
    }
}
