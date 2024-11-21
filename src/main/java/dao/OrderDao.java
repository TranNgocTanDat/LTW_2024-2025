package dao;

import context.DbContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDao {
    public OrderDao(){

    }

    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public int createOrder(int userId, String shippingAdress){
        String query = "INSERT INTO Orders (userId, shippingAddress, totalAmount, status) OUTPUT INSERTED.orderId VALUES (?, ?, 0, 'Pending')";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setString(2, shippingAdress);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
