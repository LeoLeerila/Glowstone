package glowstone.main;
import glowstone.view.AppView;

import java.sql.ResultSet;

import glowstone.model.DB;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        AppView.launch(AppView.class);
        try {
            DB.startConnection();

            DB.insertTabToDB("java created tab", 0);
            ResultSet rs = DB.readFromDB("NOTE_TAB", 1);
            while (rs.next()) {
                System.out.println("id " + rs.getInt("id") + ", name " + rs.getString("name"));
            }

            DB.insertGroupToDB("java created group", 1, 0);
            rs = DB.readFromDB("NOTE_GROUP", 1);
            while (rs.next()) {
                System.out.println("id " + rs.getInt("id") + ", name " + rs.getString("name"));
            }

            //INSERT INTO `NOTE` (`content`, `name`, `group_id`, `thumbnail_id`)
            //                     NULL     NOTNULL   NOT NULL     NULL
            DB.insertNoteToDB("java created note content", "java created note name", 1, 0);
            rs = DB.readNoteByGroup(1);
            while (rs.next()) {
                System.out.println("id " + rs.getInt("id") + ", content " + rs.getString("content") + ", name " + rs.getString("name") + ", group_id " + rs.getInt("group_id") + ", thumbnail_id " + rs.getInt("thumbnail_id"));
            }

            DB.readFromDB("NOTE", 1);
            DB.readNoteByGroup(1);
            DB.readGroupByTab(1);

            DB.updateNoteInDB(1, "java updated note content", "java updated note name", 1, 0);
            DB.updateGroupInDB(1, "java updated note group", 1, 0);
            DB.updateTabInDB(1, "java updated tab", 0);

            rs = DB.readNoteByGroup(1);
            while (rs.next()) {
                System.out.println("id " + rs.getInt("id") + ", content " + rs.getString("content") + ", name " + rs.getString("name") + ", group_id " + rs.getInt("group_id") + ", thumbnail_id " + rs.getInt("thumbnail_id"));
            }

            rs = DB.readFromDB("NOTE_GROUP", 1);
            while (rs.next()) {
                System.out.println("id " + rs.getInt("id") + ", name " + rs.getString("name"));
            }

            rs = DB.readFromDB("NOTE_TAB", 1);
            while (rs.next()) {
                System.out.println("id " + rs.getInt("id") + ", name " + rs.getString("name"));
            }

            DB.insertGroupToDB("group to be deleted", 1, 0);
            DB.insertNoteToDB("note to be deleted", "note to be deleted", 2, 0);

            rs = DB.readFromDB("NOTE_TAB", 1);
            while (rs.next()) {
                System.out.println("id " + rs.getInt("id") + ", name " + rs.getString("name"));
            }

            rs = DB.readFromDB("NOTE_GROUP", 1);
            while (rs.next()) {
                System.out.println("id " + rs.getInt("id") + ", name " + rs.getString("name"));
            }

            rs = DB.readFromDB("NOTE_GROUP", 2);
            while (rs.next()) {
                System.out.println("id " + rs.getInt("id") + ", name " + rs.getString("name"));
            }

            rs = DB.readFromDB("NOTE", 1);
            while (rs.next()) {
                System.out.println("id " + rs.getInt("id") + ", name " + rs.getString("name"));
            }

            rs = DB.readFromDB("NOTE", 2);
            while (rs.next()) {
                System.out.println("id " + rs.getInt("id") + ", name " + rs.getString("name"));
            }

            DB.deleteNoteFromDB(1);
            DB.deleteGroupFromDB(1);
            DB.deleteTabFromDB(1);

            rs = DB.readFromDB("NOTE_TAB", 1);
            while (rs.next()) {
                System.out.println("id " + rs.getInt("id") + ", name " + rs.getString("name"));
            }

            rs = DB.readFromDB("NOTE_GROUP", 1);
            while (rs.next()) {
                System.out.println("id " + rs.getInt("id") + ", name " + rs.getString("name"));
            }

            rs = DB.readFromDB("NOTE_GROUP", 2);
            while (rs.next()) {
                System.out.println("id " + rs.getInt("id") + ", name " + rs.getString("name"));
            }

            rs = DB.readFromDB("NOTE", 1);
            while (rs.next()) {
                System.out.println("id " + rs.getInt("id") + ", name " + rs.getString("name"));
            }

            rs = DB.readFromDB("NOTE", 2);
            while (rs.next()) {
                System.out.println("id " + rs.getInt("id") + ", name " + rs.getString("name"));
            }

            DB.endConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}