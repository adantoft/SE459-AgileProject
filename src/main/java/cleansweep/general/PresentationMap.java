package cleansweep.general;

import com.esotericsoftware.yamlbeans.YamlException;
import simulator.map.FloorPlan;
import simulator.map.UploadFloorPlan;
import cleansweep.vacuum.CleanSweep;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PresentationMap {

	public static void main(String[] args) throws YamlException, FileNotFoundException, DataValidationException, IOException {
		UploadFloorPlan floorPlans = new UploadFloorPlan();
        floorPlans.loadFloorPlans();

        FloorPlan mainMap = floorPlans.getFloorPlan("Sample Floor Plan");

		CleanSweep cs = CleanSweep.getInstance();
		cs.setTile(mainMap.getTile(0, 0));	// Sets CS on charging base
		cs.clean();
	}

}
