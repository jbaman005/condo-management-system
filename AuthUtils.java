import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AuthUtils {
    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users ("
            + "UserID INT AUTO_INCREMENT PRIMARY KEY,"
            + "Username VARCHAR(100) NOT NULL UNIQUE,"
            + "PasswordHash VARCHAR(64) NOT NULL,"
            + "CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
            + ")";

    public static void ensureUserTableExists() throws Exception {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(CREATE_USERS_TABLE);
        }
    }

    public static boolean registerUser(String username, String password) throws Exception {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return false;
        }

        ensureUserTableExists();

        if (userExists(username)) {
            return false;
        }

        String sql = "INSERT INTO users (Username, PasswordHash) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username.trim());
            ps.setString(2, hashPassword(password));
            ps.executeUpdate();
            return true;
        }
    }

    public static boolean authenticateUser(String username, String password) throws Exception {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return false;
        }

        ensureUserTableExists();

        String sql = "SELECT PasswordHash FROM users WHERE Username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("PasswordHash");
                    return storedHash.equals(hashPassword(password));
                }
                return false;
            }
        }
    }

    public static boolean userExists(String username) throws Exception {
        String sql = "SELECT 1 FROM users WHERE Username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username.trim());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private static String hashPassword(String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
