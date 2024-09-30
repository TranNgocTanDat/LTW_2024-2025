package controller;

import dao.UserDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ServletLogin", value = "/login")  // Cập nhật đường dẫn
public class ServletLogin extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ServletLogin.class.getName());
    private UserDao userDao;

    @Override
    public void init() {
        try {
            userDao = new UserDao();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to initialize UserDao", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            String role = userDao.authenticateUser(username, password);
            if (role != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", username);
                session.setAttribute("role", role);

                if ("admin".equals(role)) {
                    response.sendRedirect("admin.jsp");
                } else {
                    response.sendRedirect("index.jsp");
                }
            } else {
                request.setAttribute("errorMessage", "Invalid username or password");  // Sửa lỗi chính tả
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during authentication", e);
            request.setAttribute("errorMessage", "An error occurred during login. Please try again.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
        }
    }
}