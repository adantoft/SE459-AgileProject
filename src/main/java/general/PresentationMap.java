package general;

import com.esotericsoftware.yamlbeans.YamlException;
import map.FloorPlan;
import map.UploadFloorPlan;
import vacuum.CleanSweep;

import java.io.FileNotFoundException;

public class PresentationMap {

	public static void main(String[] args) throws YamlException, FileNotFoundException, DataValidationException {
        UploadFloorPlan floorPlans = new UploadFloorPlan();
        floorPlans.loadFloorPlans();

        FloorPlan mainMap = floorPlans.getFloorPlan("Sample Floor Plan");

		CleanSweep cs = CleanSweep.getInstance();
		cs.setTile(mainMap.getTile(0, 0));	// Sets CS on charging base
		cs.clean();
	}

}
