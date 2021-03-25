package ca.concordia.patterns.state.play;

import ca.concordia.gameengine.GameEngine;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

/**
 * This class checks the method implementation of Order Execution phase class
 */
public class OrderExecutionPhaseTest {

    GameEngine d_ge = new GameEngine();

    /**
     * This method checks if invalid command message is shown to the user
     */
    @Test
    public void reinforce() {
        ByteArrayOutputStream l_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_OutContent));

        OrderExecutionPhase l_oe = new OrderExecutionPhase(d_ge);
        l_oe.reinforce();
        String l_ex = "Invalid command in ";
        ;
        assertEquals(true, l_OutContent.toString().contains(l_ex));
    }

    /**
     * This method checks if invalid command message is shown to the user
     */
    @Test
    public void createOrder() {
        ByteArrayOutputStream l_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_OutContent));

        OrderExecutionPhase l_oe = new OrderExecutionPhase(d_ge);
        l_oe.createOrder();
        String l_ex = "Invalid command in state";
        assertEquals(true, l_OutContent.toString().contains(l_ex));
    }

    /**
     * This method is used to test if executeorders method is called successfully after orders are created
     */
    @Test
    public void executeOrder() {
        ByteArrayOutputStream l_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_OutContent));

        OrderExecutionPhase l_oe = new OrderExecutionPhase(d_ge);
        l_oe.executeOrder();
        String l_ex = "execute order";
        ;
        assertEquals(true, l_OutContent.toString().contains(l_ex));
    }

    /**
     * This method is used to test if after executoing all orders if the reinforcement ohase is called
     */
    @Test
    public void next() {
        ByteArrayOutputStream l_OutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(l_OutContent));

        OrderExecutionPhase l_oe = new OrderExecutionPhase(d_ge);
        l_oe.next();
        String l_ex = "setting reinforcement phase";
        assertEquals(true, l_OutContent.toString().contains(l_ex));
    }
}
