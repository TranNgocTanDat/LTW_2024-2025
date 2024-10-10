package filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String loginURI = httpRequest.getContextPath() + "/login"; // Đường dẫn đến trang đăng nhập
        String adminURI = httpRequest.getContextPath() + "/admin"; // Đường dẫn đến trang admin
        String homeURI = httpRequest.getContextPath() + "/home"; // Đường dẫn đến trang chính

        boolean loggedIn = (session != null && session.getAttribute("user") != null);
        boolean isAdmin = (session != null && "admin".equals(session.getAttribute("role"))); // Kiểm tra vai trò
        boolean loginRequest = httpRequest.getRequestURI().equals(loginURI);
        boolean adminRequest = httpRequest.getRequestURI().equals(adminURI);
        boolean homeRequest = httpRequest.getRequestURI().equals(homeURI);

        // Nếu đã đăng nhập và là admin, cho phép truy cập
        if (loggedIn && isAdmin) {
            chain.doFilter(request, response); // Tiếp tục nếu đã đăng nhập và là admin
        } else if (loggedIn && !isAdmin) {
            // Nếu đã đăng nhập nhưng không phải admin, chuyển hướng đến trang chính
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/home");
        }else if (adminRequest) {
            // Nếu yêu cầu trang admin nhưng chưa đăng nhập hoặc không phải là admin, thông báo lỗi
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to access this page.");
        } else if (loginRequest) {
            // Nếu yêu cầu trang đăng nhập, cho phép
            chain.doFilter(request, response);
        }  else if (!homeRequest) {
            // Nếu chưa đăng nhập và yêu cầu không phải trang chính, chuyển hướng đến trang chính
            httpResponse.sendRedirect(homeURI);
        } else {
            // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
            httpResponse.sendRedirect(loginURI);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Khởi tạo filter nếu cần
    }

    @Override
    public void destroy() {
        // Dọn dẹp tài nguyên nếu cần
    }
}