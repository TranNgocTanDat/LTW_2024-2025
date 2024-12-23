package controller;

import dao.UserKeyDao;
import model.ChuKi_model;
import model.UserKey;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.security.KeyPair;
import java.util.Base64;
import java.util.Date;

@WebServlet(name = "ServletSign", value = "/signOrder")
public class ServletSign extends HttpServlet {
    private UserKeyDao userKeyDAO;
    private ChuKi_model chuKiModel;

    @Override
    public void init() throws ServletException {

        userKeyDAO =  new UserKeyDao();
        chuKiModel = new ChuKi_model();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("signOrder.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        //check session
        if(session == null || session.getAttribute("userId") == null){
            response.getWriter().write("Vui lòng đăng nhập trước khi tạo key");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        try {
            //Kiểm tra người dùng tạo key hay chưa
            UserKey existingKey = userKeyDAO.getKeyByUserId(userId);
            if (existingKey != null) {
                request.setAttribute("errorMessage", "Key của bạn đã tồn tại");
                request.getRequestDispatcher("signOrder.jsp").forward(request, response);
                return;
            }
            KeyPair keyPair = chuKiModel.generateKey("RSA");

            // Lấy public và private key
            String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

            UserKey userKey = new UserKey(0, userId, publicKey, privateKey, "RSA", new Date());
            userKeyDAO.saveKey(userKey);

            // Đặt thông báo thành công


            // Đặt publicKey và privateKey vào request attribute
            request.setAttribute("publicKey", publicKey);
            request.setAttribute("privateKey", privateKey);
            request.setAttribute("message", "Key của bạn đã được lưu thành công!");
            request.getRequestDispatcher("signOrder.jsp").forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
