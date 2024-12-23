package dao;

import context.DbContext;
import model.Order;
import model.OrderItem;
import model.Product;

import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    public OrderDao() {

    }

    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    //Lấy danh sách đơn hàng
    public List<Order> getAllOrders() {
        String query = "SELECT * FROM Orders";
        List<Order> orders = new ArrayList<>();
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("orderId"));
                order.setUserId(rs.getInt("userId"));
                order.setOrderDate(rs.getDate("orderDate"));
                order.setTotalAmount(rs.getFloat("totalAmount"));
                order.setShippingAddress(rs.getString("shippingAddress"));
                order.setRecipientName(rs.getString("recipientName"));
                order.setShippingPhoneNumber(rs.getString("shippingPhoneNumber"));
                order.setPaymentMethod(rs.getString("paymentMethod"));
                order.setPaymentStatus(rs.getString("paymentStatus"));
                order.setDeliveryDate(rs.getDate("deliveryDate"));
                order.setIs_edited(rs.getBoolean("is_edited"));
                order.setNotes(rs.getString("notes"));
                order.setStatus(rs.getString("status"));
                order.setOrder_content(rs.getString("order_content"));

                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    public Order getOrderById(int orderId) {
        String query = "SELECT * FROM Orders WHERE orderId = ?";
        Order order = null;
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            if (rs.next()) {
                order = new Order();
                order.setOrderId(rs.getInt("orderId"));
                order.setUserId(rs.getInt("userId"));
                order.setOrderDate(rs.getDate("orderDate"));
                order.setTotalAmount(rs.getFloat("totalAmount"));
                order.setShippingAddress(rs.getString("shippingAddress"));
                order.setRecipientName(rs.getString("recipientName"));
                order.setShippingPhoneNumber(rs.getString("shippingPhoneNumber"));
                order.setPaymentMethod(rs.getString("paymentMethod"));
                order.setPaymentStatus(rs.getString("paymentStatus"));
                order.setDeliveryDate(rs.getDate("deliveryDate"));
                order.setIs_edited(rs.getBoolean("is_edited"));
                order.setNotes(rs.getString("notes"));
                order.setStatus(rs.getString("status"));
                order.setOrder_content(rs.getString("order_content"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return order;
    }

    public List<Order> getOrdersByUserId(int userId) {
        String query = "SELECT * FROM Orders WHERE userId = ?";
        List<Order> orders = new ArrayList<>();
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("orderId"));
                order.setUserId(rs.getInt("userId"));
                order.setOrderDate(rs.getDate("orderDate"));
                order.setTotalAmount(rs.getFloat("totalAmount"));
                order.setShippingAddress(rs.getString("shippingAddress"));
                order.setRecipientName(rs.getString("recipientName"));
                order.setShippingPhoneNumber(rs.getString("shippingPhoneNumber"));
                order.setPaymentMethod(rs.getString("paymentMethod"));
                order.setPaymentStatus(rs.getString("paymentStatus"));
                order.setDeliveryDate(rs.getDate("deliveryDate"));
                order.setIs_edited(rs.getBoolean("is_edited"));
                order.setNotes(rs.getString("notes"));
                order.setStatus(rs.getString("status"));
                order.setOrder_content(rs.getString("order_content"));

                // Thêm order vào danh sách
                orders.add(order);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return orders;
    }


    public int createOrder(int userId, String shippingAddress, String recipientName, String shippingPhoneNumber, String paymentMethod, float totalAmount, String notes, String order_content) {
        String query = "INSERT INTO Orders (userId, shippingAddress, totalAmount, recipientName, shippingPhoneNumber, paymentMethod, notes, order_content) OUTPUT INSERTED.orderId VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            // Mở kết nối
            connection = new DbContext().getConnection();

            // Tạo PreparedStatement
            ps = connection.prepareStatement(query);

            // Thiết lập các tham số
            ps.setInt(1, userId);                     // userId
            ps.setString(2, shippingAddress);          // shippingAddress
            ps.setFloat(3, totalAmount);              // totalAmount
            ps.setString(4, recipientName);           // recipientName
            ps.setString(5, shippingPhoneNumber);     // shippingPhoneNumber
            ps.setString(6, paymentMethod);           // paymentMethod
            ps.setString(7, notes);                   // notes
            ps.setString(8, order_content);           // order_content

            // Thực thi truy vấn
            rs = ps.executeQuery();

            // Lấy orderId
            if (rs.next()) {
                return rs.getInt("orderId");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Lỗi khi tạo đơn hàng", e);  // Ném lại ngoại lệ nếu có lỗi
        } finally {
            try {
                // Đảm bảo đóng các tài nguyên sau khi sử dụng
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();  // In ra lỗi nếu không đóng được tài nguyên
            }
        }
        return 0;  // Trả về 0 nếu không thành công
    }


    public void addOrderItem(int orderId, int productId, int quantity, float price, float discount) {
        String sql = "INSERT INTO OrderItem (orderId, productId, quantity, price, discount) VALUES (?, ?, ?, ?, ?)";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.setDouble(4, price);
            ps.setFloat(5, discount);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<OrderItem> getOrderDetails(int orderId) {
        String query = " SELECT oi.OrderItemId, oi.orderId, oi.quantity, oi.price, oi.discount, oi.totalPrice, p.productId, p.name, p.price, p.imageUrl FROM OrderItem oi JOIN Product p ON oi.productId = p.productId WHERE oi.orderId = ?";
        List<OrderItem> orderItems = new ArrayList<>();
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            while (rs.next()) {
                // Tạo đối tượng Product
                Product product = new Product();
                product.setProductId(rs.getInt("productId"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setImageUrl(rs.getString("imageUrl"));

                // Tạo đối tượng OrderItem
                OrderItem item = new OrderItem();
                item.setOrderItemId(rs.getInt("OrderItemId"));
                item.setOrderId(rs.getInt("orderId"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getFloat("price"));
                item.setDiscount(rs.getFloat("discount"));
                item.setTotalPrice(rs.getFloat("totalPrice"));

                item.setProduct(product); // Liên kết Product với OrderItem

                orderItems.add(item);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            // Đóng tài nguyên
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return orderItems;
    }

    public void deleteOrder(int orderId) {
        String deleteOrderItemsQuery = "DELETE FROM OrderItem WHERE orderId = ?";
        String deleteOrderQuery = "DELETE FROM Orders WHERE orderId = ?";
        try {
            connection = new DbContext().getConnection();
            connection.setAutoCommit(false); // Bắt đầu transaction

            // Xóa các mục đơn hàng trước
            ps = connection.prepareStatement(deleteOrderItemsQuery);
            ps.setInt(1, orderId);
            ps.executeUpdate();

            // Xóa đơn hàng
            ps = connection.prepareStatement(deleteOrderQuery);
            ps.setInt(1, orderId);
            ps.executeUpdate();

            connection.commit(); // Hoàn thành transaction
        } catch (SQLException | ClassNotFoundException e) {
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback nếu có lỗi
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean updateOrderWithItems(Order order, List<OrderItem> orderItems) {
        String updateOrderQuery = "UPDATE Orders SET " +
                "shippingAddress = ?, recipientName = ?, shippingPhoneNumber = ?, status = ?, is_edited = ?, totalAmount = ?, order_content = ? " +
                "WHERE orderId = ?";
        String updateOrderItemQuery = "UPDATE OrderItem SET quantity = ?, price = ? WHERE orderId = ? AND productId = ?";
        String deleteOrderItemQuery = "DELETE FROM OrderItem WHERE orderId = ? AND productId = ?";
        String insertOrderItemQuery = "INSERT INTO OrderItem (orderId, productId, quantity, price) VALUES (?, ?, ?, ?)";

        boolean success = false;

        try {
            connection = new DbContext().getConnection();
            connection.setAutoCommit(false); // Bắt đầu transaction

            // 1. Cập nhật bảng Orders
            ps = connection.prepareStatement(updateOrderQuery);
            ps.setString(1, order.getShippingAddress());
            ps.setString(2, order.getRecipientName());
            ps.setString(3, order.getShippingAddress());
            ps.setString(4, order.getStatus());
            ps.setBoolean(5, true);
            ps.setFloat(6, order.getTotalAmount());
            ps.setString(7, order.getOrder_content());
            ps.setInt(8, order.getOrderId());
            ps.executeUpdate();

            // 2. Cập nhật hoặc thêm mới các sản phẩm trong OrderItem
            for (OrderItem item : orderItems) {
                if (item.getQuantity() == 0) {
                    // Nếu số lượng = 0 thì xóa sản phẩm khỏi OrderItem
                    ps = connection.prepareStatement(deleteOrderItemQuery);
                    ps.setInt(1, order.getOrderId());
                    ps.setInt(2, item.getProduct().getProductId());
                    ps.executeUpdate();
                } else {
                    // Kiểm tra xem sản phẩm đã tồn tại chưa
                    String checkItemQuery = "SELECT COUNT(*) FROM OrderItem WHERE orderId = ? AND productId = ?";
                    ps = connection.prepareStatement(checkItemQuery);
                    ps.setInt(1, order.getOrderId());
                    ps.setInt(2, item.getProduct().getProductId());
                    rs = ps.executeQuery();
                    if (rs.next() && rs.getInt(1) > 0) {
                        // Nếu đã tồn tại, cập nhật
                        ps = connection.prepareStatement(updateOrderItemQuery);
                        ps.setInt(1, item.getQuantity());
                        ps.setFloat(2, item.getPrice());
                        ps.setInt(3, order.getOrderId());
                        ps.setInt(4, item.getProduct().getProductId());
                        ps.executeUpdate();
                    } else {
                        // Nếu chưa tồn tại, thêm mới
                        ps = connection.prepareStatement(insertOrderItemQuery);
                        ps.setInt(1, order.getOrderId());
                        ps.setInt(2, item.getProduct().getProductId());
                        ps.setInt(3, item.getQuantity());
                        ps.setFloat(4, item.getPrice());
                        ps.executeUpdate();
                    }
                }
            }
            connection.commit(); // Commit transaction nếu không có lỗi
            success = true;
        } catch (SQLException | ClassNotFoundException e) {
            try {
                connection.rollback(); // Rollback nếu xảy ra lỗi
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public boolean updateOrderStatus(Order order) {
        String sql = "UPDATE Orders SET status = ? WHERE orderId = ?";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, order.getStatus());
            ps.setInt(2, order.getOrderId());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    public String getOrderContentById(int orderId) {
        String sql = "SELECT order_content FROM Orders WHERE orderId = ?";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("order_content");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public boolean verifySignature(int orderId, String signature) {
        boolean isValid = false;
        String query = "SELECT signature FROM Orders WHERE orderId = ?";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            if (rs.next()) {
                String savedSignature = rs.getString("signature");
                isValid = savedSignature.equals(signature); // So sánh chữ ký
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return isValid;
    }
    public boolean saveSignature(int orderId, String signature) {
        String sql = "UPDATE Orders SET signature = ? WHERE orderId = ?";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, signature);
            ps.setInt(2, orderId);

            // Kiểm tra số bản ghi được cập nhật
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        OrderDao orderDao = new OrderDao();
        System.out.println(orderDao.verifySignature(77, "yaYrvxM+TprXgpwH9hs7DChaYAslAU685ZVCvpoIFVg9CbeaVMKiYMWPOfawRRXIbnyvOyFoJFmQM6xIhp8n3YAila/8AhVg/cVeTtP8PHgcoThZV4ahn09rolvy6ihhKgiDoOZUzJCnp37FbmQoh1enMSqpymeJ3fQzJMHJHYJadW0MFRzFEMeAMhpKXwtrcj4GG4kMEFdpFS/cQFYDMIMQdXHxQGDINO/Se5Y1flqnUuYNVFujcfvNIa75KFk5KRGev1qIRZoj1g9Uxf0o1FhKzRPYnU8H0ocAQ6b8G6WihwnI6qHhF6IiCVcnOIOdXsnohRGY/GnE9tRdfATJHw=="));

        // Lấy danh sách đơn hàng
//        List<Order> orders = orderDao.getOrdersByUserId(12);
//        for (Order order : orders) {
//            System.out.println("Order ID: " + order.getOrderId());
//            System.out.println("User ID: " + order.getUserId());
//            System.out.println("Order Date: " + order.getOrderDate());
//            System.out.println("Total Amount: " + order.getTotalAmount());
//            System.out.println("Shipping Address: " + order.getShippingAddress());
//            System.out.println("Recipient Name: " + order.getRecipientName());
//            System.out.println("Shipping Phone: " + order.getShippingPhoneNumber());
//            System.out.println("Payment Method: " + order.getPaymentMethod());
//            System.out.println("Payment Status: " + order.getPaymentStatus());
//            System.out.println("Delivery Date: " + order.getDeliveryDate());
//            System.out.println("Notes: " + order.getNotes());
//            System.out.println("Order Status: " + order.getStatus());
//            System.out.println("Order Content: " + order.getOrder_content());
//            System.out.println("-----------------------------");
//        }
      
        // Lấy chi tiết đơn hàng
        List<OrderItem> orderItems = orderDao.getOrderDetails(1);
        for (OrderItem item : orderItems) {
            System.out.println("Order Item ID: " + item.getOrderItemId());
            System.out.println("Order ID: " + item.getOrderId());
            System.out.println("Product ID: " + item.getProduct());
            System.out.println("Quantity: " + item.getQuantity());
            System.out.println("Price: " + item.getPrice());
            System.out.println("Discount: " + item.getDiscount());
            System.out.println("Total Price: " + item.getTotalPrice());
            System.out.println("-----------------------------");
        }
    }


}
