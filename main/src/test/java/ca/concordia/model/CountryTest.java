package ca.concordia.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
/**
 * This class contains test details of Country class
 *
 * @author to be updated
 */
public class CountryTest {

    /**
     * This method tests whether expected Country name matches with the actual Country name
     */
    @Test
    public void getName() {
        Country co=new Country(6,2,"Quebec-North",430,261);
        assertEquals("Quebec-North",co.getName());
    }
    /**
     * This method uses assert statement to test expected CountryId with actual CountryId
     */
    @Test
    public void getCountryID() {
        Country co=new Country(6,2,"Quebec-North",430,261);
        assertEquals(6,co.getCountryID());
    }
    /**
     * This method uses assert statement to test expected Country's ContinentId  with actual ContinentId of a Country.
     */
    @Test
    public void getContinentID() {
        Country co=new Country(6,2,"Quebec-North",430,261);
        assertEquals(2,co.getContinentID());
    }


}