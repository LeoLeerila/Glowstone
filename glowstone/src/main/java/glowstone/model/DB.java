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

    //update
    //INSERT INTO `NOTE` (`content`, `name`, `group_id`, `thumbnail_id`)
    public static int updateNoteInDB(int noteId, String content, String name, int group_id, int thumbnail_id){
        int result = 0;
        try {
            PreparedStatement statement = db.prepareStatement("UPDATE `NOTE` SET content=?, name=?, group_id=?, thumbnail_id=? WHERE id=" + noteId);
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
    public static int updateGroupInDB(int groupId, String name, int tab_id, int thumbnail_id){
        int result = 0;
        try {
            PreparedStatement statement = db.prepareStatement("UPDATE `NOTE_GROUP` SET name=?, tab_id=?, thumbnail_id=? WHERE id=" + groupId);
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
    public static int updateTabInDB(int tabId, String name, int thumbnail_id){
        int result = 0;
        try {
            PreparedStatement statement = db.prepareStatement("UPDATE `NOTE` SET name=?, thumbnail_id=? WHERE id=" + tabId);
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

    //delete
    public static int deleteNoteFromDB(int id){
        int result = 0;
        try{
            PreparedStatement statement = db.prepareStatement("DELETE FROM `NOTE` WHERE `id` = '" + id + "'");
            result = statement.executeUpdate();
            System.out.println("deleted " + id + " from NOTE");
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static int deleteGroupFromDB(int id){
        int result = 0;
        try{
            //ensure no notes exist in group
            ResultSet rs = readNoteByGroup(id);
            while (rs.next()) {
                deleteNoteFromDB(rs.getInt("id"));
            }
            PreparedStatement statement = db.prepareStatement("DELETE FROM `NOTE_GROUP` WHERE `id` = '" + id + "'");
            result = statement.executeUpdate();
            System.out.println("deleted " + id + " from NOTE");
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static int deleteTabFromDB(int id){
        int result = 0;
        try{
            //ensure no groups exist in tab
            ResultSet rs = readGroupByTab(id);
            while (rs.next()) {
                deleteGroupFromDB(rs.getInt("id"));
            }
            PreparedStatement statement = db.prepareStatement("DELETE FROM `NOTE_TAB` WHERE `id` = '" + id + "'" );
            result = statement.executeUpdate();
            System.out.println("deleted " + id + " from NOTE");
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public static ResultSet readWholeTableFromDB(String table) {
        ResultSet resultSet = null;
        try{
            PreparedStatement statement = db.prepareStatement("SELECT * FROM " + table + "");
            resultSet = statement.executeQuery();
            System.out.println("read " + table);
        } catch (Exception e){
            e.printStackTrace();
        }
        return resultSet;
    }
}
