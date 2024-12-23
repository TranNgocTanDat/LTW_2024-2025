<%--
  Created by IntelliJ IDEA.
  User: Danh Nguyen
  Date: 12/20/2024
  Time: 12:29 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>DoubleD</title>
    <style>
        .container {
            padding: 50px;
            text-align: center;
        }

        .container__report {
            display: flex;
            border-radius: 20px;
            border: 1px solid black;
            background-color: #d3d1d1;
            margin-bottom: 20px;
            justify-content: space-between; /* Căn đều các phần tử trong container */
            padding: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }

        .container__report--main {
            display: flex;
            justify-content: space-between; /* Căn đều các phần tử trong container */
            width: 100%;                   /* Đảm bảo các phần tử chiếm hết chiều rộng */
        }

        .report__userID {
            flex: 1;                       /* Các phần tử trong container__report--main sẽ chiếm đều không gian */
            text-align: center;            /* Căn giữa nội dung */
        }

        .report__userID h3 {
            margin: 0;                     /* Loại bỏ margin mặc định */
        }

        .report__userID h4 {
            margin: 5px 0;                 /* Thêm khoảng cách giữa các thẻ h4 */
        }

        .btn__finish {
            padding: 10px 20px;
            cursor: pointer;
            border-radius: 10px;
            box-shadow: #999999;
        }

        .btn__finish:hover {
            background-color: red;
        }
    </style>
</head>
<body>

        <div class="container">
            <h1>Quản lý report của khách hàng</h1>
            <c:forEach var="report" items="${reports}">
            <form action="${pageContext.request.contextPath}/admin/report-management" method="post">
                <div class="container__report">
                    <div class="container__report--main">
                        <div class="report__userID">
                            <h3>UserID</h3>
                            <h4>${report.userID}</h4>
                        </div>
                        <div class="report__userID">
                            <h3>Yêu cầu</h3>
                            <h4>${report.content_report}</h4>
                        </div>
                        <div class="report__userID">
                            <h3>Nội dung báo cáo</h3>
                            <h4>${report.massage}</h4>
                        </div>
                        <div class="report__userID">
                            <h3> Trạng thái</h3>
                            <h4 class="status">${report.status}</h4>
                        </div>
                        <input type="hidden" name="reportID" value="${report.id}" />
                        <button type="submit" class="btn__finish complete" name="action" value="complete">Hoàn thành</button>
                        <button type="submit" class="btn__finish noEdit"  name="action" value="noEdit" style="margin-left: 10px; margin-right: 10px">Không giải quyết</button>
                        <button type="submit" class="btn__finish delete" name="action" value="delete" style="display: none">Xóa</button>
                    </div>

                </div>
            </form>
            </c:forEach>
        </div>


    <script>
        window.onload = function() {
            var reports = document.querySelectorAll('.container__report');
            var reportCount = reports.length;
            console.log(reportCount);
            // Lưu số lượng vào sessionStorage
            sessionStorage.setItem('reportCount', reportCount);

            var reports = document.querySelectorAll('.container__report');

            reports.forEach(function(report) {
                var statusElement = report.querySelector('.status');
                var statusText = statusElement.innerText; // Get the status text for each report

                var completeButton = report.querySelector('.complete');
                var noEdit = report.querySelector('.noEdit');
                var deleteButton = report.querySelector('.delete');

                if (statusText === "Đã Hoàn Thành") {
                    completeButton.style.display = "none"; // Hide "Hoàn thành" button
                    noEdit.style.display = "none";
                    deleteButton.style.display = "inline-block"; // Show "Xóa" button
                }
            });
        };

    </script>
</body>
</html>
