package model;

import java.util.Date;
import java.util.List;


public class Cart {
    private int userId;
    private int productId;
    private int quantity;
    private String sessionId;
    private String status;

    // Constructor
    public Cart(int userId, int productId, int quantity, String sessionId, String status) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.sessionId = sessionId;
        this.status = status;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getStatus() {
        return status;
    }
}