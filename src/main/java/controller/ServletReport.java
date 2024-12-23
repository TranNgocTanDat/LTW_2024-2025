package controller;

import dao.ReportDao;
import dao.UserDao;
import model.Report;
import model.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@WebServlet(name = "ServletReport", value = "/report")
public class ServletReport extends HttpServlet {
    private ReportDao reportDao;
    private UserDao userDao;
    public void init() {
        reportDao = new ReportDao();
        userDao = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action != null){
            switch (action){
                case "sendReport":{
                    String userOtp = request.getParameter("otp");
                    HttpSession session = request.getSession();
                    Integer generatedOtp = (Integer) session.getAttribute("otp");

                    if (generatedOtp == null){
                        request.setAttribute("otpMail", "Mã OTP không chính xác !");
                        request.getRequestDispatcher("profile.jsp").forward(request, response);
                        return;
                    }else if(generatedOtp != null && userOtp.equals(generatedOtp.toString())){
                        String content = request.getParameter("content");
                        String mail = request.getParameter("mail");
                        String phone = request.getParameter("phone");
                        String message = request.getParameter("report");
                        User user = (User) session.getAttribute("user");
                        int userID = user.getUserId();

                        String status = "Đang chờ xử lý...";

                        if(userDao.checkMail(mail)==true && userDao.checkPhone(phone)==true){
                            Report rs = new Report( userID, content, message, status);
                            reportDao.insertReport(rs);
                            String error = "Report thành công !";
                            request.setAttribute("error", error);
                            request.getRequestDispatcher("profile.jsp").forward(request, response);
                        }else {
                            String error = "Thông tin ko hợp lệ";
                            request.setAttribute("error", error);
                            request.getRequestDispatcher("profile.jsp").forward(request, response);
                        }
                    }


                    break;
                }
                case "sendOTB":{
                    String email = request.getParameter("mail");
                    if(userDao.checkMail(email)==true){
                        HttpSession session = request.getSession();
                        boolean isReportView = true; // Duy trì trạng thái báo cáo
                        // Generate a random 6-digit OTP
                        int otp = (int) (Math.random() * 900000) + 100000;
                        session.setAttribute("otp", otp);

                        // SMTP configuration
                        String host = "smtp.gmail.com";
                        String port = "587";
                        final String username = "nguyendinhthanhdanh18@gmail.com"; // Replace with your email
                        final String password = "cdjdiecsebwlqhxp"; // Replace with your password

                        Properties props = new Properties();
                        props.put("mail.smtp.host", host);
                        props.put("mail.smtp.port", port);
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.smtp.starttls.enable", "true");

                        Session mailSession = Session.getInstance(props, new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                        try {
                            Message message = new MimeMessage(mailSession);
                            message.setFrom(new InternetAddress(username));
                            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                            message.setSubject("Your OTP Code");
                            message.setText("Your OTP code is: " + otp);

                            Transport.send(message);
                            request.setAttribute("otpMail", "Mã OTP đã được gửi thành công đến " + email + ".");
                            request.getRequestDispatcher("profile.jsp").forward(request, response);
                        } catch (MessagingException e) {
                            e.printStackTrace();
                            request.setAttribute("otpMail", "Gửi OTP thất bại. Vui lòng kiểm tra lại mail.");
                            request.getRequestDispatcher("profile.jsp").forward(request, response);
                        }
                        request.setAttribute("isReportView", isReportView);
                        request.getRequestDispatcher("profile.jsp").forward(request, response);
                    }else {
                        request.setAttribute("otpMail", "Địa chỉ email không chính xác ! ");
                        request.getRequestDispatcher("profile.jsp").forward(request, response);
                    }

                    break;
                }
            }
        }

    }

    private void listReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<Report> reports = reportDao.getAllReport();
        request.setAttribute("reports", reports);
        request.getRequestDispatcher("/admin/report.jsp").forward(request, response);
    }
}
