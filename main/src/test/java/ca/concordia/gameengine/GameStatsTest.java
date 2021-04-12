package ca.concordia.gameengine;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameStatsTest {
    GameStats gs;

    @Before
    public void before() {
        gs = new GameStats("abc", "12", "def");
    }

    @Test
    public void getMapName() {
        assertEquals(true, gs.getMapName().contains("abc"));
    }

    @Test
    public void getGameNumber() {
        assertEquals(true, gs.getGameNumber().contains("12"));
    }

    @Test
    public void getResult() {
        assertEquals(true, gs.getResult().contains("def"));
    }

}