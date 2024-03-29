package ca.concordia.patterns.state.end;

import ca.concordia.gameengine.GameEngine;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

/**
 * This class checks the method implementation of End class
 */
public class EndTest {

    GameEngine d_ge = new GameEngine();
    String[] d_str = {"editcontinent -add asia 1"};

    /**
     * This is the test method that checks editContinent method's expected and actual output
     */
    @Test
    public void editContinent() {
        ByteArrayOutputStream l_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_OutContent));

        End l_Et = new End(d_ge);
        l_Et.editContinent(d_str);
        String l_Ex = "Invalid command in state";
        assertEquals(true, l_OutContent.toString().contains(l_Ex));

    }

    /**
     * Test endGame in the endphase where process is being killed
     */
    @Test
    public void endGame() {
        ByteArrayOutputStream l_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_OutContent));

        End et = new End(d_ge);
        // TODO: hard to test the endgame when the process itlsef is being self-killed using System.exit()

        String ex = "Game has ended !";

    }
}
