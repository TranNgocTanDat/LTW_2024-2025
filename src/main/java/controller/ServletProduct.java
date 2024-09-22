package controller;

import dao.ProductDao;
import model.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletProduct", value = "/admin/products")
public class ServletProduct extends HttpServlet {

    private ProductDao productDao;

    public void init() {
        productDao = new ProductDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "new":
                showNewProductForm(request, response);
                break;
            case "edit":
                showEditProductForm(request, response);
                break;
            case "delete":
                deleteProduct(request, response);
                break;
            default:
                listProducts(request, response);
                break;
        }
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productDao.getAll();
        request.setAttribute("products", products);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/productList.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewProductForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/product-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditProductForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("id"));
        Product existingProduct = productDao.getProductById(productId);
        request.setAttribute("product", existingProduct);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/product-form.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int productId = Integer.parseInt(request.getParameter("id"));
        productDao.deleteProduct(productId);
        response.sendRedirect(request.getContextPath() + "/admin/products"); // Redirect đúng đường dẫn
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("insert".equals(action)) {
            insertProduct(request, response);
        } else if ("update".equals(action)) {
            updateProduct(request, response);
        }
    }

    private void insertProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        String category = request.getParameter("category");
        String size = request.getParameter("size");
        String color = request.getParameter("color");
        int stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));

        Product newProduct = new Product(0, name, description, price, category, size, color, stockQuantity);
        productDao.addProduct(newProduct);
        response.sendRedirect(request.getContextPath() + "/admin/products"); // Redirect đúng đường dẫn
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int productId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        String category = request.getParameter("category");
        String size = request.getParameter("size");
        String color = request.getParameter("color");
        int stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));

        Product updatedProduct = new Product(productId, name, description, price, category, size, color, stockQuantity);
        productDao.updateProduct(updatedProduct);
        response.sendRedirect(request.getContextPath() + "/admin/products"); // Redirect đúng đường dẫn
    }
}