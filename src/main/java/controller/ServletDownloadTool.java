package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name = "ServletDownloadTool", value = "/downloadTool")
public class ServletDownloadTool extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đường dẫn tới file

        String pathFile = getServletContext().getRealPath("/file/ToolChuKi.exe");
        File fileToDownload = new File(pathFile);

        // Kiểm tra nếu file không tồn tại
        if (!fileToDownload.exists()) {
            request.setAttribute("message", "File không tồn tại!");
            request.getRequestDispatcher("/downloadTool.jsp").forward(request, response);
            return;
        }

        // Cài đặt header để tải file
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileToDownload.getName());

        // Đọc và gửi file
        try (FileInputStream fileInputStream = new FileInputStream(fileToDownload);
             OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            // Xử lý lỗi nếu không thể tải file
            request.setAttribute("message", "Lỗi khi tải file!");
            request.getRequestDispatcher("/downloadTool.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
