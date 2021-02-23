package ca.concordia.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
/**
 * This class contains test details of Continent class
 *
 * @author Satinder Pal Singh
 */
public class ContinentTest {

    /**
     * This method uses assert statement to test expected ContinentId with actual ContinentId
     */
    @Test
    public void getID() {
        Continent c = new Continent(2, "Ontario_and_Quebec", 4, "cyan");
        assertEquals(2, c.getID());
    }
    /**
     * This method tests whether expected Continent name with the actual Continent name
     */
    @Test
    public void getName() {
        Continent c = new Continent(2, "Ontario_and_Quebec", 4, "cyan");
        assertEquals("Ontario_and_Quebec", c.getName());
    }
    /**
     * This method tests whether expected Army count  matches with the actual Army Count
     */
    @Test
    public void getArmyCount() {
        Continent c = new Continent(2, "Ontario_and_Quebec", 4, "cyan");
        assertEquals(4, c.getArmyCount());
    }
    /**
     * This method tests whether expected Continent color matches with the actual Continent color.
     */
    @Test
    public void getColor() {
        Continent c = new Continent(2, "Ontario_and_Quebec", 4, "cyan");
        assertEquals("cyan", c.getColor());
    }
}