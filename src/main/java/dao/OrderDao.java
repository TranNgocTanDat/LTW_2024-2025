package dao;

import context.DbContext;
import model.Order;

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

                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    public int createOrder(int userId, String shippingAdress){
        String query = "INSERT INTO Orders (userId, shippingAddress, totalAmount, status) OUTPUT INSERTED.orderId VALUES (?, ?, 0, 'Pending')";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setString(2, shippingAdress);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("orderId");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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
}
