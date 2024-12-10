package model;

import java.util.Date;

public class UserSignature {
    private int signatureId;
    private int userId; // ID người dùng
    private int orderId; // ID đơn hàng
    private String signature; // Chữ ký điện tử
    private Date signatureDate; // Thời gian ký

    // Constructor không tham số
    public UserSignature() {
    }

    public UserSignature(int signatureId, int userId, int orderId, String signature, Date signatureDate) {
        this.signatureId = signatureId;
        this.userId = userId;
        this.orderId = orderId;
        this.signature = signature;
        this.signatureDate = signatureDate;
    }

    public int getSignatureId() {
        return signatureId;
    }

    public void setSignatureId(int signatureId) {
        this.signatureId = signatureId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Date getSignatureDate() {
        return signatureDate;
    }

    public void setSignatureDate(Date signatureDate) {
        this.signatureDate = signatureDate;
    }
}
