package controller;

import dao.UserDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "RegisterServlet", value = "/register")
public class ServletRegister extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ServletRegister.class.getName());
    private UserDao userDao;

    @Override
    public void init() {
        userDao = new UserDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String address = request.getParameter("address");
        String phoneNumber = request.getParameter("phoneNumber");
        String role = "user"; // Default role

        // Hash the password using MD5
        String hashedPassword = hashPassword(password);

        if (hashedPassword == null) {
            request.setAttribute("errorMessage", "An error occurred while hashing the password. Please try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Kiểm tra xem có tồn tại ID hay không
        String userIdStr = request.getParameter("id");
        int userId = (userIdStr != null && !userIdStr.isEmpty()) ? Integer.parseInt(userIdStr) : 0;

        // Kiểm tra và đăng ký người dùng
        User newUser = new User(userId, username, hashedPassword, email,firstName, lastName, address, phoneNumber, role, null); // Mặc định là user
        userDao.insertUser(newUser);
        response.sendRedirect(request.getContextPath() + "/login"); // Chuyển hướng đến trang đăng nhập
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    // Hash the password using MD5
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "Error hashing password", e);
            return null;
        }
    }
}