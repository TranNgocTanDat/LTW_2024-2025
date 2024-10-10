document.addEventListener("DOMContentLoaded", function() {
    const currentPage = window.location.pathname; // Lấy đường dẫn hiện tại
    const navLeft = document.querySelector('.nav__left'); // Lấy phần tử nav__left
    const nav = document.querySelector('.nav') // lấy ra nav

    // Kiểm tra nếu đang ở trang sản phẩm
    if (currentPage.includes("productList.jsp")) {
        navLeft.style.display = 'flex'; // Hiển thị danh mục sản phẩm
    }
    if (currentPage.includes("cartProducts.jsp")) {
        nav.style.display = 'none'; // ẩm di nav
    }
    if (currentPage.includes("login.jsp")) {
        nav.style.display = 'none'; // ẩm di nav
    }
    console.log(currentPage);
});