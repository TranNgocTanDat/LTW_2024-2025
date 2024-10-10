package controller;


import dao.ProductDao;
import model.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "ServletProduct", value = "/products")
public class ServletProduct extends HttpServlet {
    private ProductDao productDao;

    public void init() {
        productDao = new ProductDao();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter("category");
        List<Product> products;
        // Kiểm tra nếu category là null thì lấy tất cả sản phẩm
        if (category == null || category.isEmpty()) {
            products = productDao.getAll(); // Lấy tất cả sản phẩm
        } else {
            products = productDao.getProductsByCategory(category); // Lấy sản phẩm theo category
        }

        // Set products vào request attribute để hiển thị trong JSP
        request.setAttribute("products", products);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/products.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
