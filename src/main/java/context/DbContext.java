    package context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbContext {

    // JDBC URL, username và password của SQL Server
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=Demo;encrypt=false";
    private static final String USER = "sa";
    private static final String PASSWORD = "Trandat123";

    public static void main(String[] args) {
        Connection connection = null;

        try {
            // Đăng ký driver JDBC
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Mở kết nối
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            if (connection != null) {
                System.out.println("Kết nối thành công với SQL Server!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
