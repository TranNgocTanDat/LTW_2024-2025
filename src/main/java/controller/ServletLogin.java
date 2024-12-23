package controller;

import dao.UserDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ServletLogin", value = "/login")
public class ServletLogin extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ServletLogin.class.getName());
    private UserDao userDao;

    @Override
    public void init() {
        userDao = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect("profile.jsp"); // Redirect if the user is already logged in
        } else {
            request.getRequestDispatcher("login.jsp").forward(request, response); // Display the login page
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username);
        System.out.println(password);;
        try {
            // Hash the password entered by the user
            String hashedPassword = hashPassword(password);
            System.out.println(hashedPassword);
            // Authenticate the user
            User user = userDao.authenticateUser(username, hashedPassword);

            if (user != null) {
                // Store user details in the session
                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("user", user);
                session.setAttribute("role", user.getRole());

                // Redirect based on user role
                if ("admin".equals(user.getRole())) {
                    response.sendRedirect("admin.jsp"); // Redirect to the admin dashboard
                } else {
                    response.sendRedirect("index.jsp"); // Redirect to the main page for regular users
                }
            } else {
                // If authentication fails, show an error
                request.setAttribute("errorMessage", "Invalid username or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during login", e);
            request.setAttribute("errorMessage", "An error occurred. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    // Method to hash the password using MD5
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(Integer.toHexString(0xFF & b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "Error hashing password", e);
            return null; // Handle null appropriately in the application
        }
    }
}
