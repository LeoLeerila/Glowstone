package glowstone.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoteTest {

    @Test
    void testNoteCreation() {
        Note note = new Note("Test Title");

        assertEquals("Test Title", note.getTitle());

        assertEquals(1, note.getId());
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

        assertEquals(1, note1.getId());
        assertEquals(2, note2.getId());
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
}