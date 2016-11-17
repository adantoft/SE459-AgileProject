package simulator.map;

import org.junit.Test;

import static org.junit.Assert.*;

public class SpaceTest {

	@Test
	public void SpaceBuilderTest() throws Exception {

		Space s1;
		Space s2;
		s1 = new Space(new Point(0, 0), new Point(3, 4));
		s2 = new Space(new Point(10, 5), new Point(8, 7));
		Point p1 = new Point(0, 0);
		Point p2 = new Point(3, 4);

		// checking bottomLeft and bottomRight are filled correctly.

		assertEquals(p1.getX(), s1.getBottomLeft().getX());
		assertEquals(p1.getY(), s1.getBottomLeft().getY());
		assertEquals(p2.getX(), s1.getTopRight().getX());
		assertEquals(p2.getY(), s1.getTopRight().getY());

		// checking point is not null
		assertNotNull(s1);
		assertNotNull(s2);

		// check not same
		assertFalse(s1 == s2);

	}

}