package controller;

import dao.ReportDao;
import dao.UserDao;
import model.Report;
import model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ServletReportAdmin", value = "/admin/report-management")
public class ServletReportAdmin extends HttpServlet {
    private ReportDao reportDao;
    private UserDao userDao;
    public void init() {
        reportDao = new ReportDao();
        userDao = new UserDao();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Report> reports = reportDao.getAllReport();
        request.setAttribute("reports", reports);
        request.getRequestDispatcher("/admin/report.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy reportID từ form
        String action = request.getParameter("action");
        String reportIDStr = request.getParameter("reportID");
        String newStatus = "Đã Hoàn Thành";
        String newStatus2 = "Lỗi không xử lý";
        HttpSession session = request.getSession();






        if(action != null){
            switch (action){
                case "complete":{
                    if (reportIDStr != null && !reportIDStr.isEmpty()) {
                        try {
                            // Chuyển đổi reportID thành int
                            int reportID = Integer.parseInt(reportIDStr);

                            // Lấy báo cáo bằng reportID
                            Report report = reportDao.getReportId(reportID);
                            if (report != null) {
                                // Cập nhật trạng thái báo cáo
                                report.setStatus(newStatus);
                                reportDao.updateReport(report);
                                String mesage = "Hệ thống đã xữ lý bạn có thể tạo Key mới";
                                userDao.insetStatus(mesage, report.getUserID());

                            }
                        } catch (NumberFormatException e) {
                            // Xử lý lỗi khi chuyển đổi hoặc khi thực hiện thao tác trên cơ sở dữ liệu
                            e.printStackTrace();
                            request.setAttribute("errorMessage", "Đã xảy ra lỗi khi cập nhật trạng thái báo cáo.");
                        }
                    }
                    break;
                }
                case "noEdit":{
                    if (reportIDStr != null && !reportIDStr.isEmpty()) {
                        try {
                            // Chuyển đổi reportID thành int
                            int reportID = Integer.parseInt(reportIDStr);

                            // Lấy báo cáo bằng reportID
                            Report report = reportDao.getReportId(reportID);
                            if (report != null) {
                                // Cập nhật trạng thái báo cáo
                                report.setStatus(newStatus2);
                                reportDao.updateReport(report);
                                if(report.getContent_report().equals("exposed")){
                                    String mesage = "Thông tin bạn cung cấp chưa đủ, mong bạn cung cấp đúng ngày và thời gian mất Key!!";
                                    userDao.insetStatus(mesage, report.getUserID());

                                }else {
                                    String mesage = "Thông tin bạn cung cấp chưa đủ mong bạn cung cấp nhìu thông tin hơn!!";
                                    userDao.insetStatus(mesage, report.getUserID());
                                }
                            }
                        } catch (NumberFormatException e) {
                            // Xử lý lỗi khi chuyển đổi hoặc khi thực hiện thao tác trên cơ sở dữ liệu
                            e.printStackTrace();
                            request.setAttribute("errorMessage", "Đã xảy ra lỗi khi cập nhật trạng thái báo cáo.");
                        }
                    }
                    String noEdit = "Yêu cầu bạn miêu tả đúng nội dung cần hỗ trợ";
                    request.setAttribute("noEdit", noEdit);
                    break;
                }
                case "delete":{
                    int reportID = Integer.parseInt(reportIDStr);
                    reportDao.deleteReport(reportID);
                    break;
                }
            }
        }



        // Sau khi cập nhật trạng thái, chuyển hướng lại trang báo cáo
        response.sendRedirect(request.getContextPath() + "/admin/report-management");

    }
}
