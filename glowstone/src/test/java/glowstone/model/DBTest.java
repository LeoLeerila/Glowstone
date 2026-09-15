package glowstone.model;

import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBTest {
    static int tab1Id;
    static int tab2Id;
    static  int group1Id;
    static  int group2Id;
    static  int note1Id;

    @BeforeAll
    static void startConn() throws SQLException {
        DB.startConnection();
        DB.insertTabToDB("TAB1",0);
        DB.insertTabToDB("TAB2",0);
        ResultSet rs = DB.readWholeTableFromDB("NOTE_TAB");
        if(rs.next()){
            tab1Id = rs.getInt("id");
            System.out.println("Tab1 id: "+ tab1Id);
        }
        if(rs.next()){
            tab2Id = rs.getInt("id");
            System.out.println("Tab2 id: "+ tab2Id);
        }

        DB.insertGroupToDB("GROUP1",tab1Id,0);
        DB.insertGroupToDB("GROUP2",tab2Id,0);

        rs = DB.readWholeTableFromDB("NOTE_GROUP");
        if(rs.next()){
            group1Id = rs.getInt("id");
            System.out.println("Group1 id: "+ group1Id);
        }
        if(rs.next()){
            group2Id = rs.getInt("id");
            System.out.println("Group2 id: "+ group2Id);
        }
        DB.insertNoteToDB("TEST_CONTENT", "NOTE1", group1Id, 0);
        DB.insertNoteToDB("TEST_CONTENT", "NOTE2", group2Id, 0);
        rs = DB.readWholeTableFromDB("NOTE");
        if(rs.next()){
            note1Id = rs.getInt("id");
            System.out.println("note1 id: "+ note1Id);
        }

    }

    @AfterAll
    static void endConn() throws SQLException {
        DB.deleteTabFromDB(tab1Id);
        DB.deleteTabFromDB(tab2Id);
        DB.endConnection();
    }

    @Test
    void updateNoteInDB() throws SQLException {
        ResultSet rs = DB.readNoteByGroup(1);
        int id = 0;
        if(rs.next())
        id = rs.getInt("id");
        DB.updateNoteInDB(id,"NEW_CONTENT","NEWNOTE",group2Id, 0);
        rs = DB.readFromDB("NOTE", id);
        if(rs.next())
        assertEquals("NEWNOTE", rs.getString("name"),"TEST ALSO CHECK UPD");




    }

    @Test
    void updateGroupInDB() throws SQLException {
        ResultSet rs = DB.readNoteByGroup(group2Id);
        int id = 0;
        if(rs.next())
        id = rs.getInt("id");
        DB.updateGroupInDB(id,"NEWGROUP",tab2Id,0);
        rs = DB.readFromDB("NOTE_GROUP", id);
        if(rs.next())
        assertEquals("NEWGROUP", rs.getString("name"),"TEST ALSO CHECK UPD");

    }

    @Test
    void updateTabInDB() throws SQLException {
        DB.updateTabInDB(tab1Id,"NEW_TAB",0);
        ResultSet rs = DB.readFromDB("NOTE_TAB", tab1Id);
        if(rs.next())
        assertEquals("NEW_TAB", rs.getString("name"),"TEST ALSO CHECK UPD");

    }

    @Test
    void readFromDB() throws SQLException {


        ResultSet rs = DB.readFromDB("NOTE_TAB", group2Id );
        if(rs.next())
        assertEquals("TAB2", rs.getString("name"));
        rs = DB.readFromDB("NOTE_GROUP", group2Id);
        if(rs.next())
        assertEquals("GROUP2", rs.getString("name"));
        rs = DB.readFromDB("NOTE", group2Id);
        if(rs.next())
        assertEquals("NOTE2", rs.getString("name"));

    }

    @Test
    void readNoteByGroup() throws SQLException {

        ResultSet rs = DB.readNoteByGroup(group2Id);
        if(rs.next())
        assertEquals("NOTE2", rs.getString("name"));
        if(rs.next())
        assertEquals("TEST_CONTENT", rs.getString("content"));
        if(rs.next())
        assertEquals(group2Id, rs.getInt("group_id"));
        if(rs.next())
        System.out.println("ID of the note: "+rs.getInt("id"));

    }

    @Test
    void readGroupByTab() throws SQLException {

        ResultSet rs = DB.readGroupByTab(tab2Id);
        if(rs.next())
        assertEquals("GROUP2", rs.getString("name"));

    }

    @Test
    void deleteNoteFromDB() throws SQLException {

        DB.deleteNoteFromDB(note1Id);
        ResultSet rs = DB.readNoteByGroup(group1Id);
        assertEquals(0,rs.getFetchSize());

    }

    @Test
    void deleteGroupFromDB() throws SQLException {
        ResultSet rs = DB.readGroupByTab(tab1Id);
        DB.deleteGroupFromDB(group1Id);

        assertEquals(0,rs.getFetchSize());

    }

    @Test
    void deleteTabFromDB() throws SQLException {

        DB.deleteTabFromDB(tab2Id);
        ResultSet rs = DB.readFromDB("NOTE_TAB",tab2Id);
        assertEquals(0, rs.getFetchSize());

    }
    @Test
    void readWholeTableFromDB() throws SQLException {
        ResultSet rs = DB.readWholeTableFromDB("NOTE_GROUP");
        if(rs.next()){
            assertEquals(group2Id, rs.getInt("id"));
        }
        if(rs.next()){
            assertEquals(group1Id, rs.getInt("id"));
        }
    }
}