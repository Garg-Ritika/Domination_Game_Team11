package ca.concordia.dao;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This class contains test details of Country class
 */
public class CountryTest {

    /**
     * This method tests whether expected Country name matches with the actual Country name
     */
    @Test
    public void getName() {
        Country l_Co = new Country(6, 2, "Quebec-North", 430, 261);
        assertEquals("Quebec-North", l_Co.getName());
    }

    /**
     * This method uses assert statement to test expected CountryId with actual CountryId
     */
    @Test
    public void getCountryID() {
        Country l_Co = new Country(6, 2, "Quebec-North", 430, 261);
        assertEquals(6, l_Co.getCountryID());
    }

    /**
     * This method uses assert statement to test expected Country's ContinentId  with actual ContinentId of a Country.
     */
    @Test
    public void getContinentID() {
        Country l_Co = new Country(6, 2, "Quebec-North", 430, 261);
        assertEquals(2, l_Co.getContinentID());
    }
}