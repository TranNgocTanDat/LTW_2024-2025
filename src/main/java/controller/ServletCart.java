package controller;

import dao.CartDao;
import dao.ProductDao;
import model.CartItem;
import model.Product;

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
    private ProductDao productDao;

    @Override
    public void init() {
        cartDAO = new CartDao();
        productDao = new ProductDao();// Khởi tạo CartDAO
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(); // Lấy session
        Integer userId = (Integer) session.getAttribute("userId"); // Lấy userId từ session

        if (userId == null) {
            response.sendRedirect("login.jsp"); // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
            return;
        }

        List<Product> products;
        products = productDao.getRandomProducts(4);
        List<Product> productsNewProduct;
        productsNewProduct = productDao.getRandomProductNewProduct(4);

        request.setAttribute("productsNewProduct", productsNewProduct);

        request.getRequestDispatcher("cart.jsp").forward(request, response);

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
        if (session == null || session.getId() == null) {
            throw new ServletException("Session ID is missing.");
        }

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String productIdStr = request.getParameter("productId");
        String quantityStr = request.getParameter("quantity");

        if (productIdStr == null || quantityStr == null || action == null) {
            throw new ServletException("Product ID, Quantity, or Action is missing.");
        }

        try {
            int productId = Integer.parseInt(productIdStr);
            int quantity = Integer.parseInt(quantityStr);

            if (quantity <= 0) {
                throw new ServletException("Quantity must be greater than 0.");
            }

            switch (action) {
                case "add":
                    cartDAO.addProduct(userId, productId, quantity, session.getId());
                    break;
                case "remove":
                    cartDAO.removeProduct(userId, productId);
                    break;
                case "increase":
                    cartDAO.increaseProductQuantity(userId, productId);
                    break;
                case "decrease":
                    cartDAO.decreaseProductQuantity(userId, productId);
                    break;
                default:
                    throw new ServletException("Invalid action.");
            }

            List<CartItem> cart = cartDAO.getCart(userId);
            session.setAttribute("cartSession", cart);
            response.sendRedirect("cart");

        } catch (SQLException e) {
            throw new ServletException("Error processing cart action: " + e.getMessage(), e);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid number format for product ID or quantity.", e);
        }
    }

}
