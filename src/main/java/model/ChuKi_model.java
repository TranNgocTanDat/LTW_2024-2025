package model;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;



public class ChuKi_model {

	private static KeyPair keyPair;
	private static SecureRandom secureRandom;
	private Signature signature;

	public KeyPair getKeyPair() {
		return keyPair;
	}

	public ChuKi_model() {
	}

	// Tạo cặp khóa mới theo thuật toán đã chọn
	public KeyPair generateKey(String algorithm) throws Exception {
		if (this.keyPair == null) {
			if (this.secureRandom == null) {
				this.secureRandom = new SecureRandom();
			}
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
			keyPairGenerator.initialize(2048, secureRandom); // Kích thước khóa 2048 bit
			keyPair = keyPairGenerator.generateKeyPair();
			initSignature( algorithm);

		}
		return keyPair;
	}

	// Khởi tạo đối tượng Signature với thuật toán băm và thuật toán mã hóa

	public void initSignature( String algorithm) throws Exception {
		if (this.signature == null) {
			this.signature = Signature.getInstance("SHA256" + "with" + algorithm);
		}
		this.secureRandom = new SecureRandom(); // Khởi tạo SecureRandom
	}



	public String signMessage(String message, byte[] hashedMessage) throws Exception {
		if (keyPair == null) {
			throw new Exception("Hãy tạo key hoặc load key của bạn!");
		}

		// Đảm bảo đối tượng Signature đã được khởi tạo trước khi ký
		if (signature == null) {
			throw new Exception("Đối tượng Signature chưa được khởi tạo.");
		}
		
		if (hashedMessage.length == 0) {
			throw new Exception("Dữ liệu chưa được băm");
		}
		

		hashedMessage = generateHash(message);


		// In ra giá trị băm dưới dạng Hexadecimal hoặc Base64
		System.out.println("Giá trị băm: " + bytesToHex(hashedMessage));

		// Khởi tạo Signature và ký giá trị băm
		signature.initSign(keyPair.getPrivate(), secureRandom);
		signature.update(hashedMessage);

		// Tạo chữ ký và trả về dạng Base64
		byte[] signatureBytes = signature.sign();
		return Base64.getEncoder().encodeToString(signatureBytes);
	}

	// Tạo băm của một thông điệp

	public static byte[] generateHash(String message) throws Exception {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		byte[] hashBytes = messageDigest.digest(message.getBytes(StandardCharsets.UTF_8));
		return hashBytes;
	}

