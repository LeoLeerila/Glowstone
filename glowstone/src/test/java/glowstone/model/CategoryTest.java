package glowstone.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {
    
    @Test
    void testCategoryCreation() {
        Category category = new Category("Test name");

        assertEquals("Test name", category.getName());
        assertEquals(7, category.getId()); //TEMP should take id from db
    }

    @Test
    void testSetName() {
        Category category = new Category("Initial name");

        category.setName("Updated name");

        assertEquals("Updated name", category.getName());
    }

    @Test
    void testSetId() {
        Category category = new Category("Test name");
        category.setId(1);
        assertEquals(1, category.getId());
    }

    @Test
    void testSetNotes() {
        Category category = new Category("Test name");
        Note note = new Note("Test Title");

        category.addNotes(note);

        assertEquals(1, category.getNotes().size());
        assertEquals(note, category.getNotes().get(0));
    }

    @Test
    void testGetName() {
        Category category1 = new Category("Test name1");
        Category category2 = new Category("Test name2");

        assertEquals("Test name1", category1.getName());
        assertEquals("Test name2", category2.getName());
    }

    @Test
    void testGetId() {
        Category category1 = new Category("Test name1");
        Category category2 = new Category("Test name2");

        assertEquals(1, category1.getId());
        assertEquals(2, category2.getId());
    }

    @Test
    void testGetNotes() {
        Category category = new Category("Test name");
        Note note1 = new Note("Test Title1");
        Note note2 = new Note("Test Title2");

        category.addNotes(note1);
        category.addNotes(note2);

        assertEquals(2, category.getNotes().size());
        assertEquals(note1, category.getNotes().get(0));
        assertEquals(note2, category.getNotes().get(1));
    }

}
