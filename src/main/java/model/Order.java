package model;

import java.util.Date;

public class Order {
    protected int orderId;
    protected int userId;
    protected Date orderDate;
    protected float totalAmount;
    protected String recipientName;
    protected String shippingPhoneNumber;
    protected String paymentMethod;
    protected String paymentStatus;
    protected Date deliveryDate;
    protected boolean is_edited;
    protected String notes;
    String shippingAddress;
    protected String status;
    protected String signature;
    protected String order_content;
    public Order(){}

    public Order(int orderId, int userId, Date orderDate, float totalAmount, String recipientName, String shippingPhoneNumber, String paymentMethod, String paymentStatus, Date deliveryDate, boolean is_edited, String notes, String shippingAddress, String status, String signature, String order_content) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.recipientName = recipientName;
        this.shippingPhoneNumber = shippingPhoneNumber;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.deliveryDate = deliveryDate;
        this.is_edited = is_edited;
        this.notes = notes;
        this.shippingAddress = shippingAddress;
        this.status = status;
        this.signature = signature;
        this.order_content = order_content;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getShippingPhoneNumber() {
        return shippingPhoneNumber;
    }

    public void setShippingPhoneNumber(String shippingPhoneNumber) {
        this.shippingPhoneNumber = shippingPhoneNumber;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public boolean isIs_edited() {
        return is_edited;
    }

    public void setIs_edited(boolean is_edited) {
        this.is_edited = is_edited;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getOrder_content() {
        return order_content;
    }

    public void setOrder_content(String order_content) {
        this.order_content = order_content;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", recipientName='" + recipientName + '\'' +
                ", shippingPhoneNumber='" + shippingPhoneNumber + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", deliveryDate=" + deliveryDate +
                ", is_edited=" + is_edited +
                ", notes='" + notes + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", status='" + status + '\'' +
                ", signature='" + signature + '\'' +
                ", order_content='" + order_content + '\'' +
                '}';
    }
}
