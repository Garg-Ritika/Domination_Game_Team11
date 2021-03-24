package ca.concordia.patterns.state.play;

import ca.concordia.gameengine.GameEngine;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

public class OrderExecutionPhaseTest {

    GameEngine d_ge = new GameEngine();


    @Test
    public void reinforce() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        OrderExecutionPhase l_oe = new OrderExecutionPhase(d_ge);
        l_oe.reinforce();
        String l_ex = "Invalid command in ";
        ;
        assertEquals(true, outContent.toString().contains(l_ex));
    }

    @Test
    public void createOrder() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        OrderExecutionPhase l_oe = new OrderExecutionPhase(d_ge);
        l_oe.createOrder();
        String l_ex = "Invalid command in state";
        assertEquals(true, outContent.toString().contains(l_ex));
    }

    @Test
    public void executeOrder() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        OrderExecutionPhase l_oe = new OrderExecutionPhase(d_ge);
        l_oe.executeOrder();
        String l_ex = "execute order";
        ;
        assertEquals(true, outContent.toString().contains(l_ex));
    }

    @Test
    public void next() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        OrderExecutionPhase l_oe = new OrderExecutionPhase(d_ge);
        l_oe.next();
        String l_ex = "setting reinforcement phase";
        assertEquals(true, outContent.toString().contains(l_ex));
    }
}
