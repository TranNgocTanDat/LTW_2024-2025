package controller;

import dao.UserDao;
import model.User; // Đảm bảo bạn đã tạo lớp User

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ServletLogin", value = "/login")
public class ServletLogin extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ServletLogin.class.getName());
    private UserDao userDao;

    @Override
    public void init() {
        try {
            userDao = new UserDao();  // Khởi tạo DAO để kiểm tra thông tin đăng nhập
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to initialize UserDao", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            // Nếu đã đăng nhập rồi, chuyển hướng đến trang chính hoặc hồ sơ người dùng
            response.sendRedirect("profile.jsp"); // Chuyển hướng đến trang profile
        } else {
            // Nếu chưa đăng nhập, hiển thị trang login
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Kiểm tra thông tin đăng nhập
            User user = userDao.authenticateUser(username, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("user", user);  // Lưu thông tin người dùng vào session
                session.setAttribute("role", user.getRole());  // Lưu vai trò người dùng vào session

                if ("admin".equals(user.getRole())) {
                    // Nếu là admin, lưu token bảo mật trong session và chuyển hướng đến trang admin
//                    String token = java.util.UUID.randomUUID().toString();
//                    session.setAttribute("adminToken", token);
                    response.sendRedirect("admin");  // Chuyển hướng đến trang admin
                } else {
                    // Nếu không phải admin, chuyển hướng đến trang người dùng chính
                    response.sendRedirect("index.jsp");
                }
            } else {
                request.setAttribute("errorMessage", "Invalid username or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);  // Hiển thị lại trang login với thông báo lỗi
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during authentication", e);
            request.setAttribute("errorMessage", "An error occurred during login. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
