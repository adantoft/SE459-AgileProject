package map;

import org.junit.Test;

import java.io.FileNotFoundException;
import static org.junit.Assert.*;

/**
 * Created by adnandossaji on 11/12/16.
 */

public class UploadFloorPlanTest {

	@Test
	public void UploadFloorPlanTest() throws FileNotFoundException, Exception {
		UploadFloorPlan floorPlans = new UploadFloorPlan();
		floorPlans.loadFloorPlans();

		FloorPlan floorPlan = floorPlans.getFloorPlan("Sample Floor Plan");
		assertTrue(floorPlan != null);

	}

}
