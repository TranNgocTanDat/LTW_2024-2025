package dao;

import context.DbContext;
import model.Order;
import model.OrderItem;

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
                order.setStatus(rs.getString("status"));

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
                order.setStatus(rs.getString("status"));
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

    public int createOrder(int userId, String shippingAddress) {
        String query = "INSERT INTO Orders (userId, shippingAddress, totalAmount) OUTPUT INSERTED.orderId VALUES (?, ?, 0)";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setString(2, shippingAddress);

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

    public void addOrderItem(int orderId, int productId, int quantity, double price){
        String query = "INSERT INTO OrderItem (orderId, productId, quantity, price) VALUES (?, ?, ?, ?)";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.setDouble(4, price);
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
                        rs.getFloat("price")
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

    public static void main(String[] args) {
        OrderDao orderDao = new OrderDao();

        // Tạo đơn hàng mới
//        int orderId = orderDao.createOrder(1, "123 Main St");
//        System.out.println("Order ID: " + orderId);
//
//        // Thêm sản phẩm vào đơn hàng
//        orderDao.addOrderItem(orderId, 1, 2, 19.99);
//        orderDao.addOrderItem(orderId, 3, 1, 9.99);

        // Lấy danh sách đơn hàng
        List<Order> orders = orderDao.getAllOrders();
        for (Order order : orders) {
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("User ID: " + order.getUserId());
            System.out.println("Order Date: " + order.getOrderDate());
            System.out.println("Total Amount: " + order.getTotalAmount());
            System.out.println("Shipping Address: " + order.getShippingAddress());
            System.out.println("status:" +  order.getStatus());
            System.out.println("-----------------------------");
        }
//        System.out.println("sss");
////         Lấy chi tiết đơn hàng
//        List<OrderItem> orderItems = orderDao.getOrderDetails(orderId);
//        for (OrderItem item : orderItems) {
//            System.out.println("Order Item ID: " + item.getOrderItemId());
//            System.out.println("Order ID: " + item.getOrderId());
//            System.out.println("Product ID: " + item.getProductId());
//            System.out.println("Quantity: " + item.getQuantity());
//            System.out.println("Price: " + item.getPrice());
//            System.out.println("-----------------------------");
//        }
    }


}
