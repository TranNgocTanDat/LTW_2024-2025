package controller;

import dao.OrderDao;
import model.Order;
import model.OrderItem;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletOrderManagement", value = "/admin/orders-management")
public class ServletOrderManagement extends HttpServlet {
    private OrderDao orderDao;

    public void init() {
        orderDao = new OrderDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            action = "list";
        }

        try {
            switch (action) {
                case "list":
                    listOrders(request, response);
                    break;
                case "details":
                    showOrderDetails(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() +"/admin/orders-management");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "approve":
                case "cancel":
                    updateOrderStatus(request, response, action);
                    break;
                case "delete":
                    deleteOrder(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() +"/admin/orders-management");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request.");
        }
    }

    private void listOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> orders = orderDao.getAllOrders();
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/admin/orders.jsp").forward(request, response);
    }

    private void showOrderDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            List<OrderItem> orderItems = orderDao.getOrderDetails(orderId);
            request.setAttribute("orderItems", orderItems);
            request.getRequestDispatcher("/admin/orderDetails.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order ID.");
        }
    }

    private void updateOrderStatus(HttpServletRequest request, HttpServletResponse response, String action) throws IOException {
        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            Order order = orderDao.getOrderById(orderId);

            if (order != null && "Pending".equals(order.getStatus())) {
                String newStatus = "approve".equals(action) ? "Shipped" : "Canceled";
                order.setStatus(newStatus);
                orderDao.updateOrderStatus(order);
                response.sendRedirect(request.getContextPath() +"/admin/orders-management?action=list");
            } else {
                response.sendRedirect(request.getContextPath() +"/admin/orders-management?action=list&error=Invalid order status.");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() +"/admin/orders-management?action=list&error=Invalid order ID.");
        }
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            orderDao.deleteOrder(orderId);
            System.out.println("Redirecting to: " + request.getContextPath() + "/admin/orders-management?action=list&message=Order deleted successfully.");
            response.sendRedirect("http://localhost:8080/demoServlet_war_exploded/admin/orders-management?action=list&message=Order deleted successfully.");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/orders-management?action=list&error=Invalid order ID.");
        }
    }

}