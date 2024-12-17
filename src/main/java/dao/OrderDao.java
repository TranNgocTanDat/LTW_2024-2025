package dao;

import context.DbContext;
import model.Order;
import model.OrderItem;

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
    public OrderDao(){

    }

    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    //Lấy danh sách đơn hàng
    public List<Order> getAllOrders(){
        String query = "SELECT * FROM Orders";
        List<Order> orders = new ArrayList<>();
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
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

    public int createOrder(int userId, String shippingAddress, String recipientName, String shippingPhoneNumber, String paymentMethod, String notes, String order_content) {
        String query = "INSERT INTO Orders (userId, shippingAddress, totalAmount, recipientName, shippingPhoneNumber, paymentMethod, notes, order_content) OUTPUT INSERTED.orderId VALUES (?, ?, 0, ?,?,?,?, ?)";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setString(2, shippingAddress);
            ps.setString(3, recipientName);
            ps.setString(4, shippingPhoneNumber);
            ps.setString(5, paymentMethod);
            ps.setString(6, notes);
            ps.setString(7, order_content);

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("orderId");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Lỗi khi tạo đơn hàng", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public void addOrderItem(int orderId, int productId, int quantity, float price, float discount){
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
        String query = "SELECT * FROM OrderItem WHERE orderId = ?";
        List<OrderItem> orderItems = new ArrayList<>();
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            while (rs.next()) {
                OrderItem item = new OrderItem(
                        rs.getInt("OrderItemId"),
                        rs.getInt("orderId"),
                        rs.getInt("productId"),
                        rs.getInt("quantity"),
                        rs.getFloat("price"),
                        rs.getFloat("discount"),
                        rs.getFloat("totalPrice")
                );
                orderItems.add(item);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
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

    public void updateOrderItem(OrderItem orderItem) {
        String query = "UPDATE OrderItem SET productId = ?, quantity = ?, price = ? WHERE orderItemId = ?";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, orderItem.getProductId());
            ps.setInt(2, orderItem.getQuantity());
            ps.setDouble(3, orderItem.getPrice());
            ps.setInt(4, orderItem.getOrderItemId());
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
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

    public boolean updateOrder(Order order) {
        String sql = "UPDATE Orders SET status = ? WHERE orderId = ?";
        try  {
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


    public String getOrderContentById(int orderId){
        String sql = "SELECT order_content FROM Orders WHERE orderId = ?";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            if (rs.next()){
                return rs.getString("order_content");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean saveSignature(int orderId, String signature){
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

        // Lấy danh sách đơn hàng
        List<Order> orders = orderDao.getAllOrders();
        for (Order order : orders) {
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("User ID: " + order.getUserId());
            System.out.println("Order Date: " + order.getOrderDate());
            System.out.println("Total Amount: " + order.getTotalAmount());
            System.out.println("Shipping Address: " + order.getShippingAddress());
            System.out.println("Recipient Name: " + order.getRecipientName());
            System.out.println("Shipping Phone: " + order.getShippingPhoneNumber());
            System.out.println("Payment Method: " + order.getPaymentMethod());
            System.out.println("Payment Status: " + order.getPaymentStatus());
            System.out.println("Delivery Date: " + order.getDeliveryDate());
            System.out.println("Notes: " + order.getNotes());
            System.out.println("Order Status: " + order.getStatus());
            System.out.println("Order Content: " + order.getOrder_content());
            System.out.println("-----------------------------");
        }

        // Lấy chi tiết đơn hàng
        List<OrderItem> orderItems = orderDao.getOrderDetails(1);
        for (OrderItem item : orderItems) {
            System.out.println("Order Item ID: " + item.getOrderItemId());
            System.out.println("Order ID: " + item.getOrderId());
            System.out.println("Product ID: " + item.getProductId());
            System.out.println("Quantity: " + item.getQuantity());
            System.out.println("Price: " + item.getPrice());
            System.out.println("Discount: " + item.getDiscount());
            System.out.println("Total Price: " + item.getTotalPrice());
            System.out.println("-----------------------------");
        }
    }


}
