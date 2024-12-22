document.addEventListener("DOMContentLoaded", function() {
    const currentPage = window.location.pathname; // Lấy đường dẫn hiện tại
    const nav = document.querySelector('.nav'); // lấy ra thẻ nav

    // Kiểm tra nếu đang ở trang login.jsp
    if (currentPage.includes("login.jsp")) {
        nav.style.display = 'none'; // Ẩn thẻ nav
    }
    console.log(currentPage); // Kiểm tra đường dẫn hiện tại trong console
});