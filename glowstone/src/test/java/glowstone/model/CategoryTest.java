package glowstone.model;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CategoryTest {
    
    @Test
    void testCategoryCreation() {
        Category category = new Category("Test name");

        assertEquals("Test name", category.getName());
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
//keeping these ID related tests with the fake IDs in the model test classes for consistency
    @Test
    void testGetId() {
        Category category1 = new Category("Test name1");
        Category category2 = new Category("Test name2");
        category1.setId(1);
        category2.setId(2);

        assertNotEquals(category1.getId(), category2.getId());
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

    @Test
    void newCategoryStartsWithNoNotes() {
        Category category = new Category("Test name");

        assertEquals(0, category.getNotes().size());
    }

    @Test
    void removeNotesRemovesNotes() {
        Category category = new Category("Test name");
        Note note = new Note("Test title");

        category.addNotes(note);

        assertEquals(1, category.getNotes().size());

        category.removeNotes(note);

        assertEquals(0, category.getNotes().size());
    }

    @Test
    void removeMissingNoteDoesNothing() {
        Category category = new Category("Test name");
        Note realNote = new Note("Real");
        Note fakeNote = new Note("Fake");
        realNote.setId(1);
        fakeNote.setId(2);

        category.addNotes(realNote);
        category.removeNotes(fakeNote);

        assertEquals(1, category.getNotes().size());
        assertEquals(realNote, category.getNotes().get(0));
    }

    @Test
    void notesPreserveInsertionOrder() {
        Category category = new Category("Test name");
        Note note1 = new Note("First note");
        Note note2 = new Note("Second note");

        category.addNotes(note1);
        category.addNotes(note2);

        assertEquals(note1, category.getNotes().get(0));
        assertEquals(note2, category.getNotes().get(1));
    }

    @Test
    void addNotesSetsNoteParentId() {
        Category category = new Category("Test name");
        Note note = new Note("Test title");

        category.addNotes(note);

        assertEquals(category.getId(), note.getParentId());
    }

    @Test
    void setParentIdUpdatesParentId() {
        Category category = new Category("Test name");

        category.setParentId(10);

        assertEquals(10, category.getParentId());
    }

    @Test
    void addTagAddsTag() {
        Category category = new Category("Test name");
        CategoryTag tag = new CategoryTag(1, "test tag", "#00ff00");

        category.addTag(tag);

        assertEquals(1, category.getTags().size());
        assertEquals(tag, category.getTags().get(0));
    }

    @Test
    void getTagsReturnsTags() {
        Category category = new Category("Test name");
        CategoryTag tag1 = new CategoryTag(1, "Test tag1", "#00ff00");
        CategoryTag tag2 = new CategoryTag(2, "Test tag2", "#ff0000");

        category.addTag(tag1);
        category.addTag(tag2);

        assertEquals(List.of(tag1, tag2), category.getTags());
    }

    @Test
    void removeTagRemovesTag() {
        Category category = new Category("Test name");
        CategoryTag tag1 = new CategoryTag(1, "Test tag1", "#00ff00");
        CategoryTag tag2 = new CategoryTag(2, "Test tag2", "#ff0000");
        category.addTag(tag1);
        category.addTag(tag2);

        category.removeTag(tag1);

        assertEquals(List.of(tag2), category.getTags());
    }

}
