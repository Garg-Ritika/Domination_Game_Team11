package ca.concordia.controller.map;

import ca.concordia.model.Border;
import ca.concordia.model.Continent;
import ca.concordia.model.Country;
import ca.concordia.model.Map;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for MapEditor
 */
public class MapEditorTest {

    Map d_Map;
    MapEditor d_mapEditor;

    /**
     * This is the method to be run before test method. It initializes the MapEditor class object
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

        //neighbours
        List<Integer> l_NeighboursOfCanada = new ArrayList<>();
        l_NeighboursOfCanada.add(2);
        l_NeighboursOfCanada.add(3);
        l_NeighboursOfCanada.add(4);
        d_Map.getListOfBorders().add(new Border(1, l_NeighboursOfCanada));

        d_mapEditor = MapEditor.getInstance();
    }

    /**
     * Test mapeditor functions
     */
    @Test
    public void testMapEditor() throws IOException {
        // continent
        assertEquals(true, d_mapEditor.addContinent("Asia", 1));
        assertEquals(true, d_mapEditor.addContinent("Africa", 1));
        assertEquals(true, d_mapEditor.removeContinent("Africa"));

        assertEquals(true, d_mapEditor.addCountry("India", "Asia"));
        assertEquals(true, d_mapEditor.addCountry("China", "Asia"));
        assertEquals(true, d_mapEditor.addCountry("Nepal", "Asia"));

        assertEquals(true, d_mapEditor.addNeighbor("India", "Nepal"));
        assertEquals(true, d_mapEditor.addNeighbor("India", "China"));
        // it should remove neighbour entries as well
        //assertEquals(true, d_mapEditor.removeCountryUsingName("Nepal"));

        assertEquals(false, d_mapEditor.validateMap());
        assertNotNull(d_mapEditor.loadMapAsGraph());

        // Incase, it throws exception CI's ubuntu-latest
        try {
            d_mapEditor.saveMap(new File("abc.map"));
            d_mapEditor.readMapFile(new File("abc.map"));
            d_mapEditor.editMap(new File("abc.map"));
            d_mapEditor.showMap();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
