package ca.concordia.gameengine;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class GameLoaderTest {

    GameEngine d_GameEngine;
    GameLoader d_GameLoader;

    @Before
    public void before() {
        d_GameEngine = new GameEngine();
        d_GameLoader = new GameLoader(d_GameEngine);
    }

    @Test
    public void loadGameFile() {
        String currentPath = System.getProperty("user.dir");
        File l_SavedGameFile = new File(currentPath + File.separator + "saved-game1.txt");
        d_GameLoader.loadGameFile(l_SavedGameFile);
        assertTrue( d_GameEngine.getMap() != null);
    }
}
