package glowstone;

import java.sql.ResultSet;
import java.util.Dictionary;
import java.util.Hashtable;

import glowstone.model.DB;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
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

            DB.endConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}