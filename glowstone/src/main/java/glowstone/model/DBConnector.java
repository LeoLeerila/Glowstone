package glowstone.model;

import org.mariadb.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static Connection conn = null;
    private static String connectionUrl = "jdbc:mariadb://localhost:3306/glowstone_test";
    private static String connectionUser = "root";
    private static String connectionPassword = "example";

    private static void connect() {
        try {
            if (!DriverManager.getDrivers().hasMoreElements()) {
                System.out.println("you done fucked up now");
            }
            conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
