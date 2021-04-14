package ca.concordia.gameengine;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class file for Game Stats Class
 */
public class GameStatsTest {
    GameStats gs;

    /**
     * This is the method to be run before test method. It initializes the Game Stats class object
     */
    @Before
    public void before() {
        gs = new GameStats("abc", "12", "def");
    }

    /**
     * To test if the map file name is retrieved successfully
     * using assertEquals
     */
    @Test
    public void getMapName() {
        assertEquals(true, gs.getMapName().contains("abc"));
    }

    /**
     * To test if the game number is retrieved successfully
     * using assertEquals
     */
    @Test
    public void getGameNumber() {
        assertEquals(true, gs.getGameNumber().contains("12"));
    }

    /**
     * To test if the result is printed successfully and the method run is success
     * using assertEquals
     */
    @Test
    public void getResult() {
        assertEquals(true, gs.getResult().contains("def"));
    }

}