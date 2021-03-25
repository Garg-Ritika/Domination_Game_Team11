package ca.concordia.patterns.state.play;

import ca.concordia.gameengine.GameEngine;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

/**
 * This class checks the method implementation of Reinforcement phase class
 */
public class ReinforcementPhaseTest {

    GameEngine d_ge = new GameEngine();

    /**
     * This method is used to test if after successfull turn player has reached reinforcement phase successfully
     */
    @Test
    public void reinforce() {
        ByteArrayOutputStream l_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_OutContent));

        ReinforcementPhase l_rp = new ReinforcementPhase(d_ge);
        l_rp.reinforce();
        String l_ex = "reinforcing ";
        assertEquals(true, l_OutContent.toString().contains(l_ex));
    }

    /**
     * This method checks if invalid command message is shown to the user
     */
    @Test
    public void createOrder() {
        ByteArrayOutputStream L_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(L_OutContent));

        ReinforcementPhase l_rp = new ReinforcementPhase(d_ge);
        l_rp.createOrder();
        String l_ex = "Invalid command in ";
        assertEquals(true, L_OutContent.toString().contains(l_ex));
    }

    /**
     * This method checks if invalid command message is shown to the user
     */
    @Test
    public void executeOrder() {
        ByteArrayOutputStream l_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_OutContent));

        ReinforcementPhase l_rp = new ReinforcementPhase(d_ge);
        l_rp.executeOrder();
        String l_ex = "Invalid command in state";
        assertEquals(true, l_OutContent.toString().contains(l_ex));
    }

    /**
     * THis method is used to test if after successful turn player successfully moves to next phase
     */
    @Test
    public void next() {
        ByteArrayOutputStream l_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_OutContent));

        ReinforcementPhase l_rp = new ReinforcementPhase(d_ge);
        l_rp.next();
        String l_ex = "setting order creation phase ";
        assertEquals(true, l_OutContent.toString().contains(l_ex));
    }
}
