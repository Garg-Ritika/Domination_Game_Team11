package ca.concordia.patterns.state.play;

import ca.concordia.gameengine.GameEngine;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

/**
 * This class checks the method implementation of Play setup class
 */
public class PlaySetupTest {
    GameEngine d_ge = new GameEngine();
    String[] d_str = {"loadmap", "abc "};
    String[] d_str1 = {"gameplayer", "-add", "player1"};

    /**
     * This method is used to test if map is loaded successfully before starting the game play
     */
    @Test
    public void loadMap() {
        System.out.println("PlaySetupTest : loadmap ");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        PlaySetup l_ps = new PlaySetup(d_ge);
        l_ps.loadMap(d_str);
        String l_ex = "load map to start game .";
        assertEquals(true, outContent.toString().contains(l_ex));



    }

    /**
     * This method is used to test if payers are set successfully
     */
    @Test
    public void setPlayers() {
        System.out.println("PlaySetupTest : setplayers");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        PlaySetup l_ps = new PlaySetup(d_ge);
        l_ps.setPlayers(d_str1);
        String l_ex = "gameplayer command received";
        assertEquals(true, outContent.toString().contains(l_ex));

    }

    /**
     * This method is used to test if countries are assigned successfully
     */
    @Test
    public void assignCountries() {
        System.out.println("PlaySetupTest : assigncountries ");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        PlaySetup l_ps = new PlaySetup(d_ge);
        l_ps.assignCountries();
        String l_ex = "assigncountries command received";
        assertEquals(true, outContent.toString().contains(l_ex));

    }

}
