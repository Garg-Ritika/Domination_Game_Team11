package ca.concordia.gameengine;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

/**
 * Test class for Game Loader Class
 */
public class GameLoaderTest {

    GameEngine d_GameEngine;
    GameLoader d_GameLoader;

    /**
     * This is the method to be run before test method. It initializes the GameLoader class object
     */
    @Before
    public void before() {
        d_GameEngine = new GameEngine();
        d_GameLoader = new GameLoader(d_GameEngine);
    }

    /**
     * To check if the file is loaded successfully and map exists to resume where left last
     * using assertTrue
     */
    @Test
    public void loadGameFile() {
        String currentPath = System.getProperty("user.dir");
        File l_SavedGameFile = new File(currentPath + File.separator + "saved-game1.txt");
        d_GameLoader.loadGameFile(l_SavedGameFile);
        assertTrue( d_GameEngine.getMap() != null);
    }
}
