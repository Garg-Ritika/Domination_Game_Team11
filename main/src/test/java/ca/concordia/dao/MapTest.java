package ca.concordia.dao;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test class for Map
 */
public class MapTest {

    Map d_Map;

    /**
     * This is the method to be run before test method. It initializes the Map class objects
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

    }

    /**
     * This  test method contains assert statement that matches expected and actual size of:
     * ListOfCountries
     * ListOfContinents
     * ListOfBorders
     * And AdjacencyMatrix.
     */
    @Test
    public void testMap() {
        assertEquals(4, d_Map.getListOfCountries().size());
        assertEquals(1, d_Map.getListOfContinents().size());
        assertEquals(1, d_Map.getListOfBorders().size());
        assertEquals(25, d_Map.getAdjacencyMatrix().size());
    }
}
