package ca.concordia.patterns.state.edit;

import ca.concordia.gameengine.GameEngine;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

/**
 * This class checks the method implementation of PreLoad class
 * and tests whether expected output of these methods are equal to actual outputs or not
 */
public class PreloadTest {
    GameEngine d_ge = new GameEngine();
    String[] str = {"editContinent"};
    String[] str1 = {"editNeighbour"};
    String[] str2 = {"editMap"};

    /**
     * This is the test method that checks editContinent method's expected and actual output
     */
    @Test
    public void editContinent() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Preload pr = new Preload(d_ge);
        pr.editContinent(str);
        String ex = "Invalid command in statePreload" + System.lineSeparator();
        assertEquals(ex, outContent.toString());
    }

    /**
     * This method check the expected and actual output of editNeighbour method
     */

    @Test
    public void editNeighbour() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Preload pr = new Preload(d_ge);
        pr.editNeighbour(str1);
        String ex = "Invalid command in statePreload" + System.lineSeparator();
        assertEquals(ex, outContent.toString());
    }

    /**
     * This method check the expected and actual output of editMap method
     */
    @Test
    public void editMap() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Preload pr = new Preload(d_ge);
        pr.editMap(str2);
        String ex = "INCOMPLETE COMMAND, create an in-memory map file from scratch" + System.lineSeparator() +
                " No continents available in the map " + System.lineSeparator();
        assertEquals(ex, outContent.toString());
    }
}