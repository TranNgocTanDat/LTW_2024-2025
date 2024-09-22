package model;

public class Cart {
    protected int cartId;
    protected int userId;
    protected int productId;
    protected int quatity;

    public Cart(int cartId, int userId, int productId, int quatity) {
        this.cartId = cartId;
        this.userId = userId;
        this.productId = productId;
        this.quatity = quatity;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuatity() {
        return quatity;
    }

    public void setQuatity(int quatity) {
        this.quatity = quatity;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", userId=" + userId +
                ", productId=" + productId +
                ", quatity=" + quatity +
                '}';
    }
}
