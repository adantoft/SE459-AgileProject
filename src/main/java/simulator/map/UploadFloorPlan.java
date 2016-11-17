package simulator.map;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import cleansweep.floor.Tile;
import cleansweep.general.DataValidationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by adnandossaji on 11/12/16.
 */
public class UploadFloorPlan {
    private static UploadFloorPlan instance;
    private HashMap<String, FloorPlan> mapHash  = new HashMap<String, FloorPlan>();
    private ArrayList<String> mapFileList = new ArrayList<String>();

    private void loadYamlMap() throws YamlException, FileNotFoundException, DataValidationException {
        for (String string : mapFileList) {

            YamlReader reader = new YamlReader(new FileReader("./src/main/java/simulator/map/upload/" + string));

            Object object = reader.read();
            Map obj = (Map) object;

            String[] dimensions = obj.get("dimensions").toString().split("x");

            FloorPlan floorPlan = new FloorPlan(Integer.parseInt(dimensions[0]), Integer.parseInt(dimensions[1]));

            List rooms = (List) obj.get("rooms");

            for (Object room : rooms) {
                String roomName = (((HashMap<String, String>) room).get("name"));
                String[] roomDimensions = (((HashMap<String, String>) room).get("dimensions")).toString().split("x");
                String roomFloor = (((HashMap<String, String>) room).get("floor"));
                String[] roomBottomLeftCoord = (((HashMap<String, String>) room).get("bottom-left")).toString().split(",");

                Point bottomLeft = new Point(Integer.parseInt(roomBottomLeftCoord[0]), Integer.parseInt(roomBottomLeftCoord[1]));
                Point topRight = new Point((Integer.parseInt(roomDimensions[1]) - 1) + Integer.parseInt(roomBottomLeftCoord[0]), (Integer.parseInt(roomDimensions[0]) - 1) + Integer.parseInt(roomBottomLeftCoord[1]));

                Tile.Floor type = null;

                if (roomFloor.equals("Bare")) {
                    type = Tile.Floor.BARE;
                } else if (roomFloor.equals("Low")) {
                    type = Tile.Floor.LOW;
                } else if (roomFloor.equals("High")) {
                    type = Tile.Floor.HIGH;
                } else {
                    throw new DataValidationException("ERROR: Invalid direction");
                }

                if (type != null) { floorPlan.setSpace(0, new Space(bottomLeft, topRight), type); }
                else { throw new DataValidationException("ERROR: Invalid CleanSweep.floor"); }

            }

            List doorways = (List) obj.get("doorways");

            for (Object doorway : doorways) {
                String[] tileOneList = (((HashMap<String, String>) doorway).get("tile-one")).toString().split(",");
                String[] tileTwoList = (((HashMap<String, String>) doorway).get("tile-two")).toString().split(",");

                Point tileOne = new Point(Integer.parseInt(tileOneList[0]), Integer.parseInt(tileOneList[1]));
                Point tileTwo = new Point(Integer.parseInt(tileTwoList[0]), Integer.parseInt(tileTwoList[1]));

                floorPlan.setDoorway(tileOne, tileTwo);
            }

            String[] chargingStation= obj.get("charging-station").toString().split(",");

            floorPlan.getTile(Integer.parseInt(chargingStation[0]), Integer.parseInt(chargingStation[1])).setRole(Tile.Role.CHARGE);

            mapHash.put((String) obj.get("name"), floorPlan);

        }
    }

    public void loadFloorPlans() throws YamlException, FileNotFoundException, DataValidationException, IOException {
        File[] listOfFiles = new File("./src/main/java/simulator/map/upload/").listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                mapFileList.add(listOfFiles[i].getName());
            }
        }

        loadYamlMap();

    }

    public FloorPlan getFloorPlan(String string) {
        return mapHash.get(string);
    }
}
