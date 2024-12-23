package controller;

import dao.ReportDao;
import dao.UserKeyDao;
import model.Report;
import model.UserKey;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ServletUserKeyManagement", value = "/admin/key-management")
public class ServletUserKeyManagement extends HttpServlet {
    private UserKeyDao userKeyDao;
    private ReportDao reportDao;
    public void init() {
        reportDao = new ReportDao();
        userKeyDao = new UserKeyDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        List<Report> listReports = reportDao.getAllReport();
        if(listReports!=null){
            for (Report report : listReports){
                if(report.getContent_report()=="exposed"){
                    int userId = report.getUserID();
                    try {
                        userKeyDao.deleteKey(userId);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        if (action == null || action.isEmpty()) {
            action = "list";
        }

        try {
            switch (action) {
                case "list":
                    listKeys(request, response);
                    break;
                case "delete":
                    deleteKey(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/admin/key-management");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request.");
        }
    }

    private void listKeys(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<UserKey> userKeys = userKeyDao.getAllKeys();
        request.setAttribute("userKeys", userKeys);
        request.getRequestDispatcher("/admin/keyManagement.jsp").forward(request, response);
    }

    private void deleteKey(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int keyId = Integer.parseInt(request.getParameter("keyId"));
            userKeyDao.deleteKey(keyId);
            response.sendRedirect(request.getContextPath() + "/admin/key-management?action=list&message=Key deleted successfully.");
        } catch (NumberFormatException | SQLException e) {
            response.sendRedirect(request.getContextPath() + "/admin/key-management?action=list&error=Invalid key ID.");
        }
    }
}
