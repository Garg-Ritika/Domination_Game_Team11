package ca.concordia.patterns.state.play;

import ca.concordia.gameengine.GameEngine;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

public class OrderCreationPhaseTest {
    GameEngine d_ge = new GameEngine();


    @Test
    public void reinforce() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        OrderCreationPhase l_or = new OrderCreationPhase(d_ge);
        l_or.reinforce();
        String l_ex = "Invalid command in state";
        assertEquals(true, outContent.toString().contains(l_ex));
    }

    @Test
    public void createOrder() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        OrderCreationPhase l_or = new OrderCreationPhase(d_ge);
        l_or.createOrder();
        String l_ex = "start of order creation";
        assertEquals(true, outContent.toString().contains(l_ex));
    }

    @Test
    public void executeOrder() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        OrderCreationPhase l_or = new OrderCreationPhase(d_ge);
        l_or.executeOrder();
        String l_ex = "Invalid command in state";
        ;
        assertEquals(true, outContent.toString().contains(l_ex));
    }

    @Test
    public void next() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        OrderCreationPhase l_or = new OrderCreationPhase(d_ge);
        l_or.next();
        String l_ex = "setting order execution phase ";
        assertEquals(true, outContent.toString().contains(l_ex));
    }
}
