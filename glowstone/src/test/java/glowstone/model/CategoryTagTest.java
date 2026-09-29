package glowstone.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryTagTest {

	@Test
	void testCategoryTagCreation() {
		CategoryTag tag = new CategoryTag(1, "test tag", "#ff0000");

		assertEquals(1, tag.getId());
		assertEquals("test tag", tag.getName());
		assertEquals("#ff0000", tag.getColor());
	}

	@Test
	void testSetName() {
		CategoryTag tag = new CategoryTag(1, "test tag", "#ff0000");

		tag.setName("updated test tag");

		assertEquals("updated test tag", tag.getName());
	}

	@Test
	void testSetColor() {
		CategoryTag tag = new CategoryTag(1, "test tag", "#ff0000");

		tag.setColor("#00ff00");

		assertEquals("#00ff00", tag.getColor());
	}
}
