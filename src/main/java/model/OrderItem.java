package model;

import java.math.BigDecimal;

public class OrderItem {
    private int OrderItemId;
    private int orderId;
    private Product product;
    private int quantity;
    private float price;
    private float discount;
    private float totalPrice;

    public OrderItem(int orderItemId, int orderId, Product product, int quantity, float price, float discount, float totalPrice) {
        OrderItemId = orderItemId;
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.totalPrice = totalPrice;
    }

    public OrderItem() {

    }

    public int getOrderItemId() {
        return OrderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        OrderItemId = orderItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "OrderItemId=" + OrderItemId +
                ", orderId=" + orderId +
                ", product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +
                ", discount=" + discount +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
