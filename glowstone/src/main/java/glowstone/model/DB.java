package glowstone.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Dictionary;
import java.util.Enumeration;
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

    //create
    //INSERT INTO `NOTE` (`content`, `name`, `group_id`, `thumbnail_id`)
    public static int insertNoteToDB(String content, String name, int group_id, int thumbnail_id){
        int result = 0;
        try {
            PreparedStatement statement = db.prepareStatement("INSERT INTO `NOTE` (`content`, `name`, `group_id`, `thumbnail_id`) VALUES (?, ?, ?, ?)");
            statement.setString(1, content);
            statement.setString(2, name);
            statement.setInt(3, group_id);
            if (thumbnail_id == 0) {
                statement.setObject(4, null, Types.INTEGER);
            } else {
                statement.setInt(4, thumbnail_id);
            }
            result = statement.executeUpdate();
            System.out.println("inserted into NOTE");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //INSERT INTO `NOTE_GROUP` (`name`, `tab_id`, `thumbnail_id`)
    public static int insertGroupToDB(String name, int tab_id, int thumbnail_id){
        int result = 0;
        try {
            PreparedStatement statement = db.prepareStatement("INSERT INTO `NOTE_GROUP` (`name`, `tab_id`, `thumbnail_id`) VALUES (?, ?, ?)");
            statement.setString(1, name);
            statement.setInt(2, tab_id);
            if (thumbnail_id == 0) {
                statement.setObject(3, null, Types.INTEGER);
            } else {
                statement.setInt(3, thumbnail_id);
            }
            result = statement.executeUpdate();
            System.out.println("inserted into NOTE_GROUP");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //INSERT INTO `NOTE_TAB` (`name`, `thumbnail_id`)
    public static int insertTabToDB(String name, int thumbnail_id){
        int result = 0;
        try {
            PreparedStatement statement = db.prepareStatement("INSERT INTO `NOTE_TAB` (`name`, `thumbnail_id`) VALUES (?, ?)");
            statement.setString(1, name);
            if (thumbnail_id == 0) {
                statement.setObject(2, null, Types.INTEGER);
            } else {
                statement.setInt(2, thumbnail_id);
            }
            result = statement.executeUpdate();
            System.out.println("inserted into NOTE_TAB");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
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
