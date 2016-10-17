
import map.JunitMapSuite;
import floor.JunitTileSuite;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {

        //Tile Suite
        Result resultTile = JUnitCore.runClasses(JunitTileSuite.class);

        for (Failure failure : resultTile.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println("\nTile Tests completed:  " + resultTile.wasSuccessful());



        //Map Suite
        Result result = JUnitCore.runClasses(JunitMapSuite.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println("\nMap Tests:  " + result.wasSuccessful());
    }

}
