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
import model.CartItem;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDao {
    public void addProduct(int userId, int productId, int quantity, String sessionId) throws SQLException {
        String queryCheck = "SELECT quantity FROM Cart WHERE userId = ? AND productId = ?";
        try (Connection connection = new DbContext().getConnection();
             PreparedStatement stmtCheck = connection.prepareStatement(queryCheck)) {
            stmtCheck.setInt(1, userId);
            stmtCheck.setInt(2, productId);
            ResultSet rs = stmtCheck.executeQuery();

            if (rs.next()) {
                // Nếu sản phẩm đã tồn tại, cập nhật số lượng
                int currentQuantity = rs.getInt("quantity");
                String queryUpdate = "UPDATE Cart SET quantity = ? WHERE userId = ? AND productId = ?";
                try (PreparedStatement stmtUpdate = connection.prepareStatement(queryUpdate)) {
                    stmtUpdate.setInt(1, currentQuantity + quantity); // Tăng số lượng sản phẩm
                    stmtUpdate.setInt(2, userId);
                    stmtUpdate.setInt(3, productId);
                    stmtUpdate.executeUpdate();
                }
            } else {
                // Nếu sản phẩm chưa tồn tại, thêm mới vào giỏ hàng
                String queryInsert = "INSERT INTO Cart (userId, productId, quantity, sessionId, status) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement stmtInsert = connection.prepareStatement(queryInsert)) {
                    stmtInsert.setInt(1, userId);
                    stmtInsert.setInt(2, productId);
                    stmtInsert.setInt(3, quantity);
                    stmtInsert.setString(4, sessionId);
                    stmtInsert.setString(5, "pending"); // Hoặc bất kỳ trạng thái nào bạn muốn
                    stmtInsert.executeUpdate();
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CartItem> getCart(int userId) throws SQLException {
        List<CartItem> cartItems = new ArrayList<>();
        String query = "SELECT p.productId, p.name, p.price, p.stockQuantity, c.quantity " +
                "FROM Product p JOIN Cart c ON p.productId = c.productId WHERE c.userId = ?";

        try (Connection connection = new DbContext().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("productId"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setStockQuantity(rs.getInt("stockQuantity"));

                int quantity = rs.getInt("quantity"); // Lấy số lượng từ bảng Cart

                CartItem cartItem = new CartItem(product, quantity);
                cartItems.add(cartItem);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return cartItems;
    }

    // Phương thức để xóa sản phẩm khỏi giỏ hàng
    public void removeProduct(int userId, int productId) throws SQLException {
        String sql = "DELETE FROM cart WHERE user_id = ? AND product_id = ?";
        try (Connection conn = new DbContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Phương thức để cập nhật số lượng sản phẩm
    public void updateProductQuantity(int userId, int productId, int quantity) throws SQLException {
        String sql = "UPDATE cart SET quantity = ? WHERE user_id = ? AND product_id = ?";
        try (Connection conn = new DbContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, userId);
            stmt.setInt(3, productId);
            stmt.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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


    }
}

