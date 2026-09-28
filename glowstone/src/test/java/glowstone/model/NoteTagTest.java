package glowstone.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoteTagTest {

	@Test
	void testNoteTagCreation() {
		NoteTag tag = new NoteTag(2, "test tag", "#0000ff");

		assertEquals(2, tag.getId());
		assertEquals("test tag", tag.getName());
		assertEquals("#0000ff", tag.getColor());
	}

	@Test
	void testSetName() {
		NoteTag tag = new NoteTag(2, "test tag", "#0000ff");

		tag.setName("updated test tag");

		assertEquals("updated test tag", tag.getName());
	}

	@Test
	void testSetColor() {
		NoteTag tag = new NoteTag(2, "test tag", "#0000ff");

		tag.setColor("#00ff00");

		assertEquals("#00ff00", tag.getColor());
	}
}
