package controller;

import dao.UserKeyDAO;
import model.ChuKi_model;
import model.UserKey;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Date;

@WebServlet(name = "ServletKey", value = "/key")
public class ServletKey extends HttpServlet {
    private UserKeyDAO userKeyDAO;
    private ChuKi_model chuKiModel;
    public void init(){
        userKeyDAO = new UserKeyDAO();
        chuKiModel = new ChuKi_model();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            request.getRequestDispatcher("generateKey.jsp").forward(request, response);

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
            if (existingKey!=null){
                response.getWriter().write("Key của bạn đã tồn tại");
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
            request.getRequestDispatcher("generateKey.jsp").forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
