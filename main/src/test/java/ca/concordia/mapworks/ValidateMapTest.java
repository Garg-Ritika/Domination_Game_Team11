package ca.concordia.mapworks;

import ca.concordia.dao.*;
import org.junit.Before;
import org.junit.Test;

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
     * This is the method to be run before test method. It initializes the ValidateMap class object
     */
    @Before
    public void before() {
        d_Map = new Map();
        d_Map.getListOfContinents().add(new Continent(1, "NorthAmerica", 20, "RED"));
        d_Map.getListOfCountries().add(new Country(1, 1, "Canada", 2, 2));
        d_Map.getListOfCountries().add(new Country(2, 1, "America", 1, 1));
        d_Map.getListOfCountries().add(new Country(3, 1, "Mexico", 0, 0));
        d_Map.getListOfCountries().add(new Country(4, 1, "Quebec", 0, 0));

        List<Integer> l_NeighboursOfCanada = new ArrayList<>();
        l_NeighboursOfCanada.add(2);
        l_NeighboursOfCanada.add(3);
        l_NeighboursOfCanada.add(4);
        d_Map.getListOfBorders().add(new Border(1, l_NeighboursOfCanada));
        d_ValidateMap = new ValidateMap(d_Map);
    }

    /**
     * validate the map
     * (1) map validation – map is a connected graph;
     * (2) continent validation – continent is a connected subgraph;
     */
    @Test
    public void testValidateMap() {
        System.out.println("ValidateMap test");
        assertEquals(true, d_ValidateMap.validate());
        Graph continentGraph = d_Map.getContinentSubGraph("NorthAmerica");
        assertEquals(true, d_ValidateMap.validate());
    }
}
