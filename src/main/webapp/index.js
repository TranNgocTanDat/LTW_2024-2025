const listIMG = document.querySelector('.img');
const imgs = document.querySelector('.img__item');
let count = 0;
let len = imgs.length;
console.log(len)
setInterval(() => {
    if(count==3){
        count = 0;
        listIMG.style.transform = `translateX(0px)`
    }else {
        count++;
        let width = imgs.width;
        listIMG.style.transform = `translateX(${width*-1*count}px)`
    }
}, 4000)

document.addEventListener("DOMContentLoaded", function() {
    const currentPage = window.location.pathname; // Lấy đường dẫn hiện tại
    if (currentPage.includes("Home.jsp")) { // Kiểm tra nếu là trang Home
        document.querySelector('.active').style.backgroundColor = '#bf9369'; // Đổi màu nền

    }
    if (currentPage.includes("productList.jsp")) {
        const navLeft = document.querySelector('.nav__left');
        if (navLeft) {
            navLeft.style.display = 'block'; // Hiện danh mục sản phẩm
        }
        const headerProduct = document.querySelector('.header__product');
        if (headerProduct) {
            headerProduct.style.backgroundColor = '#bf9369';
        }
    }
});