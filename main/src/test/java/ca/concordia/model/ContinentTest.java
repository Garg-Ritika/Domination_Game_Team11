package ca.concordia.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ContinentTest {


    @Test
    public void getID() {
        Continent c = new Continent(2, "Ontario_and_Quebec", 4, "cyan");
        assertEquals(2, c.getID());
    }

    @Test
    public void getName() {
        Continent c = new Continent(2, "Ontario_and_Quebec", 4, "cyan");
        assertEquals("Ontario_and_Quebec", c.getName());
    }

    @Test
    public void getArmyCount() {
        Continent c = new Continent(2, "Ontario_and_Quebec", 4, "cyan");
        assertEquals(4, c.getArmyCount());
    }

    @Test
    public void getColor() {
        Continent c = new Continent(2, "Ontario_and_Quebec", 4, "cyan");
        assertEquals("cyan", c.getColor());
    }
}