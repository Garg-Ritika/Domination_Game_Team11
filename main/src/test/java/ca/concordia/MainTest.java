package ca.concordia;

import ca.concordia.dao.*;
import ca.concordia.gameengine.GameEngineTest;
import ca.concordia.mapworks.MapEditorTest;
import ca.concordia.mapworks.ValidateMapTest;
import ca.concordia.patterns.command.*;
import ca.concordia.patterns.observer.LogEntryBufferTest;
import ca.concordia.patterns.state.edit.PostLoadTest;
import ca.concordia.patterns.state.edit.PreloadTest;
import ca.concordia.patterns.state.end.EndTest;
import ca.concordia.patterns.state.play.*;
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
        GraphTest.class,
        MapTest.class,
        PlayerTest.class,
        GameEngineTest.class,
        MapEditorTest.class,
        ValidateMapTest.class,
        AdvanceTest.class,
        AirliftTest.class,
        BlockadeTest.class,
        BombTest.class,
        DeployTest.class,
        DiplomacyTest.class,
        LogEntryBufferTest.class,
        PostLoadTest.class,
        PreloadTest.class,
        EndTest.class,
        OrderCreationPhaseTest.class,
        OrderExecutionPhaseTest.class,
        PlaySetupTest.class,
        PlayTest.class,
        ReinforcementPhaseTest.class
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