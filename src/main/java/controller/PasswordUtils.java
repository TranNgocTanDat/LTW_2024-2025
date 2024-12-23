package controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {

    // Hash the password with salt using SHA-256
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            String saltedPassword = salt + password; // Kết hợp salt với mật khẩu
            byte[] hashedBytes = messageDigest.digest(saltedPassword.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error: SHA-256 algorithm not found.", e);
        }
    }


    // Check if the hashed password matches the stored hash
    public static boolean checkPassword(String inputPassword, String storedHash, String salt) {
        String hashedInput = hashPassword(inputPassword, salt); // Hash lại mật khẩu nhập vào với salt
        return hashedInput.equals(storedHash); // So sánh với hash đã lưu
    }

}
