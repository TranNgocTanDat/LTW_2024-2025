package controller;

import com.google.gson.Gson;
import dao.CartDao;
import dao.OrderDao;
import model.CartItem;
import model.Order;
import model.OrderItem;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletConfirm", value = "/confirm")
public class ServletConfirm extends HttpServlet {
    private OrderDao orderDao;
    private CartDao cartDao;

    @Override
    public void init() throws ServletException {
        orderDao = new OrderDao();
        cartDao = new CartDao();
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

        try {
            List<CartItem> cartItems = cartDao.getCart(userId);

            double totalAmount = 0.0;
            for (CartItem item : cartItems) {
                totalAmount += item.getQuantity() * item.getProduct().getPrice();
            }

            System.out.println("Cart Items:");
            for (CartItem item : cartItems) {
                System.out.println("Product: " + item.getProduct().getName() + ", Quantity: " + item.getQuantity() + ", Price: " + item.getProduct().getPrice());
            }

            // Gửi dữ liệu sang JSP
            request.setAttribute("cartItems", cartItems);
            request.setAttribute("totalAmount", totalAmount);

            // Kiểm tra xem tham số orderId có tồn tại trong request không
            Integer orderId = (Integer) session.getAttribute("orderId");
            if (orderId == null) {
                response.sendRedirect("checkout.jsp");  // Hoặc trang lỗi
                return;
            }

            if (orderId == null) {
                // Nếu không có orderId, chuyển hướng hoặc trả về lỗi
                response.sendRedirect("error.jsp");  // Hoặc trang lỗi thích hợp
                return;
            }
            // Lấy thông tin đơn hàng
            Order order = orderDao.getOrderById(orderId);
            if (order == null) {
                // Nếu không tìm thấy thông tin đơn hàng, chuyển hướng về trang lỗi
                response.sendRedirect("index.jsp");  // Hoặc trang lỗi
                return;
            }

            // Gửi thông tin tới JSP
            // Trong Servlet, chuyển cartItems thành chuỗi JSON
            try {
                // Chuyển đổi cartItems thành JSON
                Gson gson = new Gson();
                String cartItemsJson = gson.toJson(cartItems);

                // Truyền dữ liệu sang JSP
                request.setAttribute("cartItemsJson", cartItemsJson);
                request.setAttribute("order", order);
                request.setAttribute("totalAmount", totalAmount);
                request.getRequestDispatcher("orderConfirmation.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace(); // Log lỗi
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi xử lý yêu cầu");
            }


        } catch (Exception e) {
            // Xử lý các lỗi (SQLException, etc.)
            e.printStackTrace();
            response.sendRedirect("error.jsp");  // Hoặc trang lỗi chung
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);  // Gọi lại doGet nếu không có xử lý gì đặc biệt trong doPost
    }
}
