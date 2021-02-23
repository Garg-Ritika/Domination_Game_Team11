package ca.concordia.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class contains test details of Order class
 */
public class OrderTest {
    Order o;

    /**
     * This is the method to be run before test method. It initializes the Order class object
     */

    @Before
    public void before(){

        o=new Order("abcd");
    }

    /**
     * This test method uses assert statement to test expected OrderCommand with actual OrderCommand
     */

    @Test
    public void getOrder() {
        assertEquals("abcd",o.getOrder());

    }
}