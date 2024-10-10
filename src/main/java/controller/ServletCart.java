package controller;

import dao.CartDao;
import model.CartItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ServletCart", value = "/cart")
public class ServletCart extends HttpServlet {
    private CartDao cartDAO;

    @Override
    public void init() {
        cartDAO = new CartDao(); // Khởi tạo CartDAO
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(); // Lấy session
        Integer userId = (Integer) session.getAttribute("userId"); // Lấy userId từ session

        if (userId == null) {
            response.sendRedirect("login.jsp"); // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
            return;
        }

        try {
            List<CartItem> cart = cartDAO.getCart(userId); // Lấy giỏ hàng từ CartDAO
            request.setAttribute("cart", cart); // Đưa cart vào request scope

            // Lưu cart vào session để sử dụng sau này
            session.setAttribute("cartSession", cart);

            request.getRequestDispatcher("cart.jsp").forward(request, response); // Chuyển hướng đến trang giỏ hàng
        } catch (SQLException e) {
            throw new ServletException("Error retrieving cart", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");

        try {
            int productId = Integer.parseInt(request.getParameter("productId"));

            if ("add".equals(action)) {
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                // Thêm sản phẩm vào giỏ hàng
                cartDAO.addProduct(userId, productId, quantity, request.getSession().getId());
            } else if ("remove".equals(action)) {
                // Xóa sản phẩm khỏi giỏ hàng
                cartDAO.removeProduct(userId, productId);
            } else if ("update".equals(action)) {
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                // Cập nhật số lượng sản phẩm
                cartDAO.updateProductQuantity(userId, productId, quantity);
            }

            // Cập nhật giỏ hàng trong session sau khi thực hiện hành động
            List<CartItem> cart = cartDAO.getCart(userId);
            session.setAttribute("cartSession", cart);

            response.sendRedirect("cart");
        } catch (SQLException e) {
            throw new ServletException("Error processing cart action: " + e.getMessage(), e);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid number format: " + e.getMessage(), e);
        }
    }
}