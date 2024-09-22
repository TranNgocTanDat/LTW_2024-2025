package controller;

import dao.UserDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "ServletLogin", value = "/login")
public class ServletLogin extends HttpServlet {
    private UserDao userDao;

    public void init(){
        userDao = new UserDao();
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
                    response.sendRedirect("admin-dashboard.jsp");
                } else {
                    response.sendRedirect("user-dashboard.jsp");
                }
            } else {
                request.setAttribute("errorMessage", "Invaild username or password");
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
