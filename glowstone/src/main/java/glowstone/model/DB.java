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

    public static int insertNoteToDB(String content, String name, int group_id){
        int result = 0;
        try {
            PreparedStatement statement = db.prepareStatement("INSERT INTO `NOTE` (`content`, `name`, `group_id`, `thumbnail_id`) VALUES (?, ?, ?, ?)");
            statement.setString(1, content);
            statement.setString(2, name);
            statement.setInt(3, group_id);
            statement.setObject(4, null, Types.INTEGER);
            result = statement.executeUpdate();
            System.out.println("inserted into NOTE");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int insertGroupToDB(String name, int tab_id){
        int result = 0;
        try {
            PreparedStatement statement = db.prepareStatement("INSERT INTO `NOTE_GROUP` (`name`, `tab_id`, `thumbnail_id`) VALUES (?, ?, ?)");
            statement.setString(1, name);
            statement.setInt(2, tab_id);
            statement.setObject(3, null, Types.INTEGER);
            result = statement.executeUpdate();
            System.out.println("inserted into NOTE_GROUP");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int insertTabToDB(String name){
        int result = 0;
        try {
            PreparedStatement statement = db.prepareStatement("INSERT INTO `NOTE_TAB` (`name`, `thumbnail_id`) VALUES (?, ?)");
            statement.setString(1, name);
            statement.setObject(2, null, Types.INTEGER);
            result = statement.executeUpdate();
            System.out.println("inserted into NOTE_TAB");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int insertThumbnailToDB(String blobData){
        int result = 0;
        try {
            PreparedStatement statement = db.prepareStatement("INSERT INTO `THUMBNAIL` (`thumbnail`) VALUES (?)");
            statement.setString(1, blobData);
            result = statement.executeUpdate();
            System.out.println("inserted into THUMBNAIL");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int insertNoteCategoryToDB(String name, String color){
        int result = 0;
        try {
            PreparedStatement statement = db.prepareStatement("INSERT INTO `NOTE_CATEGORY` (`name`, `color`) VALUES (?, ?)");
            statement.setString(1, name);
            statement.setString(2, color);
            result = statement.executeUpdate();
            System.out.println("inserted into NOTE_CATEGORY");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int insertNoteGroupCategoryToDB(String name, String color){
        int result = 0;
        try {
            PreparedStatement statement = db.prepareStatement("INSERT INTO `NOTE_GROUP_CATEGORY` (`name`, `color`) VALUES (?, ?)");
            statement.setString(1, name);
            statement.setString(2, color);
            result = statement.executeUpdate();
            System.out.println("inserted into NOTE_GROUP_CATEGORY");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int insertNoteHasToDB(int noteId, int noteCategoryId){
        int result = 0;
        try {
            PreparedStatement statement = db.prepareStatement("INSERT INTO `NOTE_HAS` (`note_id`, `note_category_id`) VALUES (?, ?)");
            statement.setInt(1, noteId);
            statement.setInt(2, noteCategoryId);
            result = statement.executeUpdate();
            System.out.println("inserted into NOTE_HAS");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int insertGroupHasToDB(int groupId, int groupCategoryId){
        int result = 0;
        try {
            PreparedStatement statement = db.prepareStatement("INSERT INTO `GROUP_HAS` (`group_id`, `group_category_id`) VALUES (?, ?)");
            statement.setInt(1, groupId);
            statement.setInt(2, groupCategoryId);
            result = statement.executeUpdate();
            System.out.println("inserted into GROUP_HAS");
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

    public static ResultSet readThumbnail(int thumbnailId){
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = db.prepareStatement("SELECT * FROM `THUMBNAIL` WHERE `id` = '" + thumbnailId + "'");
            resultSet = statement.executeQuery();
            System.out.println("read THUMBNAIL with id " + thumbnailId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static ResultSet readThumbnail(){
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = db.prepareStatement("SELECT * FROM `THUMBNAIL`");
            resultSet = statement.executeQuery();
            System.out.println("read THUMBNAIL all");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static ResultSet readNoteCategory(int noteCategoryId){
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = db.prepareStatement("SELECT * FROM `NOTE_CATEGORY` WHERE `id` = '" + noteCategoryId + "'");
            resultSet = statement.executeQuery();
            System.out.println("read NOTE_CATEGORY with id " + noteCategoryId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static ResultSet readNoteCategory(){
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = db.prepareStatement("SELECT * FROM `NOTE_CATEGORY`");
            resultSet = statement.executeQuery();
            System.out.println("read NOTE_CATEGORY all");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static ResultSet readNoteGroupCategory(int groupCategoryId){
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = db.prepareStatement("SELECT * FROM `NOTE_GROUP_CATEGORY` WHERE `id` = '" + groupCategoryId + "'");
            resultSet = statement.executeQuery();
            System.out.println("read NOTE_GROUP_CATEGORY with id " + groupCategoryId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static ResultSet readNoteGroupCategory(){
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = db.prepareStatement("SELECT * FROM `NOTE_GROUP_CATEGORY`");
            resultSet = statement.executeQuery();
            System.out.println("read NOTE_GROUP_CATEGORY all");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static ResultSet readNoteHas(int noteId, int noteCategoryId){
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = db.prepareStatement("SELECT * FROM `NOTE_HAS` WHERE `note_id` = '" + noteId + "' AND `note_category_id` = '" + noteCategoryId + "'");
            resultSet = statement.executeQuery();
            System.out.println("read NOTE_HAS with noteId " + noteId + " and noteCategoryId " + noteCategoryId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static ResultSet readNoteHas(int noteId){
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = db.prepareStatement("SELECT * FROM `NOTE_HAS` WHERE `note_id` = '" + noteId + "'");
            resultSet = statement.executeQuery();
            System.out.println("read NOTE_HAS with noteId " + noteId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static ResultSet readNoteHas(){
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = db.prepareStatement("SELECT * FROM `NOTE_HAS`");
            resultSet = statement.executeQuery();
            System.out.println("read NOTE_HAS all");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static ResultSet readGroupHas(int groupId, int groupCategoryId){
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = db.prepareStatement("SELECT * FROM `GROUP_HAS` WHERE `group_id` = '" + groupId + "' AND `group_category_id` = '" + groupCategoryId + "'");
            resultSet = statement.executeQuery();
            System.out.println("read GROUP_HAS with groupId " + groupId + " and groupCategoryId " + groupCategoryId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static ResultSet readGroupHas(int groupId){
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = db.prepareStatement("SELECT * FROM `GROUP_HAS` WHERE `group_id` = '" + groupId + "'");
            resultSet = statement.executeQuery();
            System.out.println("read GROUP_HAS with groupId " + groupId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static ResultSet readGroupHas(){
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = db.prepareStatement("SELECT * FROM `GROUP_HAS`");
            resultSet = statement.executeQuery();
            System.out.println("read GROUP_HAS all");
        } catch (Exception e) {
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
            System.out.println("update NOTE with id " + noteId);
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
            System.out.println("update NOTE_GROUP with id " + groupId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //INSERT INTO `NOTE_TAB` (`name`, `thumbnail_id`)
    public static int updateTabInDB(int tabId, String name, int thumbnail_id){
        int result = 0;
        try {
            PreparedStatement statement = db.prepareStatement("UPDATE `NOTE_TAB` SET name=?, thumbnail_id=? WHERE id="+ tabId);
            statement.setString(1, name);
            if (thumbnail_id == 0) {
                statement.setObject(2, null, Types.INTEGER);
            } else {
                statement.setInt(2, thumbnail_id);
            }
            result = statement.executeUpdate();
            System.out.println("update NOTE_TAB with id " + tabId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static int updateGroupInDB(int groupId, String name, int tab_id){
        int result = 0;
        try {
            PreparedStatement statement = db.prepareStatement("UPDATE `NOTE_GROUP` SET name=?, tab_id=?, thumbnail_id=? WHERE id=" + groupId);
            statement.setString(1, name);
            statement.setInt(2, tab_id);
            statement.setObject(3, null, Types.INTEGER);
            result = statement.executeUpdate();
            System.out.println("update NOTE_GROUP with id " + groupId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int updateTabInDB(int tabId, String name){
        int result = 0;
        try {
            PreparedStatement statement = db.prepareStatement("UPDATE `NOTE_TAB` SET name=?, thumbnail_id=? WHERE id="+ tabId);
            statement.setString(1, name);
            statement.setObject(2, null, Types.INTEGER);
            result = statement.executeUpdate();
            System.out.println("update NOTE_TAB with id " + tabId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int updateNoteInDB(int noteId, String content, String name, int group_id){
        int result = 0;
        try {
            PreparedStatement statement = db.prepareStatement("UPDATE `NOTE` SET content=?, name=?, group_id=?, thumbnail_id=? WHERE id=" + noteId);
            statement.setString(1, content);
            statement.setString(2, name);
            statement.setInt(3, group_id);
            statement.setObject(4, null, Types.INTEGER);
            result = statement.executeUpdate();
            System.out.println("update NOTE with id " + noteId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int updateThumbnailInDB(int thumbnailId, String blobData){
        int result = 0;
        try {
            PreparedStatement statement = db.prepareStatement("UPDATE `THUMBNAIL` SET thumbnail=? WHERE id=" + thumbnailId);
            statement.setString(1, blobData);
            result = statement.executeUpdate();
            System.out.println("update THUMBNAIL with id " + thumbnailId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int updateNoteCategoryInDB(int noteCategoryId, String name, String color){
        int result = 0;
        try {
            PreparedStatement statement = db.prepareStatement("UPDATE `THUMBNAIL` SET name=?, color=? WHERE id=" + noteCategoryId);
            statement.setString(1, name);
            statement.setString(2, color);
            result = statement.executeUpdate();
            System.out.println("update NOTE_CATEGORY with id " + noteCategoryId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int updateNoteGroupCategoryInDB(int groupCategoryId, String name, String color){
        int result = 0;
        try {
            PreparedStatement statement = db.prepareStatement("UPDATE `THUMBNAIL` SET name=?, color=? WHERE id=" + groupCategoryId);
            statement.setString(1, name);
            statement.setString(2, color);
            result = statement.executeUpdate();
            System.out.println("update NOTE_GROUP_CATEGORY with id " + groupCategoryId);
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
            System.out.println("deleted " + id + " from NOTE_GROUP");
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
            System.out.println("deleted " + id + " from NOTE_TAB");
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static int deleteThumbnailFromDB(int id){
        int result = 0;
        try{
            PreparedStatement statement = db.prepareStatement("DELETE FROM `THUMBNAIL` WHERE `id` = '" + id + "'");
            result = statement.executeUpdate();
            System.out.println("deleted " + id + " from THUMBNAIL");
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static int deleteNoteCategoryFromDB(int id){
        int result = 0;
        try{
            PreparedStatement statement = db.prepareStatement("DELETE FROM `NOTE_CATEGORY` WHERE `id` = '" + id + "'");
            result = statement.executeUpdate();
            System.out.println("deleted " + id + " from NOTE_CATEGORY");
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static int deleteNoteGroupCategoryFromDB(int id){
        int result = 0;
        try{
            PreparedStatement statement = db.prepareStatement("DELETE FROM `NOTE_GROUP_CATEGORY` WHERE `id` = '" + id + "'");
            result = statement.executeUpdate();
            System.out.println("deleted " + id + " from NOTE_GROUP_CATEGORY");
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static int deleteNoteHasFromDB(int noteId, int noteCategoryId){
        int result = 0;
        try{
            PreparedStatement statement = db.prepareStatement("DELETE FROM `NOTE_HAS` WHERE `note_id` = '" + noteId + "' AND note_category_id = '" + noteCategoryId + "'");
            result = statement.executeUpdate();
            System.out.println("deleted relationship of note_id " + noteId + " and note_category_id " + noteCategoryId + " from NOTE_HAS");
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static int deleteGroupHasFromDB(int groupId, int groupCategoryId){
        int result = 0;
        try{
            PreparedStatement statement = db.prepareStatement("DELETE FROM `GROUP_HAS` WHERE `group_id` = '" + groupId + "' AND group_category_id = '" + groupCategoryId + "'");
            result = statement.executeUpdate();
            System.out.println("deleted relationship of group_id " + groupId + " and group_category_id " + groupCategoryId + " from GROUP_HAS");
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
