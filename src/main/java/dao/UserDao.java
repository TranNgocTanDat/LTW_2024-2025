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
    public User authenticateUser(String username, String password){
        String query = "SELECT * FROM Users WHERE username=? AND password=?";
        User user = null; // Khởi tạo đối tượng User

        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Lấy thông tin từ kết quả truy vấn và khởi tạo đối tượng User
                int userId = rs.getInt("userId");
                String email = rs.getString("email");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String address = rs.getString("address");
                String phoneNumber = rs.getString("phoneNumber");
                String role = rs.getString("role");

                user = new User(userId, username, password, email, firstName, lastName, address, phoneNumber, role);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            // Đóng kết nối, PreparedStatement, và ResultSet nếu cần thiết
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    // get user by userId
    public User getUserById(int userId){
        String query ="SELECT * FROM Users WHERE userId=?";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()){
                User user = new User();
                user.setUserId(rs.getInt("userId"));
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
            ps.setString(4, user.getFirstName());
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
            rs = ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //update user
    public void updateUser(User user) {
        String query = "UPDATE Users SET username=?, password=?, email=?, firstName=?, lastName=?, address=?, phoneNumber=?, role=? WHERE userId=?";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getFirstName());
            ps.setString(5, user.getLastName());
            ps.setString(6, user.getAddress());
            ps.setString(7, user.getPhoneNumber());
            ps.setString(8, user.getRole());
            ps.setInt(9, user.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            // Đóng các đối tượng để tránh rò rỉ tài nguyên
            try {
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkMail(String mail){
        String sql = "SELECT * FROM Users WHERE email=?";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, mail);
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public boolean checkPhone(String phone){
        String sql = "SELECT * FROM Users WHERE phoneNumber=?";
        try {
            connection = new DbContext().getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, phone);
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        List<User> users = userDao.getAll();
        for (User user: users) {
            System.out.println(user);
        }

    }
}
