package dao;

import context.DbContext;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public UserDao(){}
    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // phương thức xác thực người dùng
    public String authenticateUser(String username, String password){
        String query ="SELECT role FROM Users WHERE username=? AND password=?";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getString("role");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // get user from username
    public User getUserByUsername(String username){
        String query ="SELECT * FROM Users WHERE username=?";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()){
                User user = new User();
                user.setUserId(rs.getInt("userID"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setFirsrtName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setAddress(rs.getString("address"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setRole(rs.getString("role"));
                return user;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void insertUser(User user) {
        String query = "INSERT INTO users (username, password, email, firstName, lastName, address, phoneNumber) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getFirsrtName());
            ps.setString(5, user.getLastName());
            ps.setString(6, user.getAddress());
            ps.setString(7, user.getPhoneNumber());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public List<User> getAll(){
        List<User> list = new ArrayList<>();
        String query ="select*from users";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                list.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void delelteUser(int userId){
        String query = "DELETE  FROM Users WHERE userID=?";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        String text = userDao.authenticateUser("td", "securepassword123");
        System.out.println(text);
        List<User> users = userDao.getAll();
        for (User user: users) {
            System.out.println(user);
        }

    }
}
