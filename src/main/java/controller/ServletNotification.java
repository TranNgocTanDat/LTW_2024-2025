package controller;

import dao.UserDao;
import model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ServletNotification", value = "/ServletNotification")
public class ServletNotification extends HttpServlet {
    private UserDao userDao = new UserDao();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(); // Lấy session
        Integer userId = (Integer) session.getAttribute("userId"); // Lấy userId từ session

        if (userId == null) {
            response.sendRedirect("login.jsp"); // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
            return;
        }
        User user = userDao.getUserById(userId);
        System.out.println(user);
        request.setAttribute("user", user);
        request.getRequestDispatcher("notification.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
