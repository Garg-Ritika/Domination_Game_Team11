package ca.concordia.patterns.state.play;

import ca.concordia.gameengine.GameEngine;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

/**
 * This class checks the method implementation of Order Creation phase class
 */
public class OrderCreationPhaseTest {
    GameEngine d_ge = new GameEngine();


    /**
     * This method checks if invalid command message is shown to the user
     */
    @Test
    public void reinforce() {
        ByteArrayOutputStream l_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_OutContent));

        OrderCreationPhase l_or = new OrderCreationPhase(d_ge);
        l_or.reinforce();
        String l_ex = "Invalid command in state";
        assertEquals(true, l_OutContent.toString().contains(l_ex));
    }

    /**
     * This method tests if all the reinforcements armies have been placed and order is created successfully
     */
    @Test
    public void createOrder() {
        ByteArrayOutputStream l_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_OutContent));

        OrderCreationPhase l_or = new OrderCreationPhase(d_ge);
        l_or.createOrder();
        String l_ex = "start of order creation";
        assertEquals(true, l_OutContent.toString().contains(l_ex));
    }

    /**
     * This method tests if invalid command message is shown to the user
     */
    @Test
    public void executeOrder() {
        ByteArrayOutputStream l_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_OutContent));

        OrderCreationPhase l_or = new OrderCreationPhase(d_ge);
        l_or.executeOrder();
        String l_ex = "Invalid command in state";
        ;
        assertEquals(true, l_OutContent.toString().contains(l_ex));
    }

    /**
     * This mehtod tests if order executions phase is called successfully after orders are created
     */
    @Test
    public void next() {
        ByteArrayOutputStream l_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_OutContent));

        OrderCreationPhase l_or = new OrderCreationPhase(d_ge);
        l_or.next();
        String l_ex = "setting order execution phase ";
        assertEquals(true, l_OutContent.toString().contains(l_ex));
    }
}
