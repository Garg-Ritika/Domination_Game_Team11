package ca.concordia.patterns.state.play;

import ca.concordia.gameengine.GameEngine;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

public class ReinforcementPhaseTest {

    GameEngine d_ge = new GameEngine();


    @Test
    public void reinforce() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ReinforcementPhase l_rp = new ReinforcementPhase(d_ge);
        l_rp.reinforce();
        String l_ex = "reinforcing ";
        assertEquals(true, outContent.toString().contains(l_ex));
    }

    @Test
    public void createOrder() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ReinforcementPhase l_rp = new ReinforcementPhase(d_ge);
        l_rp.createOrder();
        String l_ex = "Invalid command in ";
        assertEquals(true, outContent.toString().contains(l_ex));
    }

    @Test
    public void executeOrder() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ReinforcementPhase l_rp = new ReinforcementPhase(d_ge);
        l_rp.executeOrder();
        String l_ex = "Invalid command in state";
        ;
        assertEquals(true, outContent.toString().contains(l_ex));
    }

    @Test
    public void next() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ReinforcementPhase l_rp = new ReinforcementPhase(d_ge);
        l_rp.next();
        String l_ex = "setting order creation phase ";
        assertEquals(true, outContent.toString().contains(l_ex));
    }
}
