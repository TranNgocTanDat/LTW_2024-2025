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
        String shippingAddress = request.getParameter("shippingAddress");

        //Luu chi tiet don hang
        try {
            //Tao don hang
            int orderId = orderDao.createOrder(userId, shippingAddress);
            List<CartItem> cartItems = cartDao.getCart(userId);
            for (CartItem item : cartItems) {
                orderDao.addOrderItem(orderId, item.getProduct().getProductId(), item.getQuantity(), item.getProduct().getPrice());
            }

            // Xóa sản phẩm khỏi giỏ hàng
            cartDao.clearCart(userId);

            // Chuyển hướng đến trang xác nhận đơn hàng
            response.sendRedirect("confirm?orderId=" + orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
