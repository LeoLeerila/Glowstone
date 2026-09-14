package glowstone.db;

import glowstone.model.DB;
import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBTest {
    @BeforeAll
    static void startConn() throws SQLException {
        DB.startConnection();
    }

    @AfterAll
    static void endConn() throws SQLException {
        DB.endConnection();
    }

    @Test
    void insertNoteToDB() throws SQLException {

        DB.insertNoteToDB("TEST_CONTENT", "NOTE1", 1, 0);
        DB.insertNoteToDB("TEST_CONTENT", "NOTE2", 2, 0);

    }

    @Test
    void insertGroupToDB() throws SQLException {

        DB.insertGroupToDB("GROUP1",1,0);
        DB.insertGroupToDB("GROUP2",2,0);
        DB.insertGroupToDB("GROUP3",2,0);

    }

    @Test
    void insertTabToDB() throws SQLException {

        DB.insertTabToDB("TAB1",0);
        DB.insertTabToDB("TAB2",0);

    }

    @Test
    void updateNoteInDB() throws SQLException {

        DB.updateNoteInDB(1,"NEW_CONTENT","NEWNOTE",1, 0);

    }

    @Test
    void updateGroupInDB() throws SQLException {

        DB.updateGroupInDB(1,"NEWGROUP",1,0);

    }

    @Test
    void updateTabInDB() throws SQLException {

        DB.updateTabInDB(1,"NEW_TAB",0);

    }

    @Test
    void readFromDB() throws SQLException {

        ResultSet rs = DB.readFromDB("NOTE_TAB", 1);
        if(rs.next())
        assertEquals("NEW_TAB", rs.getString("name"),"TEST ALSO CHECK UPD");
        rs = DB.readFromDB("NOTE_GROUP", 1);
        if(rs.next())
        assertEquals("NEWGROUP", rs.getString("name"),"TEST ALSO CHECK UPD");
        rs = DB.readFromDB("NOTE", 1);
        if(rs.next())
        assertEquals("NEW_NOTE", rs.getString("name"),"TEST ALSO CHECK UPD");
        rs = DB.readFromDB("NOTE_TAB", 2 );
        if(rs.next())
        assertEquals("TAB2", rs.getString("name"));
        rs = DB.readFromDB("NOTE_GROUP", 2);
        if(rs.next())
        assertEquals("GROUP2", rs.getString("name"));
        rs = DB.readFromDB("NOTE", 2);
        if(rs.next())
        assertEquals("NOTE2", rs.getString("name"));

    }

    @Test
    void readNoteByGroup() throws SQLException {

        ResultSet rs = DB.readNoteByGroup(2);
        rs.first();
        assertEquals("NOTE2", rs.getString("name"));
        assertEquals("TEST_CONTENT", rs.getString("content"));
        assertEquals(2, rs.getInt("group_id"));
        System.out.println("ID of the note: "+rs.getInt("id"));

    }

    @Test
    void readGroupByTab() throws SQLException {

        ResultSet rs = DB.readGroupByTab(2);
        rs.first();
        assertEquals("GROUP2", rs.getString("name"));

    }

    @Test
    void deleteNoteFromDB() throws SQLException {

        DB.deleteNoteFromDB(1);
        ResultSet rs = DB.readNoteByGroup(1);
        assertEquals(0,rs);

    }

    @Test
    void deleteGroupFromDB() throws SQLException {
        ResultSet rs = DB.readGroupByTab(1);
        DB.deleteGroupFromDB(1);

        assertEquals(0,rs);

    }

    @Test
    void deleteTabFromDB() throws SQLException {

        DB.deleteTabFromDB(1);
        ResultSet rs = DB.readFromDB("NOTE_TAB",1);
        assertEquals(0,rs);

    }
}