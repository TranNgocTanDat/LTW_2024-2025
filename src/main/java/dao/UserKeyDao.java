package dao;

import context.DbContext;
import model.UserKey;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class UserKeyDao {
    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;

    public List<UserKey> getAllKeys() throws SQLException {
        List<UserKey> userKeys = new ArrayList<>();
        String sql = "SELECT * FROM user_keys";
        try  {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                UserKey userKey = new UserKey(
                        rs.getInt("keyId"),
                        rs.getInt("userId"),
                        rs.getString("publicKey"),
                        rs.getString("privateKey"),
                        rs.getString("keyType"),
                        rs.getDate("creationDate")
                );
                userKeys.add(userKey);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return userKeys;
    }

    public void deleteKey(int keyId) throws SQLException {
        String sql = "DELETE FROM user_keys WHERE keyId = ?";
        try  {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, keyId);
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public PublicKey getPublicKeyById(int userId){
        String sql = "Select publicKey from user_keys where userId =?";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()){
                String publicKeyBase64 = rs.getString("publicKey");
                byte[] decoded = Base64.getDecoder().decode(publicKeyBase64);
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                return keyFactory.generatePublic(keySpec);
            }
        } catch (SQLException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

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

    public static void main(String[] args) throws SQLException {
        UserKeyDao userKeyDao = new UserKeyDao();
        userKeyDao.deleteKey(1);
    }
}
