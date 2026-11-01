package glowstone.model;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class NoteTest {

    @Test
    void testNoteCreation() {
        Note note = new Note("Test Title");

        assertEquals("Test Title", note.getTitle());
    }

    @Test
    void testSetTitle() {
        Note note = new Note("Initial Title");

        note.setTitle("Updated Title");

        assertEquals("Updated Title", note.getTitle());
    }

    @Test
    void testSetContent() {
        Note note = new Note("Test Title");

        note.setContent("Test Content");

        assertEquals("Test Content", note.getContent());
    }

    @Test
    void testGetId() {
        Note note1 = new Note("Note 1");
        Note note2 = new Note("Note 2");
        note1.setId(1);
        note2.setId(2);

        assertNotEquals(note1.getId(), note2.getId()); //TEMP should take id from db
    }

    @Test
    void testGetTitle() {
        Note note1 = new Note("Note 1");
        Note note2 = new Note("Note 2");

        assertEquals("Note 1", note1.getTitle());
        assertEquals("Note 2", note2.getTitle());
    }

    @Test
    void testGetContent() {
        Note note1 = new Note("Note 1");
        Note note2 = new Note("Note 2");

        note1.setContent("Test Content1");
        note2.setContent("Test Content2");

        assertEquals("Test Content1", note1.getContent());
        assertEquals("Test Content2", note2.getContent());
    }

    @Test
    void setTitleUpdatesTitle() {
        Note note = new Note("Initial title");

        note.setTitle("Updated title");

        assertEquals("Updated title", note.getTitle());
    }

    @Test
    void setContentUpdatesContent() {
        Note note = new Note("Test title");

        note.setContent("Initial content");
        note.setContent("Updated content");

        assertEquals("Updated content", note.getContent());
    }

    @Test
    void setParentIdUpdatesParentId() {
        Note note = new Note("Child note");

        note.setParentId(42);

        assertEquals(42, note.getParentId());
    }

    @Test
    void newNoteStartsWithNullContent() {
        Note note = new Note("Test title");

        assertNull(note.getContent());
    }

    @Test
    void addTagAddsTag() {
        Note note = new Note("Test title");
        NoteTag tag = new NoteTag(1, "test tag", "#00ff00");

        note.addTag(tag);

        assertEquals(1, note.getTags().size());
        assertEquals(tag, note.getTags().get(0));
    }

    @Test
    void getTagsReturnsTags() {
        Note note = new Note("Test title");
        NoteTag tag1 = new NoteTag(1, "Test tag1", "#00ff00");
        NoteTag tag2 = new NoteTag(2, "Test tag2", "#ff0000");

        note.addTag(tag1);
        note.addTag(tag2);

        assertEquals(List.of(tag1, tag2), note.getTags());
    }

    @Test
    void removeTagRemovesTag() {
        Note note = new Note("Test title");
        NoteTag tag1 = new NoteTag(1, "Test tag1", "#00ff00");
        NoteTag tag2 = new NoteTag(2, "Test tag2", "#ff0000");
        note.addTag(tag1);
        note.addTag(tag2);

        note.removeTag(tag1);

        assertEquals(List.of(tag2), note.getTags());
    }
}