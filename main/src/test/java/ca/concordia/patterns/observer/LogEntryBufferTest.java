package ca.concordia.patterns.observer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the setter and getter method of LogEntryBuffer class
 */
public class LogEntryBufferTest {

    LogEntryBuffer leb;
    String str = "abcd";

    /**
     * This method runs before each test method
     */
    @Before
    public void before() {
        leb = new LogEntryBuffer();

    }

    /**
     * This method checks whether actual and expected value of getUpdate method is same or not
     */
    @Test
    public void getUpdate() {

        leb.setUpdate(str);
        assertEquals(leb.getUpdate(), "abcd");

    }
}