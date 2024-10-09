//package dao;
//
//import context.DbContext;
//import model.Cart;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class CartDao {
//    Connection connection =null;
//    PreparedStatement ps = null;
//    ResultSet rs = null;
//
//    public int addCart(Cart cart) throws SQLException {
//        String query = "INSERT INTO Cart (userId, productId, quantity, sessionId, status) VALUES (?, ?, ?, ?, ?)";
//        try {
//            connection = new DbContext().getConnection();
//            ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//            ps.setInt(1, cart.getUserId());
//            ps.setInt(2, cart.getProductId());
//            ps.setInt(3, cart.getQuantity());
//            ps.setString(4, cart.getSessionId());
//            ps.setString(5, cart.getStatus());
//
//            int affectedRows = ps.executeUpdate();
//            if (affectedRows == 0) {
//                throw new SQLException("Failed to create cart, no rows affected.");
//            }
//
//            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    return generatedKeys.getInt(1); // Trả về `cartId` vừa được tạo
//                } else {
//                    throw new SQLException("Failed to create cart, no ID obtained.");
//                }
//            }
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // Lấy danh sách sản phẩm trong giỏ hàng của người dùng
//    public List<Cart> getCartItems(int userId) throws SQLException {
//        List<Cart> cartItems = new ArrayList<>();
//        String query = "SELECT * FROM Cart WHERE userId = ?";
//
//        try  {
//            connection = new DbContext().getConnection();
//            ps = connection.prepareStatement(query);
//            ps.setInt(1, userId);
//            rs = ps.executeQuery();
//                while (rs.next()) {
//                    int cartId = rs.getInt("cartId");
//                    int productId = rs.getInt("productId");
//                    int quantity = rs.getInt("quantity");
//                    String sessionId = rs.getString("sessionId");
//                    String status = rs.getString("status");
//
//                    Cart cart = new Cart(userId, productId, quantity, sessionId, status);
//                    cartItems.add(cart);
//                }
//
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        return cartItems;
//    }
//
//    public static void main(String[] args) {
//        CartDao cartDao = new CartDao();
//
//    }
//}
package dao;

import context.DbContext;
import model.Cart;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDao {
    public void addProduct(int userId, int productId, int quantity, String sessionId) throws SQLException {
        String query = "IF EXISTS (SELECT * FROM Cart WHERE userId = ? AND productId = ?) " +
                "BEGIN " +
                "   UPDATE Cart SET quantity = quantity + ? WHERE userId = ? AND productId = ?; " +
                "END " +
                "ELSE " +
                "BEGIN " +
                "   INSERT INTO Cart (userId, productId, quantity, sessionId) VALUES (?, ?, ?, ?); " +
                "END";

        try (Connection connection = new DbContext().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Thiết lập các tham số
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            statement.setInt(3, quantity); // Số lượng để cập nhật
            statement.setInt(4, userId);
            statement.setInt(5, productId);
            statement.setInt(6, userId);
            statement.setInt(7, productId);
            statement.setInt(8, quantity); // Số lượng để thêm
            statement.setString(9, sessionId); // sessionId

            // Thực thi câu lệnh
            statement.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Product> getCart(int userId) throws SQLException {
        List<Product> cart = new ArrayList<>();
        String query = "SELECT p.productId, p.name, p.price, p.description, p.category, " +
                "p.size, p.color, p.stockQuantity, p.imageUrl, c.quantity " +
                "FROM Cart c JOIN Product p ON c.productId = p.productId " +
                "WHERE c.userId = ?";

        try (Connection connection = new DbContext().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("productId");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                String category = rs.getString("category");
                String size = rs.getString("size");
                String color = rs.getString("color");
                int stockQuantity = rs.getInt("stockQuantity");
                String imageUrl = rs.getString("imageUrl");
                cart.add(new Product(productId, name,description, price, category, size, color, stockQuantity, imageUrl));
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return cart;
    }

    public static void main(String[] args) throws SQLException {
        CartDao cartDao = new CartDao();

        // Thay đổi thông tin này theo nhu cầu của bạn
        int userId = 1; // ID người dùng
        int productId = 1; // ID sản phẩm
        int quantity = 2; // Số lượng
        String sessionId = "session123"; // ID phiên
        cartDao.addProduct(userId, productId, quantity, sessionId);
        System.out.println("Product added to cart successfully.");

        // Lấy danh sách sản phẩm trong giỏ hàng
        List<Product> cart = cartDao.getCart(userId);

        for (Product product : cart) {
            System.out.println("Product ID: " + product.getProductId() + ", Name: " + product.getName() + ", Price: " + product.getPrice() + ", Quantity: " + product.getStockQuantity());
        }

    }
}

