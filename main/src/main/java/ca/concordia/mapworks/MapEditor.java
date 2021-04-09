package ca.concordia.mapworks;

import ca.concordia.dao.*;
import ca.concordia.patterns.adapter.ConquestMapHandler;
import ca.concordia.patterns.adapter.DominationMapHandler;
import ca.concordia.patterns.adapter.MapHandlerAdapter;
import ca.concordia.patterns.observer.LogUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A singleton class to do all the things required for map-editing.
 * Since this is a singleton design pattern, only one instance of this class would be running at a time.
 */
public class MapEditor {

    public static final String DOMINATION_MAP_TYPE = "DOMINATION_MAP";
    public static final String CONQUEST_MAP_TYPE = "CONQUEST_MAP";

    private static MapEditor d_Instance = null;
    private static Map d_CurrentMap;

    /**
     * Private constructor of Map Editor, so that this class can only be instantiate from inside
     */
    public MapEditor() {
        this.d_CurrentMap = new Map();
    }

    /**
     * GetInstance method to get the single instance of the Mapeditor,
     * If there is already a instance available statically, return that otherwise create a new one
     *
     * @return return the instance of the map
     */
    public static MapEditor getInstance() {
        if (d_Instance == null) {
            d_Instance = new MapEditor();
        }
        return d_Instance;
    }

    /**
     * Helper method to get the current map that is being process
     *
     * @return
     */
    private Map getCurrentMap() {
        return this.d_CurrentMap;
    }

    private void setCurrentMap(Map p_Map) {
        d_CurrentMap = p_Map;
    }


    /**
     * Helper method to add continent to the map
     *
     * @param p_ContinentName continent name
     * @param p_ArmyCount     army count
     * @return boolean
     */
    public boolean addContinent(String p_ContinentName, int p_ArmyCount) {
        boolean l_Found = false;
        for (Continent l_Continent : d_CurrentMap.getListOfContinents()) {
            if (l_Continent.getName().equalsIgnoreCase(p_ContinentName)) {
                l_Continent.setArmyCount(p_ArmyCount);
                l_Found = true;
            }
        }
        if (!l_Found) {
            int l_NumberOfContinents = this.getCurrentMap().getListOfContinents().size();
            Continent l_Continent = new Continent(l_NumberOfContinents + 1, p_ContinentName, p_ArmyCount, "RED");
            Map l_Map = this.getCurrentMap();
            l_Map.getListOfContinents().add(l_Continent);
            LogUtil.log("number of continents: " + l_Map.getListOfContinents().size());
        }
        return true;
    }

