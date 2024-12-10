package model;

import java.util.Date;

public class UserKey {
    private int keyId;
    private int userId; // ID người dùng
    private String publicKey; // Khóa công khai
    private String privateKey; // Khóa riêng (có thể null)
    private String keyType; // Loại khóa (ví dụ: RSA, AES)
    private Date creationDate; // Thời gian tạo khóa

    // Constructor không tham số
    public UserKey() {
    }

    public UserKey(int keyId, int userId, String publicKey, String privateKey, String keyType, Date creationDate) {
        this.keyId = keyId;
        this.userId = userId;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.keyType = keyType;
        this.creationDate = creationDate;
    }

    public int getKeyId() {
        return keyId;
    }

    public void setKeyId(int keyId) {
        this.keyId = keyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
