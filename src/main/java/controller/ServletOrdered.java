package controller;

import dao.OrderDao;
import model.Order;
import model.OrderItem;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletOrdered", value = "/ordered")
public class ServletOrdered extends HttpServlet {
    private OrderDao orderDao;

    @Override
    public void init() throws ServletException {
        orderDao = new OrderDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId"); // Lấy userId từ session
        Integer orderId = (Integer) session.getAttribute("orderId");

        if (userId == null) {
            response.sendRedirect("login.jsp"); // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
            return;
        }

        Order ordered = orderDao.getOrderById(orderId);
        List<OrderItem> orderItems = orderDao.getOrderDetails(orderId);
        request.setAttribute("ordered", ordered);
        request.setAttribute("orderItems", orderItems);
        request.getRequestDispatcher("/ordered.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
