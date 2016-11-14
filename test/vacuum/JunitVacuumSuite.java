package vacuum;


import map.FloorPlanTest;
import map.PointTest;
import map.SpaceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        NavigationTest.class,
        CleanSweepTest.class
})

public class JunitVacuumSuite {
}