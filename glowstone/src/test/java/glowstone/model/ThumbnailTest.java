package glowstone.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThumbnailTest {

	@Test
	void testThumbnailCreation() {
		byte[] data = {1, 2, 3};
		Thumbnail thumbnail = new Thumbnail(7, data);

		assertEquals(7, thumbnail.getId());
		assertArrayEquals(data, thumbnail.getData());
	}
}
