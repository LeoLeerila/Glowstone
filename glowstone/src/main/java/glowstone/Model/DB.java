package glowstone.Model;

import java.sql.Connection;
import java.sql.SQLException;

public class DB {
    private Connection db = null;

    public void startConnection() throws SQLException {
        db = DBConnector.getInstance();
    }

    public void endConnection() throws SQLException {
        DBConnector.closeConnection();
        db = null;
    }

    
}
