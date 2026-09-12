package glowstone.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB {
    private static Connection db = null;

    public static void startConnection() throws SQLException {
        db = DBConnector.getInstance();
    }

    public static void endConnection() throws SQLException {
        DBConnector.closeConnection();
        db = null;
    }

    public static void getFromDB(String table, int id) {
        try{
            PreparedStatement statement = db.prepareStatement("SELECT * FROM `" + table + "` WHERE `id` = '" + id + "'");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + " " + resultSet.getString("content") + " " + resultSet.getInt("name"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
