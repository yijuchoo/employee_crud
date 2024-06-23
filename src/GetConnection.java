import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetConnection {
    public static Connection getConn(String dbName) {
        String user = "root";
        String pwd = "Welcome_1";
        String url = "jdbc:mysql://localhost:3306/" + dbName;
        // System.out.println(url);
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pwd);
            assert conn != null : "Connection Failed";
            return conn;
        } catch (SQLException e) {
            e.getMessage();
        }
        return conn;
    }
}
