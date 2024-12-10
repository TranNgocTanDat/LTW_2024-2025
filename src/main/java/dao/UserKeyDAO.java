package dao;

import context.DbContext;
import model.UserKey;

import java.sql.*;

public class UserKeyDAO {
    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;

    public UserKey getKeyByUserId(int userId){
        String sql = "Select * from user_keys where userId =?";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while(rs.next()){
                return new UserKey(
                        rs.getInt("keyId"),
                        rs.getInt("userId"),
                        rs.getString("publicKey"),
                        rs.getString("privateKey"),
                        rs.getString("keyType"),
                        rs.getDate("creationDate")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void saveKey(UserKey userKey){
        String sql = "Insert into user_keys(userId, publicKey, privateKey, keyType, creationDate) values (?,?,?,?,?)";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1,userKey.getUserId());
            ps.setString(2, userKey.getPublicKey());
            ps.setString(3, userKey.getPrivateKey());
            ps.setString(4, userKey.getKeyType());
            // Chuyển đổi java.util.Date sang java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(userKey.getCreationDate().getTime());
            ps.setDate(5, sqlDate);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
