//package filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@WebFilter(filterName = "AdminAccessFilter")
//public class AdminAccessFilter implements Filter {
//    public void init(FilterConfig config) throws ServletException {
//    }
//
//    public void destroy() {
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        HttpSession session = httpRequest.getSession(false); // Lấy session hiện tại (không tạo mới)
//
//        // Kiểm tra nếu session không tồn tại hoặc không phải là admin
//        if (session == null || !"admin".equals(session.getAttribute("role"))) {
//            // Nếu người dùng không phải là admin hoặc không đăng nhập, chuyển hướng về login
//            httpResponse.sendRedirect("login.jsp");
//            return;
//        }
//
//        // Kiểm tra token để đảm bảo yêu cầu đến từ một phiên làm việc hợp lệ
//        String tokenFromRequest = httpRequest.getParameter("adminToken");
//        String sessionToken = (String) session.getAttribute("adminToken");
//
//        if (tokenFromRequest == null || !tokenFromRequest.equals(sessionToken)) {
//            httpResponse.sendRedirect("login.jsp");
//            return;
//        }
//
//
//        // Tiếp tục chuỗi Filter nếu tất cả điều kiện đều hợp lệ
//        chain.doFilter(request, response);
//    }
//}
