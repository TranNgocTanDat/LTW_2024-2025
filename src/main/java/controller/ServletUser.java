
package controller;

import dao.ProductDao;
import dao.UserDao;
import model.Product;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ServletUser", value = "/admin/users")
public class ServletUser extends HttpServlet {
    private UserDao userDao;

    public void init() {
        userDao = new UserDao();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "edit":
                showEditUserForm(request, response);
                break;
            case "delete":
                deleteUser(request, response);
                break;
            default:
                listUsers(request, response);
                break;
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userDao.getAll();
        request.setAttribute("users", users);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/userManager.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditUserForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdString = request.getParameter("userId");
        int userId = Integer.parseInt(userIdString);
        User existingUser = userDao.getUserById(userId);
        request.setAttribute("users", existingUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/user-form.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        userDao.delelteUser(userId);
        response.sendRedirect(request.getContextPath() + "/admin/users"); // Redirect đúng đường dẫn
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("update".equals(action)) {
            try {
                updateUser(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException {
        int userId = Integer.parseInt(request.getParameter("id"));
        String role = request.getParameter("role");

        // Chỉ cập nhật role
        userDao.updateUserRole(userId, role);

        response.sendRedirect(request.getContextPath() + "/admin/users");
    }

}
