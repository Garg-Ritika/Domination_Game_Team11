package ca.concordia.controller.map;

import ca.concordia.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A singleton class to do all the things required for map-editing.
 * Since this is a singleton design pattern, only one instance of this class would be running at a time.
 */
public class MapEditor {

    public static final String HEADER_CONTINENT = "[continents]";
    public static final String HEADER_COUNTRIES = "[countries]";
    public static final String HEADER_BORDERS = "[borders]";

    private static MapEditor d_Instance = null;
    private static Map d_CurrentMap;

    /**
     * Private constructor of Map Editor, so that this class can only be instantiate from inside
     */
    private MapEditor() {
        this.d_CurrentMap = new Map();
    }

    /**
     * GetInstance method to get the single instance of the Mapeditor,
     * If there is already a instance available statically, return that otherwise create a new one
     *
     * @return
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
            System.out.println(l_Map.getListOfContinents().size());
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
                System.out.println("Adding a new country ");
                int l_NumberOfCountries = d_CurrentMap.getListOfCountries().size();
                Country l_country = new Country(l_NumberOfCountries + 1, l_ContinentID, p_CountryName, 0, 0);
                d_CurrentMap.getListOfCountries().add(l_country);
            }
        } else {
            System.out.println("Continent with name: " + p_Continent_Name + " doesn't exists");
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
        System.out.println("--> " + l_CountryID + " to " + l_NeighborID);

        if (l_CountryID > 0 && l_NeighborID > 0) {
            System.out.println("both country exists in map: " + p_CountryName + " " + p_NeighborName);
            boolean l_BorderFound = false;
            for (Border l_Border : d_CurrentMap.getListOfBorders()) {
                if (l_Border.getCountryId() == l_CountryID) {
                    if (!l_Border.isNeighbour(l_CountryID)) {
                        l_Border.addNeighbour(l_CountryID);
                    }
                    l_BorderFound = true;

                }
                else if (l_Border.getCountryId() == l_NeighborID) {
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
            System.out.println("not both country exists in map so unable to add it as neighbours");
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
            System.out.println("both country exists in map: " + p_CountryName + " " + p_NeighborName);

            for (Border l_Border : d_CurrentMap.getListOfBorders()) {
                if (l_Border.getCountryId() == l_CountryID) {
                    l_Border.removeNeighbour(l_NeighborID);

                } else if (l_Border.getCountryId() == l_NeighborID) {
                    l_Border.removeNeighbour(l_CountryID);
                }
            }
        } else {
            System.out.println("not both country exists in map so unable to remove it as neighbours");
            return false;
        }
        return true;
    }

    /**
     * Helper method to show the map
     */
    public void showMap() {
        System.out.println("showing map..");
        for (Continent l_Val : getCurrentMap().getListOfContinents()) {
            System.out.print(" ID: " + l_Val.getID());
            System.out.print(" Continent Name : " + l_Val.getName());
            System.out.print(" Army Count: " + l_Val.getArmyCount());
            System.out.print(" Color : " + l_Val.getColor());

            System.out.println(" \ncountries in this continent : ");
            for (Country l_Country : getCurrentMap().getListOfCountries()) {
                if (l_Country.getContinentID() == l_Val.getID()) {
                    System.out.println(l_Country.getCountryID() + " " + l_Country.getName() + " " + l_Country.getContinentID() + " x: " + l_Country.getX() + " Y: " + l_Country.getY());
                    for (Border l_Border : getCurrentMap().getListOfBorders()) {
                        if (l_Border.getCountryId() == l_Country.getCountryID()) {
                            int l_NeighbourCount = 0;
                            for (int l_NeighbourCountryID : l_Border.getNeighbours()) {
                                for (Country lCountry2 : getCurrentMap().getListOfCountries()) {
                                    if (l_NeighbourCountryID == lCountry2.getCountryID()) {
                                        l_NeighbourCount++;
                                        System.out.println("neighbour " + l_NeighbourCount + " is " + lCountry2.getName());
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
     * @param p_MapPath
     * @throws IOException
     */
    public void saveMap(File p_MapPath) throws IOException {
        validateMap();
        writeMapFile(p_MapPath);
    }

    /**
     * Load a map from an existing domination map file,
     * or create a new map from scratch if the file does not exist.
     * <p>
     * and automatically validates map upon loading
     *
     * @param p_MapPath
     * @throws IOException
     */
    public void editMap(File p_MapPath) throws IOException {
        readMapFile(p_MapPath);

        validateMap();
    }

    /**
     * Helper method to validate map. it is being called after reading and before writing a map
     * as well as through "validatemap" command
     *
     * @return
     */
    public boolean validateMap() {
        return new ValidateMap(getCurrentMap()).validate();
    }

    /**
     * private method to reset the currentmap into an empty map
     */
    private void resetCurrentMap() {
        this.d_CurrentMap = new Map();
    }

    /**
     * Helper method to read the map file from filesystem
     *
     * @param p_MapFile the file object of map file
     * @return map object
     * @throws IOException
     */
    public Map readMapFile(File p_MapFile) throws IOException {
        resetCurrentMap();
        if (p_MapFile != null && p_MapFile.exists()) {
            System.out.println("reading .map file from path: " + p_MapFile.getAbsolutePath());
            FileReader l_Fr = new FileReader(p_MapFile);
            BufferedReader l_Br = new BufferedReader(l_Fr);

            String l_Line;
            while ((l_Line = l_Br.readLine()) != null) {
                if (l_Line.equalsIgnoreCase(HEADER_CONTINENT)) {

                    int l_ContinentID = 1;
                    while ((l_Line = l_Br.readLine()).length() > 0) {
                        System.out.println("continent : " + l_Line);

                        String[] l_ContinentArray = l_Line.trim().split(" ");
                        String l_ContinentName = l_ContinentArray[0];
                        String l_ContinentArmy = l_ContinentArray[1];
                        int l_ContinentArmyInteger = Integer.parseInt(l_ContinentArmy);
                        String l_ContinentColor = l_ContinentArray[2];

                        Continent l_Continent = new Continent(l_ContinentID, l_ContinentName, l_ContinentArmyInteger, l_ContinentColor);
                        getCurrentMap().getListOfContinents().add(l_Continent);
                    }
                } else if (l_Line.equalsIgnoreCase(HEADER_COUNTRIES)) {

                    while ((l_Line = l_Br.readLine()).length() > 0) {
                        System.out.println("country : " + l_Line);

                        String[] l_CountryArray = l_Line.trim().split(" ");
                        String l_CountryId = l_CountryArray[0];
                        int l_CountryIdInteger = Integer.parseInt(l_CountryId);
                        String l_CountryName = l_CountryArray[1];
                        String l_ContinentId = l_CountryArray[2];
                        int l_ContinentIdInteger = Integer.parseInt(l_ContinentId);
                        String l_XCoordinate = l_CountryArray[3];
                        int l_XCoordinateInteger = Integer.parseInt(l_XCoordinate);
                        String l_YCoordinate = l_CountryArray[4];
                        int l_YCoordinateInteger = Integer.parseInt(l_YCoordinate);

                        Country l_Country = new Country(l_CountryIdInteger, l_ContinentIdInteger, l_CountryName, l_XCoordinateInteger, l_YCoordinateInteger);
                        getCurrentMap().getListOfCountries().add(l_Country);

                    }
                } else if (l_Line.equalsIgnoreCase(HEADER_BORDERS)) {
                    l_Line = l_Br.readLine();
                    while (l_Line != null && l_Line.length() > 0) {
                        System.out.println("border : " + l_Line);

                        String[] l_BorderArray = l_Line.trim().split(" ");
                        int l_CountryIdInteger = Integer.parseInt(l_BorderArray[0]);
                        ArrayList<Integer> l_BorderCountries = new ArrayList<Integer>();
                        for (int l_I = 1; l_I < l_BorderArray.length; l_I++) {
                            l_BorderCountries.add(Integer.parseInt(l_BorderArray[l_I]));
                        }
                        Border l_Border = new Border(l_CountryIdInteger, l_BorderCountries);
                        getCurrentMap().getListOfBorders().add(l_Border);
                        l_Line = l_Br.readLine();
                    }
                }
            }
        }
        return getCurrentMap();
    }

    /**
     * private method to write the map file
     *
     * @param p_MapFile the map file object
     * @throws IOException
     */
    private void writeMapFile(File p_MapFile) throws IOException {
        System.out.println("writing .map file to path " + p_MapFile.getAbsolutePath());
        FileWriter l_FileWriter = new FileWriter(p_MapFile);
        BufferedWriter l_BufferedWriter = new BufferedWriter(l_FileWriter);

        String l_Content = "; map:" + p_MapFile.getName() + "\r\n";
        l_Content += "; created by team-11 \r\n";

        l_Content += "\r\n[continents]\r\n";
        for (Continent l_Continent : getCurrentMap().getListOfContinents()) {
            l_Content += l_Continent.getName() + " " + l_Continent.getArmyCount() + " " + l_Continent.getColor() + " \r\n";
        }

        l_Content += "\r\n[countries]\r\n";
        for (Country l_Country : getCurrentMap().getListOfCountries()) {
            l_Content += l_Country.getCountryID() + " " + l_Country.getName() + " " + l_Country.getContinentID() + " " + l_Country.getX() + " " + l_Country.getY() + " \r\n";
        }

        l_Content += "\r\n[borders]\r\n";
        for (Border l_Border : getCurrentMap().getListOfBorders()) {
            l_Content += l_Border.getCountryId() + " ";
            for (int neighbourCountryId : l_Border.getNeighbours()) {
                l_Content += neighbourCountryId + " ";
            }
            l_Content += " \r\n";
        }

        l_BufferedWriter.write(l_Content);
        l_BufferedWriter.close();
        l_FileWriter.close();

        System.out.println("Successfully written map to .map file at: " + p_MapFile.getAbsolutePath());
    }

    /**
     * method to load the map as a graph
     * TODO: currently this method is not being used anywhere ..
     *
     * @return
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
