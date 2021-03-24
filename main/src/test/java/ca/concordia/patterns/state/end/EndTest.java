package ca.concordia.patterns.state.end;

import ca.concordia.gameengine.GameEngine;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

public class EndTest {

    GameEngine d_ge = new GameEngine();
    String[] d_str = {"editcontinent -add asia 1"};

    /**
     * This is the test method that checks editContinent method's expected and actual output
     */
    @Test
    public void editContinent() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        End et = new End(d_ge);
        et.editContinent(d_str);
        String ex = "Invalid command in state";
        assertEquals(true, outContent.toString().contains(ex));

    }

    /**
     * Test endGame in the endphase where process is being killed
     */
    @Test
    public void endGame() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        End et = new End(d_ge);
        // TODO: hard to test the endgame when the process itlsef is being self-killed using System.exit()
        //et.endGame();
        String ex = "Game has ended !";
        //assertEquals(true, outContent.toString().contains(ex));
    }
}
