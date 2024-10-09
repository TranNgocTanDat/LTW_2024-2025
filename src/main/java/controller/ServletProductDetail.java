package controller;

import dao.ProductDao;
import model.Product;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletProductDetail", value = "/product-detail")
public class ServletProductDetail extends HttpServlet {
    private ProductDao productDao;

    public void init(){
        productDao = new ProductDao();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdParam = request.getParameter("productId");
        int productId = Integer.parseInt(productIdParam);

        // Lấy thông tin sản phẩm từ database
        Product product = productDao.getProductById(productId);

        // Đưa sản phẩm vào request
        request.setAttribute("product", product);
        request.getRequestDispatcher("productDetail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
