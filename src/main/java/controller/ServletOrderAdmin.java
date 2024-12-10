package controller;

import dao.OrderDao;
import model.Order;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ServletOrderAdmin", value = "/admin/orders")
public class ServletOrderAdmin extends HttpServlet {
    private OrderDao orderDao;

    public void init(){
        orderDao = new OrderDao();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> orders;
        orders = orderDao.getAllOrders();

        //Gửi dữ liệu đến jsp
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("orders.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
