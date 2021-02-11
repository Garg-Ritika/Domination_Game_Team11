package ca.concordia.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CountryTest {


    @Test
    public void getName() {
        Country co=new Country(6,2,"Quebec-North",430,261);
        assertEquals("Quebec-North",co.getName());
    }

    @Test
    public void getCountryID() {
        Country co=new Country(6,2,"Quebec-North",430,261);
        assertEquals(6,co.getCountryID());
    }

    @Test
    public void getContinentID() {
        Country co=new Country(6,2,"Quebec-North",430,261);
        assertEquals(2,co.getContinentID());
    }


}