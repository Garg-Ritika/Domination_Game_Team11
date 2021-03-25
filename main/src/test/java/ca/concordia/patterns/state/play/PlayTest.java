package ca.concordia.patterns.state.play;

import ca.concordia.gameengine.GameEngine;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

/**
 * This class checks the method implementation of Play class
 */
public class PlayTest {

    GameEngine d_ge = new GameEngine();
    String[] d_str = {"savemap"};

    /**
     * This is the test method that checks saveMap method's expected and actual output
     */
    @Test
    public void saveMap() {
        ByteArrayOutputStream l_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_OutContent));

        Play l_P = new Play(d_ge);
        l_P.saveMap(d_str);
        String l_Ex = "Invalid command in state";
        assertEquals(true, l_OutContent.toString().contains(l_Ex));
    }

    /**
     * This method is used to test if map is shown successfully
     */
    @Test
    public void showMap() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Play l_P = new Play(d_ge);
        l_P.showMap();
        String ex = "show game command received ";
        assertEquals(true, outContent.toString().contains(ex));
    }
}