	public static String bytesToHex(byte[] bytes) {
		StringBuilder hexString = new StringBuilder();
		for (byte b : bytes) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	public static byte[] hexToBytes(String hex) {
		int length = hex.length();
		byte[] bytes = new byte[length / 2];
		for (int i = 0; i < length; i += 2) {
			bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
		}
		return bytes;
	}

	// Băm một tệp tin

	public byte[] hashFile(String path) {
		try (InputStream fis = new FileInputStream(path)) {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");

			byte[] byteArray = new byte[1024];
			int bytesRead;

			// Đọc dữ liệu tệp tin theo từng khối và cập nhật giá trị băm
			while ((bytesRead = fis.read(byteArray)) != -1) {
				digest.update(byteArray, 0, bytesRead);
			}

			// Chuyển đổi giá trị băm thành chuỗi dạng hex
			byte[] hashBytes = digest.digest();
			
			return hashBytes;

		} catch (Exception e) {
			System.out.println("Lỗi khi băm tệp: " + e.getMessage());
			return null;
		}
	}

	// Xác minh chữ ký của một thông điệp

	public boolean verifySignature(String message, String signatureString) throws Exception {
		Signature signature = this.signature != null ? this.signature : Signature.getInstance("SHA256withRSA");

		if (keyPair == null) {
			throw new Exception("Cặp khóa chưa được khởi tạo.");
		}

		// Kiểm tra chuỗi chữ ký không rỗng
		if (signatureString == null || signatureString.isEmpty()) {
			throw new IllegalArgumentException("Chuỗi chữ ký không thể rỗng.");
		}

		signatureString = signatureString.trim();

		// Đảm bảo chiều dài của chuỗi chữ ký là bội số của 4
		while (signatureString.length() % 4 != 0) {
			signatureString += "="; // Thêm dấu "=" để chiều dài là bội số của 4
		}

		byte[] signatureBytes;
		try {
			signatureBytes = Base64.getDecoder().decode(signatureString);
		} catch (IllegalArgumentException e) {
			throw new Exception("Mã hóa Base64 không hợp lệ cho chữ ký.", e);
		}

		byte[] hashedMessage = generateHash(message);


		// Lấy khóa công khai và khởi tạo đối tượng Signature
		PublicKey publicKey = keyPair.getPublic();
		signature.initVerify(publicKey);

		// Cập nhật Signature với thông điệp
		signature.update(hashedMessage);

		return signature.verify(signatureBytes); // Kiểm tra tính hợp lệ của chữ ký
	}

	// Ký một tệp và trả về chữ ký dưới dạng Base64

	public String signFile(String filePath, byte[] hashedFile) throws Exception {
		if (keyPair == null) {
			throw new Exception("Hãy tạo key hoặc load key của bạn!");
		}

		if (signature == null) {
			throw new Exception("Đối tượng Signature chưa được khởi tạo.");
		}
		
		if (hashedFile.length == 0) {
			throw new Exception("Dữ liệu chưa được băm");
		}

		// Đọc nội dung tệp

		hashedFile = hashFile(filePath);

		
		// Khởi tạo Signature với khóa riêng để ký
		PrivateKey privateKey = keyPair.getPrivate();
		signature.initSign(privateKey, secureRandom);
		signature.update(hashedFile);

		// Ký tệp và trả về chữ ký dưới dạng Base64
		byte[] signatureBytes = signature.sign();
		String base64Signature = Base64.getEncoder().encodeToString(signatureBytes);

		return base64Signature;
	}

	// Xác minh chữ ký của một tệp bằng khóa công khai

	public boolean verifyFile(String filePath, String signatureString) throws Exception {

		if (keyPair == null) {
			throw new Exception("Cặp khóa chưa được khởi tạo.");
		}
		
		 // Băm nội dung tệp trước khi xác minh

	    byte[] fileBytes = hashFile(filePath);


		// Giải mã chữ ký từ chuỗi Base64
		byte[] signatureBytes;
		try {
			signatureBytes = Base64.getDecoder().decode(signatureString);
		} catch (IllegalArgumentException e) {
			throw new Exception("Mã hóa Base64 không hợp lệ cho chữ ký.", e);
		}

		// Lấy khóa công khai và khởi tạo đối tượng Signature
		PublicKey publicKey = keyPair.getPublic();
		signature.initVerify(publicKey);

		// Cập nhật Signature với nội dung tệp
		signature.update(fileBytes);

		return signature.verify(signatureBytes); // Kiểm tra tính hợp lệ của chữ ký
	}


	// Hàm đọc Public Key từ file (dạng plain text Base64)
	public static PublicKey loadPublicKey(String filePath) throws Exception {
		// Đọc chuỗi Base64 từ file
		String keyString = new String(Files.readAllBytes(Paths.get(filePath))).replaceAll("\\s+", ""); // Loại bỏ khoảng
		// trắng
		// Decode chuỗi Base64 thành byte array
		byte[] keyBytes = Base64.getDecoder().decode(keyString);
		// Tạo PublicKey từ byte array
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(spec);
	}

	// Hàm đọc Private Key từ file (dạng plain text Base64)
	public static PrivateKey loadPrivateKey(String filePath) throws Exception {
		// Đọc chuỗi Base64 từ file
		String keyString = new String(Files.readAllBytes(Paths.get(filePath))).replaceAll("\\s+", ""); // Loại bỏ khoảng
		// trắng
		// Decode chuỗi Base64 thành byte array
		byte[] keyBytes = Base64.getDecoder().decode(keyString);
		// Tạo PrivateKey từ byte array
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(spec);
	}

	// Chuyển PublicKey thành chuỗi Base64
	public static String publicKeyToBase64(PublicKey publicKey) {
		return Base64.getEncoder().encodeToString(publicKey.getEncoded());
	}

	// Chuyển PrivateKey thành chuỗi Base64
	public static String privateKeyToBase64(PrivateKey privateKey) {
		return Base64.getEncoder().encodeToString(privateKey.getEncoded());
	}

	// Save Key to a File
	public static void savePublicKeyToFile(String filePath, PublicKey publicKey) {
		String key = publicKeyToBase64(publicKey);
		// Ghi chuỗi Base64 vào file
		File file = new File(filePath);
		try (FileWriter writer = new FileWriter(file)) {
			writer.write(key);
			System.out.println("PublicKey đã được lưu thành công tại: " + filePath);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Lỗi lưu key: " + e.getMessage());
		}
	}

	// Lưu Private Key to a file
	public static void savePrivateKeyToFile(String filePath, PrivateKey privateKey) {
		String key = privateKeyToBase64(privateKey);
		// Ghi chuỗi Base64 vào file
		File file = new File(filePath);
		try (FileWriter writer = new FileWriter(file)) {
			writer.write(key);
			System.out.println("PrivateKey được lưu thành công tại: " + filePath);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Lỗi lưu key: " + e.getMessage());
		}
	}

	// Chuyển chuỗi Base64 thành PublicKey
	public static PublicKey base64ToPublicKey(String base64Key) throws Exception {
		byte[] decoded = Base64.getDecoder().decode(base64Key);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(keySpec);
	}

	// Chuyển chuỗi Base64 thành PrivateKey
	public static PrivateKey base64ToPrivateKey(String base64Key) throws Exception {
		byte[] decoded = Base64.getDecoder().decode(base64Key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(keySpec);
	}


	// Phương thức trả về PublicKey
	public PublicKey getPublicKey() {
		return keyPair.getPublic();
	}

	// Phương thức trả về PrivateKey
	public PrivateKey getPrivateKey() {
		return keyPair.getPrivate();
	}

	public static void main(String[] args) {
		try {
	        // Tạo một đối tượng ChuKi_model
	        ChuKi_model chuKiModel = new ChuKi_model();

	        // 1. Tạo cặp khóa RSA
	        System.out.println("Tạo cặp khóa RSA...");
	        chuKiModel.generateKey("RSA");
	        System.out.println("Cặp khóa đã được tạo thành công.");

	        // 2. Băm một thông điệp và in ra giá trị băm (MD5)
	        String message = "TranNgocTanDat";


	        byte[] hashedMessage = generateHash(message);
	        System.out.println("Giá trị băm của thông điệp '" + message + "': " + chuKiModel.bytesToHex(hashedMessage));

	        // 3. Ký thông điệp và lấy chữ ký dưới dạng Base64
	        String signedMessage = chuKiModel.signMessage(message,  hashedMessage);
	        System.out.println("Chữ ký của thông điệp: " + signedMessage);

	        // 4. Xác minh chữ ký với thông điệp và chữ ký đã ký (Base64)
	        boolean isVerified = chuKiModel.verifySignature(message, signedMessage);

	        System.out.println("Chữ ký có hợp lệ không? " + isVerified);

	        // 5. Ký một tệp tin và in ra chữ ký Base64
	        String filePath = "C:\\Users\\USER\\Documents\\diem.txt";  // Đảm bảo rằng tệp tin này tồn tại trên hệ thống của bạn\

	        byte[] hashedFile = chuKiModel.hashFile(filePath);
	        String fileSignature = chuKiModel.signFile(filePath,  hashedFile);
	        System.out.println("Chữ ký của tệp tin " + filePath + ": " + fileSignature);

	        // 6. Xác minh chữ ký của tệp tin
	        boolean isFileVerified = chuKiModel.verifyFile(filePath, fileSignature);
	        System.out.println("Chữ ký tệp tin có hợp lệ không? " + isFileVerified);

	        // 7. Băm một tệp tin và in ra giá trị băm (SHA-256)
//	        byte[] hashedFile = chuKiModel.hashFile(filePath, "SHA-256");
	        String fileHash = chuKiModel.bytesToHex(hashedFile);
	        System.out.println("Giá trị băm của tệp tin " + filePath + ": " + fileHash);

	    } catch (Exception e) {
	        e.printStackTrace(); // In ra lỗi nếu có
	    }
	}

}
