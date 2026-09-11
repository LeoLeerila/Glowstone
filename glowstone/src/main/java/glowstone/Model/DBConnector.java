package glowstone.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static Connection conn = null;
    private static String connectionUrl = "jdbc:mariadb://localhost/";
    private static String connectionUser = "root";
    private static String connectionPassword = "Example";

    private static void connect() throws SQLException {
        conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
    }

    public static Connection getInstance() throws SQLException {
        if (conn == null) {
            connect();
        }
        return conn;
    }

    public static void closeConnection() throws SQLException {
        conn.close();
    }

    public static void setConnectionUrl(String url) {
        connectionUrl = url;
    }
    
    public static void setConnectionUser(String user) {
        connectionUser = user;
    }
    
    public static void setConnectionPassword(String password) {
        connectionPassword = password;
    }
    
}
