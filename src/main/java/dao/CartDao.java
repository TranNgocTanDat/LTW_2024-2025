package dao;

import context.DbContext;
import model.CartItem;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDao {
    public void addProduct(int userId, int productId, int quantity, String sessionId) throws SQLException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0.");
        }

        String queryCheck = "SELECT quantity FROM Cart WHERE userId = ? AND productId = ?";
        try (Connection connection = new DbContext().getConnection();
             PreparedStatement stmtCheck = connection.prepareStatement(queryCheck)) {
            connection.setAutoCommit(false);  // Bắt đầu giao dịch
            stmtCheck.setInt(1, userId);
            stmtCheck.setInt(2, productId);
            ResultSet rs = stmtCheck.executeQuery();

            if (rs.next()) {
                // Sản phẩm đã tồn tại, cập nhật số lượng
                int currentQuantity = rs.getInt("quantity");
                String queryUpdate = "UPDATE Cart SET quantity = ? WHERE userId = ? AND productId = ?";
                try (PreparedStatement stmtUpdate = connection.prepareStatement(queryUpdate)) {
                    stmtUpdate.setInt(1, currentQuantity + quantity);
                    stmtUpdate.setInt(2, userId);
                    stmtUpdate.setInt(3, productId);
                    stmtUpdate.executeUpdate();
                }
            } else {
                // Sản phẩm chưa tồn tại, thêm vào giỏ hàng
                String queryInsert = "INSERT INTO Cart (userId, productId, quantity, sessionId) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmtInsert = connection.prepareStatement(queryInsert)) {
                    stmtInsert.setInt(1, userId);
                    stmtInsert.setInt(2, productId);
                    stmtInsert.setInt(3, quantity);
                    stmtInsert.setString(4, sessionId);
                    stmtInsert.executeUpdate();
                }
            }
            connection.commit();  // Xác nhận giao dịch
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Lỗi khi cập nhật giỏ hàng", e);
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
        String sql = "DELETE FROM cart WHERE userId = ? AND productId = ?";
        try (Connection conn = new DbContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Tăng số lượng sản phẩm trong giỏ hàng
    public void increaseProductQuantity(int userId, int productId) throws SQLException {
        String sql = "UPDATE Cart SET quantity = quantity + 1 WHERE userId = ? AND productId = ?";
        try (Connection conn = new DbContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Giảm số lượng sản phẩm trong giỏ hàng
    public void decreaseProductQuantity(int userId, int productId) throws SQLException {
        String sql = "UPDATE Cart SET quantity = quantity - 1 WHERE userId = ? AND productId = ? AND quantity > 1";
        try (Connection conn = new DbContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearCart(int userId) {
        String sql = "DELETE FROM Cart WHERE userId = ?";
        try (Connection conn = new DbContext().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws SQLException {
        CartDao cartDao = new CartDao();

        int userId = 12; // ID người dùng
        int productId = 7  ; // ID sản phẩm
        int initialQuantity = 1; // Số lượng ban đầu
        String sessionId = "session123"; // ID phiên

        // Thêm sản phẩm vào giỏ hàng với số lượng ban đầu
        cartDao.addProduct(userId, productId, initialQuantity, sessionId);
        System.out.println("First addProduct call: Product added to cart with quantity = " + initialQuantity);

        // Hiển thị giỏ hàng sau lần thêm đầu tiên
        List<CartItem> cartItems = cartDao.getCart(userId);
        System.out.println("Cart items after first addProduct:");
        for (CartItem item : cartItems) {
            System.out.println("Product ID: " + item.getProduct().getProductId() +
                    ", Name: " + item.getProduct().getName() +
                    ", Quantity: " + item.getQuantity());
        }

        // Hiển thị giỏ hàng sau lần thêm thứ hai
        cartItems = cartDao.getCart(userId);
        System.out.println("Cart items after second addProduct:");
        for (CartItem item : cartItems) {
            System.out.println("Product ID: " + item.getProduct().getProductId() +
                    ", Name: " + item.getProduct().getName() +
                    ", Quantity: " + item.getQuantity());
        }


    }
}

