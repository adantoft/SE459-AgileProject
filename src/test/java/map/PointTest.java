package map;

import org.junit.Test;
import static org.junit.Assert.*;

public class PointTest {

	@Test
	public void PointBuilderTest() throws Exception {
		Point p1;
		Point p2;
		p1 = new Point(2, 3);
		p2 = new Point(4, 5);

		// checking point x and y are filled correctly.
		assertEquals(2, p1.getX());
		assertEquals(3, p1.getY());

		// checking point is not null
		assertNotNull(p1);
		assertNotNull(p2);

		// check not same
		assertFalse(p1 == p2);
	}

}
