package ca.concordia.controller.map;

import ca.concordia.controller.game.PlayerActions;
import ca.concordia.model.Continent;
import ca.concordia.model.Country;
import ca.concordia.model.Map;
import ca.concordia.model.Player;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test class for ValidateMap
 */
public class ValidateMapTest {
    Map d_Map;
    ValidateMap d_ValidateMap;

    /**
     * This is the method to be run before test method. It initializes the ValidateMap lass object
     */
    @Before
    public void before() {
        // Initialize map
        d_Map = new Map();
        d_Map.getListOfContinents().add(new Continent(1, "NorthAmerica", 20, "RED"));
        d_Map.getListOfCountries().add(new Country(1, 1, "Canada", 2, 2));
        d_Map.getListOfCountries().add(new Country(2, 1, "America", 1, 1));
        d_Map.getListOfCountries().add(new Country(3, 1, "Mexico", 0, 0));
        d_Map.getListOfCountries().add(new Country(3, 1, "Quebec", 0, 0));

        d_ValidateMap = new ValidateMap(d_Map);

    }

    /**
     * validate the map
     */
    public void testValidateMap() {
        assertEquals(true, d_ValidateMap);
    }
}
