package ca.concordia;

import ca.concordia.controller.game.GameEngineTest;
import ca.concordia.controller.game.PlayerActionsTest;
import ca.concordia.controller.map.MapEditorTest;
import ca.concordia.controller.map.ValidateMapTest;
import ca.concordia.model.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BorderTest.class,
        ContinentTest.class,
        CountryTest.class,
        OrderTest.class,
        PlayerTest.class,
        GameEngineTest.class,
        PlayerActionsTest.class,
        MapEditorTest.class,
        ValidateMapTest.class
})
public class MainTest extends TestCase {

    public MainTest(String testname) {

        super(testname);
    }

    public static Test suite() {

        return new TestSuite(MainTest.class);

    }

    public void testApp() {

        assertTrue(true);
    }

}