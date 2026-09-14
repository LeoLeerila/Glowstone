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

    //read
    public static ResultSet readFromDB(String table, int id) {
        ResultSet resultSet = null;
        try{
            PreparedStatement statement = db.prepareStatement("SELECT * FROM `" + table + "` WHERE `id` = '" + id + "'");
            resultSet = statement.executeQuery();
            System.out.println("read " + table + " with id " + id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return resultSet;
    }

    public static ResultSet readNoteByGroup(int groupId) {
        ResultSet resultSet = null;
        try{
            PreparedStatement statement = db.prepareStatement("SELECT * FROM `NOTE` WHERE `group_id` = '" + groupId + "'");
            resultSet = statement.executeQuery();
            System.out.println("read GROUP with id " + groupId);
        } catch (Exception e){
            e.printStackTrace();
        }
        return resultSet;
    }

    public static ResultSet readGroupByTab(int tabId) {
        ResultSet resultSet = null;
        try{
            PreparedStatement statement = db.prepareStatement("SELECT * FROM `NOTE_GROUP` WHERE `tab_id` = '" + tabId + "'");
            resultSet = statement.executeQuery();
            System.out.println("read TAB with id " + tabId);
        } catch (Exception e){
            e.printStackTrace();
        }
        return resultSet;
    }
}
