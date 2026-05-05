import java.sql.*;

public class DBConnection {
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3307/condo_db?useSSL=false&serverTimezone=UTC",
            "root",
            "JamesSelior1105"
        );
    }
}