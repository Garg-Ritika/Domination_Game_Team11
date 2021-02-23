package ca.concordia.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * This class contains Test details about Player class
 */
public class PlayerTest {
    Player p;
    int d_NoOfArmies = 4;
    private List<Country> d_ListOfCountries = new ArrayList<>();

    /**
     * This is the method to be run before test method. It initializes the Player class object
     */
    @Before
    public void before() {
        p = new Player("abcd", 1);
    }

    /**
     * This method contains assert statement that checks whether expected PlayerName is same as Actual PlayerName
     */

    @Test
    public void getPlayerName() {
        assertEquals("abcd", p.getPlayerName());
    }

    /**
     * This method contains assert statement that checks expected and actual PlayerID
     */

    @Test
    public void getPlayerID() {
        assertEquals(1, p.getPlayerID());
    }

    /**
     * This method tests whether expected and actual NoOfArmies are equal or not
     */

    @Test
    public void getNoOfArmies() {
        assertEquals(1, p.getPlayerID());
        p.setNoOfArmies(d_NoOfArmies);
        assertEquals(d_NoOfArmies, p.getNoOfArmies());

    }

    /**
     * This test method contains assert statement that matches expected and actual ListOfContinents
     */

    @Test
    public void getListOfCountries() {

        Country c1 = new Country(6, 2, "Quebec-North", 430, 261);
        Country c2 = new Country(7, 3, "abc", 200, 205);
        d_ListOfCountries.add(c1);
        d_ListOfCountries.add(c2);

        p.setListOfCountries(d_ListOfCountries);
        assertEquals(d_ListOfCountries, p.getListOfCountries());

    }
}