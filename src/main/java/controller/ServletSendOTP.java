package controller;

import dao.UserDao;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Properties;

@WebServlet(name = "ServletSendOTP", value = "/sendOTP")
public class ServletSendOTP extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init() {
        userDao = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Chuyển đến trang nhập email để gửi OTP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/sendOTP.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Integer orderId = (Integer) session.getAttribute("orderId");

        if (action == null || action.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }

        switch (action) {
            case "sendOTP":
                handleSendOTP(request, response, session);
                break;
            case "sendReport":
                handleSendReport(request, response, session, orderId);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
                break;
        }
    }

    private void handleSendOTP(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        String email = request.getParameter("mail");

        if (email == null || email.isEmpty()) {
            request.setAttribute("otpMail", "Email không được để trống!");
            request.getRequestDispatcher("/sendOTP.jsp").forward(request, response);
            return;
        }

        if (!userDao.checkMail(email)) {
            request.setAttribute("otpMail", "Địa chỉ email không tồn tại trong hệ thống!");
            request.getRequestDispatcher("/sendOTP.jsp").forward(request, response);
            return;
        }

        // Sinh mã OTP ngẫu nhiên 6 chữ số
        int otp = (int) (Math.random() * 900000) + 100000;
        session.setAttribute("otp", otp);

        // Cấu hình SMTP
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
            // Gửi email OTP
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Mã OTP của bạn");
            message.setText("Mã OTP của bạn là: " + otp);

            Transport.send(message);
            request.setAttribute("otpMail", "Mã OTP đã được gửi thành công đến " + email + ".");
            request.getRequestDispatcher("/sendOTP.jsp").forward(request, response);
        } catch (MessagingException e) {
            e.printStackTrace();
            request.setAttribute("otpMail", "Gửi OTP thất bại. Vui lòng kiểm tra lại email hoặc kết nối mạng.");
            request.getRequestDispatcher("/sendOTP.jsp").forward(request, response);
        }
    }

    private void handleSendReport(HttpServletRequest request, HttpServletResponse response, HttpSession session, Integer orderId) throws ServletException, IOException {
        String userOtp = request.getParameter("otp");
        Integer generatedOtp = (Integer) session.getAttribute("otp");

        if (generatedOtp == null) {
            request.setAttribute("otpMail", "Mã OTP đã hết hạn hoặc không tồn tại. Vui lòng thử lại.");
            request.getRequestDispatcher("/sendOTP.jsp").forward(request, response);
        } else if (!userOtp.equals(generatedOtp.toString())) {
            request.setAttribute("otpMail", "Mã OTP không chính xác. Vui lòng thử lại.");
            request.getRequestDispatcher("/sendOTP.jsp").forward(request, response);
        } else {
            // OTP hợp lệ, chuyển đến ký đơn hàng
            request.setAttribute("otpMail", "Mã OTP hợp lệ đơn hàng đã được tạo thành công");
            response.sendRedirect(request.getContextPath() + "/ordersUser");
        }
    }
}
