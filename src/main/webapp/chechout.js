// Danh sách các quận/huyện theo thành phố
const districtData = {
    hcm: [
        "Quận 1", "Quận 3", "Quận 4", "Quận 5", "Quận 7",
        "Quận 10", "Quận 11", "Quận 12", "Quận Bình Thạnh", "Quận Gò Vấp"
    ],
    hn: [
        "Quận Ba Đình", "Quận Hoàn Kiếm", "Quận Hai Bà Trưng",
        "Quận Đống Đa", "Quận Cầu Giấy", "Quận Thanh Xuân"
    ],
    dn: [
        "Quận Hải Châu", "Quận Thanh Khê", "Quận Liên Chiểu",
        "Quận Cẩm Lệ", "Quận Ngũ Hành Sơn", "Quận Sơn Trà"
    ]
};

// Hàm cập nhật quận/huyện dựa vào thành phố được chọn
function updateDistricts() {
    const citySelect = document.getElementById("city");
    const districtSelect = document.getElementById("district");
    const selectedCity = citySelect.value;

    // Xóa các tùy chọn quận/huyện hiện tại
    districtSelect.innerHTML = '<option value="">-- Chọn quận / huyện --</option>';

    // Nếu có thành phố được chọn, thêm quận/huyện tương ứng
    if (selectedCity && districtData[selectedCity]) {
        districtData[selectedCity].forEach(district => {
            const option = document.createElement("option");
            option.value = district.toLowerCase().replace(/\s/g, "-");
            option.textContent = district;
            districtSelect.appendChild(option);
        });
    }
}

window.onload = function() {
    const footer = document.querySelector('.footer__checkout');
    const form = document.querySelector('.checkout');
    if (footer && form) {
        const footerHeight = footer.offsetHeight;
        form.style.paddingBottom = `${footerHeight +20}px`; // Tự động thêm padding bằng chiều cao footer
    }
};
