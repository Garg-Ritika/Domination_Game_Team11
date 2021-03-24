package ca.concordia.patterns.state.play;

import ca.concordia.gameengine.GameEngine;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

public class PlayTest {

    GameEngine d_ge = new GameEngine();
    String[] d_str = {"savemap"};

    /**
     * This is the test method that checks saveMap method's expected and actual output
     */
    @Test
    public void saveMap() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Play p = new Play(d_ge);
        p.saveMap(d_str);
        String ex = "Invalid command in state";
        assertEquals(true, outContent.toString().contains(ex));
    }

    @Test
    public void showMap() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Play p = new Play(d_ge);
        p.showMap();
        String ex = "show game command received ";
        assertEquals(true, outContent.toString().contains(ex));
    }
}