    /**
     * Helper method to the remove the continent from the man
     *
     * @param p_ContinentName continent name
     * @return boolean
     */
    public boolean removeContinent(String p_ContinentName) {
        Map l_Map = this.getCurrentMap();
        for (Continent l_continent : l_Map.getListOfContinents()) {
            if (l_continent.getName().equalsIgnoreCase(p_ContinentName)) {
                int l_continentID = l_continent.getID();
                l_Map.getListOfContinents().remove(l_continent);
                for (Country l_country : l_Map.getListOfCountries()) {
                    if (l_country.getContinentID() == l_continentID) {
                        removeCountryUsingID(l_country.getCountryID());
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to add the country into the map  and assign it to a continent
     *
     * @param p_CountryName    country name
     * @param p_Continent_Name continent name
     * @return boolean
     */
    public boolean addCountry(String p_CountryName, String p_Continent_Name) {

        int l_ContinentID = 0;
        for (Continent l_continent : d_CurrentMap.getListOfContinents()) {
            if (l_continent.getName().equalsIgnoreCase(p_Continent_Name)) {
                l_ContinentID = l_continent.getID();
            }
        }
        boolean l_Found = false;
        if (l_ContinentID > 0) {
            for (Country l_Country : d_CurrentMap.getListOfCountries()) {
                if (l_Country.getName().equalsIgnoreCase(p_CountryName)) {
                    l_Found = true;
                    l_Country.setContinentID(l_ContinentID);
                    break;
                }
            }
            if (!l_Found) {
                LogUtil.log("Adding a new country ");
                int l_NumberOfCountries = d_CurrentMap.getListOfCountries().size();
                Country l_country = new Country(l_NumberOfCountries + 1, l_ContinentID, p_CountryName, 0, 0);
                d_CurrentMap.getListOfCountries().add(l_country);
            }
        } else {
            LogUtil.log("Continent with name: " + p_Continent_Name + " doesn't exists");
            return false;
        }
        return true;
    }

    /**
     * Helper method to remove the country from the map and its continent
     *
     * @param p_CountryName country name
     * @return boolean
     */
    public boolean removeCountryUsingName(String p_CountryName) {
        for (Country l_Country : d_CurrentMap.getListOfCountries()) {
            if (l_Country.getName().equalsIgnoreCase(p_CountryName)) {
                int l_CountryID = l_Country.getCountryID();
                d_CurrentMap.getListOfCountries().remove(l_Country);

                for (Border l_Border : d_CurrentMap.getListOfBorders()) {
                    if (l_Border.getCountryId() == l_CountryID) {
                        d_CurrentMap.getListOfBorders().remove(l_Border);
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to remove the country using its country id
     *
     * @param p_CountryID country id integer
     * @return boolean
     */
    private boolean removeCountryUsingID(int p_CountryID) {
        Map l_Map = this.getCurrentMap();
        for (Country l_country : l_Map.getListOfCountries()) {
            if (l_country.getCountryID() == p_CountryID) {
                l_Map.getListOfCountries().remove(l_country);
                for (Border l_border : l_Map.getListOfBorders()) {
                    if (l_border.getCountryId() == p_CountryID) {
                        l_Map.getListOfBorders().remove(l_border);
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to add a new neighbour country to a country
     *
     * @param p_CountryName  country whose neighbour is being assigned
     * @param p_NeighborName the new neighbor
     * @return boolean
     */
    public boolean addNeighbor(String p_CountryName, String p_NeighborName) {
        int l_CountryID = 0;
        int l_NeighborID = 0;
        for (Country l_country : d_CurrentMap.getListOfCountries()) {
            if (l_country.getName().equalsIgnoreCase(p_CountryName)) {
                l_CountryID = l_country.getCountryID();
            } else if (l_country.getName().equalsIgnoreCase(p_NeighborName)) {
                l_NeighborID = l_country.getCountryID();
            }
        }
        LogUtil.log("--> " + l_CountryID + " to " + l_NeighborID);

        if (l_CountryID > 0 && l_NeighborID > 0) {
            LogUtil.log("both country exists in map: " + p_CountryName + " " + p_NeighborName);
            boolean l_BorderFound = false;
            for (Border l_Border : d_CurrentMap.getListOfBorders()) {
                if (l_Border.getCountryId() == l_CountryID) {
                    if (!l_Border.isNeighbour(l_CountryID)) {
                        l_Border.addNeighbour(l_CountryID);
                    }
                    l_BorderFound = true;

                } else if (l_Border.getCountryId() == l_NeighborID) {
                    if (!l_Border.isNeighbour(l_NeighborID)) {
                        l_Border.addNeighbour(l_NeighborID);
                    }
                }
            }
            if (!l_BorderFound) {
                List<Integer> l_TempNeighbourList1 = new ArrayList<>();
                l_TempNeighbourList1.add(l_NeighborID);
                Border l_NewBorder1 = new Border(l_CountryID, l_TempNeighbourList1);
                getCurrentMap().getListOfBorders().add(l_NewBorder1);

                List<Integer> l_TempNeighbourList2 = new ArrayList<>();
                l_TempNeighbourList2.add(l_CountryID);
                Border l_NewBorder2 = new Border(l_NeighborID, l_TempNeighbourList2);
                getCurrentMap().getListOfBorders().add(l_NewBorder2);
            }
        } else {
            LogUtil.log("not both country exists in map so unable to add it as neighbours");
            return false;
        }
        return true;
    }

    /**
     * Helper method to remove a neighbor from the country
     *
     * @param p_CountryName  country whose neighbour is being removed
     * @param p_NeighborName the neighbour being removed
     * @return boolean
     */
    public boolean removeNeighbor(String p_CountryName, String p_NeighborName) {
        int l_CountryID = 0;
        int l_NeighborID = 0;
        for (Country l_country : d_CurrentMap.getListOfCountries()) {
            if (l_country.getName().equalsIgnoreCase(p_CountryName)) {
                l_CountryID = l_country.getCountryID();
            } else if (l_country.getName().equalsIgnoreCase(p_NeighborName)) {
                l_NeighborID = l_country.getCountryID();
            }
        }

        if (l_CountryID > 0 && l_NeighborID > 0) {
            LogUtil.log("both country exists in map: " + p_CountryName + " " + p_NeighborName);

            for (Border l_Border : d_CurrentMap.getListOfBorders()) {
                if (l_Border.getCountryId() == l_CountryID) {
                    l_Border.removeNeighbour(l_NeighborID);

                } else if (l_Border.getCountryId() == l_NeighborID) {
                    l_Border.removeNeighbour(l_CountryID);
                }
            }
        } else {
            LogUtil.log("not both country exists in map so unable to remove it as neighbours");
            return false;
        }
        return true;
    }

    /**
     * Helper method to show the map
     */
    public void showMap() {
        LogUtil.log("showing map..");
        for (Continent l_Val : getCurrentMap().getListOfContinents()) {
            StringBuilder builder = new StringBuilder();
            builder.append(" ID: " + l_Val.getID());
            builder.append(" Continent Name : " + l_Val.getName());
            builder.append(" Army Count: " + l_Val.getArmyCount());
            builder.append(" Color: " + l_Val.getColor());
            LogUtil.log(builder.toString());


            LogUtil.log("countries in this continent : ");
            for (Country l_Country : getCurrentMap().getListOfCountries()) {
                if (l_Country.getContinentID() == l_Val.getID()) {
                    LogUtil.log(l_Country.getCountryID() + " " + l_Country.getName() + " " + l_Country.getContinentID() + " x: " + l_Country.getX() + " Y: " + l_Country.getY());
                    for (Border l_Border : getCurrentMap().getListOfBorders()) {
                        if (l_Border.getCountryId() == l_Country.getCountryID()) {
                            int l_NeighbourCount = 0;
                            for (int l_NeighbourCountryID : l_Border.getNeighbours()) {
                                for (Country lCountry2 : getCurrentMap().getListOfCountries()) {
                                    if (l_NeighbourCountryID == lCountry2.getCountryID()) {
                                        l_NeighbourCount++;
                                        LogUtil.log("neighbour " + l_NeighbourCount + " is " + lCountry2.getName());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Save a map to a text file exactly as edited using
     * domination game map format.
     * and automatically validates map before saving ..
     *
     * @param p_MapPath value which is passed as parameter is the file path and is an object of class File
     * @throws IOException if an error is found, it will throw an error of InputOutput type
     */
    public void saveMap(File p_MapPath) throws IOException {
        String l_MapType = DOMINATION_MAP_TYPE;
        Scanner l_Keyboard = new Scanner(System.in);

        LogUtil.log("========================================");
        LogUtil.log("1. Domination Map");
        LogUtil.log("2. Conquest Map ");
        LogUtil.log("Press 1 for Domination map 2 for Conquest map ");
        LogUtil.log("=======================================");
        int l_Input = l_Keyboard.nextInt();
        LogUtil.log(Integer.toString(l_Input));
        switch (l_Input) {
            case 1:
                l_MapType = DOMINATION_MAP_TYPE;
                break;
            case 2:
                l_MapType = CONQUEST_MAP_TYPE;
                break;
        }
        validateMap();
        writeMapFile(p_MapPath, l_MapType);
    }

    /**
     * Load a map from an existing domination map file,
     * or create a new map from scratch if the file does not exist.
     * <p>
     * and automatically validates map upon loading
     *
     * @param p_MapPath value which is passed as parameter is the file path and is an object of class File
     * @throws IOException inputOutput exception
     */
    public void editMap(File p_MapPath) throws IOException {
        readMapFile(p_MapPath);
        validateMap();
    }

    /**
     * Helper method to validate map. it is being called after reading and before writing a map
     * as well as through "validatemap" command
     *
     * @return ValidateMap(getCurrentMap ())
     */
    public boolean validateMap() {
        return new ValidateMap(getCurrentMap()).validate();
    }

    /**
     * private method to reset the currentmap into an empty map
     */
    public void resetCurrentMap() {
        this.d_CurrentMap = new Map();
    }

    /**
     * Helper method to read the map file from filesystem
     *
     * @param p_MapFile the file object of map file
     * @return map object
     * @throws IOException if an error is found, it will throw an error of InputOutput type
     */
    public Map readMapFile(File p_MapFile) throws IOException {
        resetCurrentMap();

        if (p_MapFile != null) {
            BufferedReader bufferReaderForFile = new BufferedReader(new FileReader(p_MapFile));
            String firstLine = bufferReaderForFile.readLine();
            DominationMapHandler dmh = new DominationMapHandler(d_CurrentMap);
            ConquestMapHandler cmh = new ConquestMapHandler(d_CurrentMap);

            if (!firstLine.startsWith(";")) {
                dmh = new MapHandlerAdapter(cmh);
            }
            d_CurrentMap = dmh.readMapFile(p_MapFile);
        }
        return getCurrentMap();
    }

    /**
     * Private method to write the map file
     *
     * @param p_MapFile the map file object
     */
    public void writeMapFile(File p_MapFile, String p_MapType) {
        LogUtil.log("writing .map file to path " + p_MapFile.getAbsolutePath());
        DominationMapHandler dmh = new DominationMapHandler(d_CurrentMap);
        if (p_MapType.equalsIgnoreCase(DOMINATION_MAP_TYPE)) {
            dmh.writeMapFile(p_MapFile);
        } else if (p_MapType.equalsIgnoreCase(CONQUEST_MAP_TYPE)) {
            ConquestMapHandler cmh = new ConquestMapHandler(d_CurrentMap);
            dmh = new MapHandlerAdapter(cmh);
            dmh.writeMapFile(p_MapFile);
        }
        LogUtil.log("Successfully written map to .map file at: " + p_MapFile.getAbsolutePath());
    }

    /**
     * Method to load the map as a graph
     * TODO: currently this method is not being used anywhere ..
     *
     * @return l_Graph
     */
    public Graph loadMapAsGraph() {
        Map l_Map = this.getCurrentMap();
        int l_NumberOfCountries = l_Map.getListOfCountries().size();
        Graph l_Graph = new Graph(l_NumberOfCountries);
        for (int l_I = 0; l_I < l_Map.getListOfBorders().size(); l_I++) {
            Border l_Border = l_Map.getListOfBorders().get(l_I);
            for (int l_CountryId : l_Border.getNeighbours()) {
                l_Graph.addEdge(l_Border.getCountryId(), l_CountryId);
            }

        }
        return l_Graph;
    }

}
