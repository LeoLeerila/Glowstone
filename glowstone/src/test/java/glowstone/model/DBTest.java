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
        DB.startConnection("glowstone_test");
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

    @Test 
    void insertsReturnDatabaseGeneratedIds() throws SQLException {
        int groupId = DB.insertGroupToDB("test group", tab1Id, 0);

        try {
            assertTrue(groupId > 0);

            int noteId = DB.insertNoteToDB(
                "test content",
                "test note",
                groupId,
                0
            );

            assertTrue(noteId > 0);
            assertNotEquals(0, noteId);
            assertNotEquals(0, groupId);
        } finally {
            DB.deleteGroupFromDB(groupId);
        }
    }

    
    @Test
    void InsertToDBWithoutThumbnail() throws SQLException {
        int tab3Id = DB.insertTabToDB("TAB3");
        int group3Id = DB.insertGroupToDB("GROUP3", tab3Id);
        int note3Id = DB.insertNoteToDB("TEST_CONTENT", "NOTE3", group3Id);

        try {
            assertTrue(tab3Id > 0);
            assertTrue(group3Id > 0);
            assertTrue(note3Id > 0);

            try (ResultSet rs = DB.readFromDB("NOTE_TAB", tab3Id)) {
                assertTrue(rs.next());
                assertEquals("TAB3", rs.getString("name"));
            }

            try (ResultSet rs = DB.readFromDB("NOTE_GROUP", group3Id)) {
                assertTrue(rs.next());
                assertEquals("GROUP3", rs.getString("name"));
                assertEquals(tab3Id, rs.getInt("tab_id"));

            }

            try (ResultSet rs = DB.readFromDB("NOTE", note3Id)) {
                assertTrue(rs.next());
                assertEquals("TEST_CONTENT", rs.getString("content"));
                assertEquals("NOTE3", rs.getString("name"));
                assertEquals(group3Id, rs.getInt("group_id"));
            }

            } finally {
                if (note3Id > 0) {
                    DB.deleteNoteFromDB(note3Id);
                }
                if (group3Id > 0) {
                    DB.deleteGroupFromDB(group3Id);
                }
                if (tab3Id > 0) {
                    DB.deleteTabFromDB(tab3Id);
                }
        }
    }

    @Test
    void UpdateWithoutThumbnail() throws SQLException {
        int tab3Id = DB.insertTabToDB("TAB3");
        int group3Id = DB.insertGroupToDB("GROUP3", tab3Id);
        int note3Id = DB.insertNoteToDB("TEST_CONTENT", "NOTE3", group3Id);

        DB.updateTabInDB(tab3Id,"NEW_TAB3");
        DB.updateGroupInDB(group3Id, "NEW_GROUP3", tab3Id);
        DB.updateNoteInDB(note3Id, "NEW_CONTENT", "NEW_NOTE3", group3Id);

        try {
            assertTrue(tab3Id > 0);
            assertTrue(group3Id > 0);
            assertTrue(note3Id > 0);

            try (ResultSet rs = DB.readFromDB("NOTE_TAB", tab3Id)) {
                assertTrue(rs.next());
                assertEquals("NEW_TAB3", rs.getString("name"));
            }

            try (ResultSet rs = DB.readFromDB("NOTE_GROUP", group3Id)) {
                assertTrue(rs.next());
                assertEquals("NEW_GROUP3", rs.getString("name"));
                assertEquals(tab3Id, rs.getInt("tab_id"));

            }

            try (ResultSet rs = DB.readFromDB("NOTE", note3Id)) {
                assertTrue(rs.next());
                assertEquals("NEW_CONTENT", rs.getString("content"));
                assertEquals("NEW_NOTE3", rs.getString("name"));
                assertEquals(group3Id, rs.getInt("group_id"));
            }


        } finally {
            if (note3Id > 0) {
                    DB.deleteNoteFromDB(note3Id);
                }
                if (group3Id > 0) {
                    DB.deleteGroupFromDB(group3Id);
                }
                if (tab3Id > 0) {
                    DB.deleteTabFromDB(tab3Id);
                }
        }
    }

    @Test
    void insertNoteCategoryToDB () throws SQLException {
        int category1Id = DB.insertNoteCategoryToDB("CATEGORY1", "#00ff00");
        int category2Id = DB.insertNoteCategoryToDB(null, "#00ff00");

        ResultSet rs = DB.readNoteCategory(category1Id);
        assertTrue(rs.next());
        assertEquals("CATEGORY1", rs.getString("name"));
        assertEquals("#00ff00", rs.getString("color"));

        assertEquals(0, category2Id);

        DB.deleteNoteCategoryFromDB(category1Id);
        DB.deleteNoteCategoryFromDB(category2Id);
    }

    @Test
    void InsertNoteGroupCategoryToDB() throws SQLException {
        int groupCategory1Id = DB.insertNoteGroupCategoryToDB("GROUPCATEGORY1", "#00ff00");
        int groupCategory2Id = DB.insertNoteGroupCategoryToDB(null, "#00ff00");

        ResultSet rs = DB.readNoteGroupCategory(groupCategory1Id);
        assertTrue(rs.next());
        assertEquals("GROUPCATEGORY1", rs.getString("name"));
        assertEquals("#00ff00", rs.getString("color"));

        assertEquals(0, groupCategory2Id);

        DB.deleteNoteGroupCategoryFromDB(groupCategory1Id);
        DB.deleteNoteGroupCategoryFromDB(groupCategory2Id);
    }

    @Test
    void updateNoteCategoryInDB() throws SQLException {
        int category1Id = DB.insertNoteCategoryToDB("CATEGORY1", "#00ff00");
        
        DB.updateNoteCategoryInDB(category1Id, "NEW_CATEGORY1", "#00ff00");

        ResultSet rs = DB.readNoteCategory(category1Id);
        assertTrue(rs.next());
        assertEquals("NEW_CATEGORY1", rs.getString("name"));
        assertEquals("#00ff00", rs.getString("color"));

        DB.deleteNoteCategoryFromDB(category1Id);
    }

    @Test
    void updateNoteGroupCategoryInDB() throws SQLException {
        int groupCategory1Id = DB.insertNoteGroupCategoryToDB("GROUP_CATEGORY1", "#00ff00");
        
        DB.updateNoteGroupCategoryInDB(groupCategory1Id, "NEW_GROUP_CATEGORY1", "#00ff00");

        ResultSet rs = DB.readNoteGroupCategory(groupCategory1Id);
        assertTrue(rs.next());
        assertEquals("NEW_GROUP_CATEGORY1", rs.getString("name"));
        assertEquals("#00ff00", rs.getString("color"));

        DB.deleteNoteGroupCategoryFromDB(groupCategory1Id);
    }

    @Test
    void readNoteCategory() throws SQLException {
        int category1Id = DB.insertNoteCategoryToDB("CATEGORY1", "#00ff00");

        ResultSet rs = DB.readNoteCategory();
        if(rs.next())
        assertEquals("CATEGORY1", rs.getString("name"));

        DB.deleteNoteCategoryFromDB(category1Id);
    }

    @Test
    void readNoteGroupCategory() throws SQLException {
        int groupCategory1Id = DB.insertNoteGroupCategoryToDB("GROUP_CATEGORY1", "#00ff00");

        ResultSet rs = DB.readNoteGroupCategory();
        if(rs.next())
        assertEquals("GROUP_CATEGORY1", rs.getString("name"));

        DB.deleteNoteGroupCategoryFromDB(groupCategory1Id);
    }

    @Test
    void insertNoteHasToDB() throws SQLException {
        int note3Id = DB.insertNoteToDB("TEST_CONTENT", "NOTE3", group2Id);
        int category1Id = DB.insertNoteCategoryToDB("CATEGORY1", "#00ff00");

        DB.insertNoteHasToDB(note3Id, category1Id);

        ResultSet rs = DB.readNoteHas(note3Id, category1Id);
        assertTrue(rs.next());
        assertEquals(note3Id, rs.getInt("note_id"));
        assertEquals(category1Id, rs.getInt("note_category_id"));
        assertFalse(rs.next());

        DB.deleteNoteHasFromDB(note3Id, category1Id);
        DB.deleteNoteFromDB(note3Id);
        DB.deleteNoteCategoryFromDB(category1Id);
    }

    @Test
    void insertGroupHasToDB() throws SQLException {
        int group3Id = DB.insertGroupToDB("TEST_CONTENT", tab2Id);
        int groupCategory1Id = DB.insertNoteGroupCategoryToDB("GROUP_CATEGORY1", "#00ff00");

        DB.insertGroupHasToDB(group3Id, groupCategory1Id);

        ResultSet rs = DB.readGroupHas(group3Id, groupCategory1Id);
        assertTrue(rs.next());
        assertEquals(group3Id, rs.getInt("group_id"));
        assertEquals(groupCategory1Id, rs.getInt("group_category_id"));
        assertFalse(rs.next());

        DB.deleteGroupHasFromDB(group3Id, groupCategory1Id);
        DB.deleteGroupFromDB(group3Id);
        DB.deleteNoteGroupCategoryFromDB(groupCategory1Id);
    }

    @Test
    void readNoteHas() throws SQLException {
        int note3Id = DB.insertNoteToDB("TEST_CONTENT", "NOTE3", group2Id);
        int category1Id = DB.insertNoteCategoryToDB("CATEGORY1", "#00ff00");

        DB.insertNoteHasToDB(note3Id, category1Id);

        ResultSet rs = DB.readNoteHas(note3Id);
        assertTrue(rs.next());
        assertEquals(note3Id, rs.getInt("note_id"));
        assertEquals(category1Id, rs.getInt("note_category_id"));
        assertFalse(rs.next());

        ResultSet rs2 = DB.readNoteHas();
        assertTrue(rs2.next());
        assertEquals(note3Id, rs2.getInt("note_id"));
        assertEquals(category1Id, rs2.getInt("note_category_id"));
        assertFalse(rs2.next());

        DB.deleteNoteHasFromDB(note3Id);
        DB.deleteNoteFromDB(note3Id);
        DB.deleteNoteCategoryFromDB(category1Id);
    }

    @Test
    void readGroupHas() throws SQLException {
        int group3Id = DB.insertGroupToDB("GROUP3", tab2Id);
        int groupCategory1Id = DB.insertNoteGroupCategoryToDB("CATEGORY1", "#00ff00");

        DB.insertGroupHasToDB(group3Id, groupCategory1Id);

        ResultSet rs = DB.readGroupHas(group3Id);
        assertTrue(rs.next());
        assertEquals(group3Id, rs.getInt("group_id"));
        assertEquals(groupCategory1Id, rs.getInt("group_category_id"));
        assertFalse(rs.next());

        ResultSet rs2 = DB.readGroupHas();
        assertTrue(rs2.next());
        assertEquals(group3Id, rs2.getInt("group_id"));
        assertEquals(groupCategory1Id, rs2.getInt("group_category_id"));
        assertFalse(rs2.next());

        DB.deleteGroupHasFromDB(group3Id, groupCategory1Id);
        DB.deleteGroupFromDB(group3Id);
        DB.deleteNoteGroupCategoryFromDB(groupCategory1Id);
    }
}