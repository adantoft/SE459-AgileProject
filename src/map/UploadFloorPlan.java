package map;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Created by adnandossaji on 11/12/16.
 */
public class UploadFloorPlan {
    private static UploadFloorPlan instance;
    private HashMap<String, FloorPlan> mapHash  = new HashMap<String, FloorPlan>();
    private ArrayList<String> mapFileList = new ArrayList<String>();

    private void loadYamlMap() throws YamlException, FileNotFoundException {
        for (String string : mapFileList) {

            YamlReader reader = new YamlReader(new FileReader("./src/map/upload/" + string));

            Object object = reader.read();
            Map obj = (Map) object;

            String[] dimensions = obj.get("dimensions").toString().split("x");

            FloorPlan floorPlan = new FloorPlan(Integer.parseInt(dimensions[0]), Integer.parseInt(dimensions[1]));

            List rooms = (List) obj.get("rooms");

            for (Object room : rooms) {
                System.out.println(room);
            }

            List doorways = (List) obj.get("doorways");

            for (Object doorway : doorways) {
                System.out.println(doorway);
            }

            mapHash.put((String) obj.get("name"), floorPlan);
            System.out.println(mapHash.get(obj.get("name")));


        }
    }

    public void loadFloorPlans() throws YamlException, FileNotFoundException {
        File[] listOfFiles = new File("./src/map/upload/").listFiles();

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
