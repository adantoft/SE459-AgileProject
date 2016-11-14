package general;

import vacuum.CleanSweep;
import map.FloorPlan;
import map.TestMap;

public class PresentationMap {

	public static void main(String[] args) {
		
		FloorPlan mainMap = TestMap.buildExampleFloorPlan();
		CleanSweep cs = CleanSweep.getInstance();
//		cs.enableChargeDebugMode();
//		cs.enableDirtDebugMode();
		cs.setTile(mainMap.getTile(0, 0));	// Sets CS on charging base
		cs.clean();
	}

}
