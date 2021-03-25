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
    String[] d_Str = {"editContinent"};
    String[] d_Str1 = {"editNeighbour"};
    String[] d_Str2 = {"editMap"};

    /**
     * This is the test method that checks editContinent method's expected and actual output
     */
    @Test
    public void editContinent() {
        ByteArrayOutputStream l_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_OutContent));

        Preload l_Pr = new Preload(d_ge);
        l_Pr.editContinent(d_Str);
        String l_Ex = "Invalid command in statePreload" + System.lineSeparator();
        assertEquals(l_Ex, l_OutContent.toString());
    }

    /**
     * This method check the expected and actual output of editNeighbour method
     */

    @Test
    public void editNeighbour() {
        ByteArrayOutputStream l_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_OutContent));

        Preload l_Pr = new Preload(d_ge);
        l_Pr.editNeighbour(d_Str1);
        String l_Ex = "Invalid command in statePreload" + System.lineSeparator();
        assertEquals(l_Ex, l_OutContent.toString());
    }

    /**
     * This method check the expected and actual output of editMap method
     */
    @Test
    public void editMap() {
        ByteArrayOutputStream l_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_OutContent));

        Preload l_Pr = new Preload(d_ge);
        l_Pr.editMap(d_Str2);
        String l_Ex = "INCOMPLETE COMMAND, create an in-memory map file from scratch" + System.lineSeparator() +
                " No continents available in the map " + System.lineSeparator();
        assertEquals(l_Ex, l_OutContent.toString());
    }

}