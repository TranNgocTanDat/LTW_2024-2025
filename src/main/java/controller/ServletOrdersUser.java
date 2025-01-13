package controller;

import dao.OrderDao;
import model.Order;
import model.OrderItem;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletOrdersUser", value = "/ordersUser")
public class ServletOrdersUser extends HttpServlet {
    private OrderDao orderDao;

    @Override
    public void init() throws ServletException {
        orderDao = new OrderDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId"); // Lấy userId từ session
//        String orderIdIdParam = request.getParameter("orderId");
//        int orderId = Integer.parseInt(orderIdIdParam);

        if (userId == null) {
            response.sendRedirect("login.jsp"); // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
            return;
        }

        OrderDao orderDAO = new OrderDao();
        List<Order> orders = orderDAO.getOrdersByUserId(userId);
//        List<OrderItem> orderItems = orderDao.getOrderDetails(orderId);
        request.setAttribute("orders", orders);
//        request.setAttribute("orderItems", orderItems);
        request.getRequestDispatcher("ordersUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
