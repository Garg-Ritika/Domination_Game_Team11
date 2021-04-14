package ca.concordia.patterns.adapter;

import ca.concordia.dao.Continent;
import ca.concordia.dao.Country;
import ca.concordia.dao.Map;
import ca.concordia.patterns.observer.LogUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * This test class is for DominationMapHandler CLass
 */
public class DominationMapHandlerTest {

    Map d_Map;
    DominationMapHandler d_DMH;

    /**
     * This method runs before every test method
     * and it initializes the objects
     */
    @Before
    public void init(){

        d_Map = new Map();
        d_Map.getListOfContinents().add(new Continent(1, "NorthAmerica", 20, "RED"));
        d_Map.getListOfCountries().add(new Country(1, 1, "Canada", 2, 2));
        d_Map.getListOfCountries().add(new Country(2, 1, "America", 1, 1));
        d_Map.getListOfCountries().add(new Country(3, 1, "Mexico", 0, 0));
        d_Map.getListOfCountries().add(new Country(3, 1, "Quebec", 0, 0));
        d_DMH = new DominationMapHandler(d_Map);
    }

    /**
     * This method is used to test if the domination map file is created successfully
     */
    @Test
    public void readMapFileTest(){
        String currentPath = System.getProperty("user.dir");
        File l_MapFile = new File(currentPath
                + File.separator + "maps"
                + File.separator + "domination"
                + File.separator + "canada"
                + File.separator + "canada.map");

        try {
            Map l_Map = d_DMH.readMapFile(l_MapFile);

            assertTrue(l_Map != null);
        }catch (IOException io){
            LogUtil.log(io.getMessage());
        }

    }

    /**
     * This method is used to test if the domination map file is created successfully
     */
    @Test
    public void writeMapFileTest(){
        String currentPath = System.getProperty("user.dir");
        File l_MapFile = new File(currentPath
                + File.separator + "maps"
                + File.separator + "domination"
                + File.separator + "canada"
                + File.separator + "canadatest.map");

        d_DMH.writeMapFile(l_MapFile);
        assertTrue(l_MapFile.exists());
    }
}
