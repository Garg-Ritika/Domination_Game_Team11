package ca.concordia.controller.map;

import ca.concordia.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Create a singleton class, so that only instance of MapEditor will be there ...
public class MapEditor {

    //
    /* details from domination .map file */
    public static final String HEADER_CONTINENT = "[continents]";
    public static final String HEADER_COUNTRIES = "[countries]";
    public static final String HEADER_BORDERS = "[borders]";

    private static MapEditor instance = null;
    private static Map currentMap;

    private MapEditor() {
        this.currentMap = new Map();
    }

    public static MapEditor getInstance() {
        if (instance == null) {
            instance = new MapEditor();
        }
        return instance;
    }

    private Map getCurrentMap() {
        return this.currentMap;
    }

    public boolean addContinent(String continentName, int armyCount) {
        boolean l_Found = false;
        for (Continent l_Continent: currentMap.getListOfContinents()){
            if (l_Continent.getName().equalsIgnoreCase(continentName)){
                l_Continent.setArmyCount(armyCount);
                l_Found = true;
            }
        }
        if(!l_Found) {
            int l_NumberOfContinents = this.getCurrentMap().getListOfContinents().size();
            //TODO : add a unique new color to the new continent
            Continent continent = new Continent(l_NumberOfContinents + 1, continentName, armyCount, "RED");
            Map map = this.getCurrentMap();
            map.getListOfContinents().add(continent);
            System.out.println(map.getListOfContinents().size());
        }
        return true;
    }

    public boolean removeContinent(String p_ContinentName) {
        Map map = this.getCurrentMap();
        for (Continent l_continent : map.getListOfContinents()) {
            if (l_continent.getName().equalsIgnoreCase(p_ContinentName)) {
                int l_continentID = l_continent.getID();
                map.getListOfContinents().remove(l_continent);

                // also remove country and border using continentID
                for (Country l_country: map.getListOfCountries()){
                    if (l_country.getContinentID() == l_continentID){
                        removeCountryUsingID(l_country.getCountryID());
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean addCountry(String p_CountryName, String p_Continent_Name) {

        // find continentID from continent_name
        int l_ContinentID = 0;
        for(Continent l_continent: currentMap.getListOfContinents()){
            if(l_continent.getName().equalsIgnoreCase(p_Continent_Name)){
                l_ContinentID = l_continent.getID();
            }
        }
        boolean l_Found = false;
        if(l_ContinentID>0) {
            for (Country l_Country : currentMap.getListOfCountries()) {
                if (l_Country.getName().equalsIgnoreCase(p_CountryName)) {
                    l_Found = true;
                    l_Country.setContinentID(l_ContinentID);
                    break;
                }
            }
            if(!l_Found){
                System.out.println("Adding a new country ");
                int l_NumberOfCountries = currentMap.getListOfCountries().size();
                //TODO :by default x and y coordinates are 0,0 for now
                Country l_country = new Country(l_NumberOfCountries+1, l_ContinentID,p_CountryName,0,0);
                currentMap.getListOfCountries().add(l_country);
            }
        }else{
            System.out.println("Continent with name: "+ p_Continent_Name + " doesn't exists");
            return false;
        }
        return true;
    }

    public boolean removeCountryUsingName(String countryName){
        for(Country l_Country : currentMap.getListOfCountries()){
            if (l_Country.getName().equalsIgnoreCase(countryName)){
                int l_CountryID = l_Country.getCountryID();
                currentMap.getListOfCountries().remove(l_Country);

                for (Border l_Border: currentMap.getListOfBorders()){
                    if (l_Border.getCountryId() == l_CountryID){
                        currentMap.getListOfBorders().remove(l_Border);
                    }
                }
                return true;
            }
        }
        return false;
    }

    private boolean removeCountryUsingID(int countryID) {
        Map map = this.getCurrentMap();
        for (Country l_country : map.getListOfCountries()) {
            if (l_country.getCountryID() == countryID) {
                map.getListOfCountries().remove(l_country);

                // Also remove borders
                for (Border l_border: map.getListOfBorders()){
                    if(l_border.getCountryId() == countryID){
                        map.getListOfBorders().remove(l_border);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean addNeighbor(String p_CountryName, String p_NeighborName) {
        int l_CountryID = 0 ;
        int l_NeighborID = 0;
        for(Country l_country: currentMap.getListOfCountries()){
            if(l_country.getName().equalsIgnoreCase(p_CountryName)){
                l_CountryID = l_country.getCountryID();
            }else if(l_country.getName().equalsIgnoreCase(p_NeighborName)){
                l_NeighborID = l_country.getCountryID();
            }
        }
        System.out.println("--> " + l_CountryID + " to " + l_NeighborID);

        if(l_CountryID >0 && l_NeighborID >0){
            System.out.println("both country exists in map: " + p_CountryName + " " + p_NeighborName);
            boolean l_BorderFound = false;
            for(Border l_Border: currentMap.getListOfBorders()){
                // make country1 neighbour of country2
                if(l_Border.getCountryId() == l_CountryID){
                    if(!l_Border.isNeighbour(l_CountryID)){
                        l_Border.addNeighbour(l_CountryID);
                    }
                    l_BorderFound = true;

                }// make country1 neighbour of country2
                else if(l_Border.getCountryId() == l_NeighborID){
                    if(!l_Border.isNeighbour(l_NeighborID)){
                        l_Border.addNeighbour(l_NeighborID);
                    }
                }
            }
            if(!l_BorderFound){
                // country1's neighbour is country2
                List<Integer> l_TempNeighbourList1 = new ArrayList<>();
                l_TempNeighbourList1.add(l_NeighborID);
                Border l_NewBorder1 = new Border(l_CountryID,l_TempNeighbourList1);
                getCurrentMap().getListOfBorders().add(l_NewBorder1);

                // country2's neighbour is country1
                List<Integer> l_TempNeighbourList2 = new ArrayList<>();
                l_TempNeighbourList2.add(l_CountryID);
                Border l_NewBorder2 = new Border(l_NeighborID,l_TempNeighbourList2);
                getCurrentMap().getListOfBorders().add(l_NewBorder2);
            }
        }else{
            System.out.println("not both country exists in map so unable to add it as neighbours");
            return false;
        }
        return true;
    }

    public boolean removeNeighbor(String p_CountryName, String p_NeighborName) {
        int l_CountryID = 0 ;
        int l_NeighborID = 0;
        for(Country l_country: currentMap.getListOfCountries()){
            if(l_country.getName().equalsIgnoreCase(p_CountryName)){
                l_CountryID = l_country.getCountryID();
            }else if(l_country.getName().equalsIgnoreCase(p_NeighborName)){
                l_NeighborID = l_country.getCountryID();
            }
        }

        if(l_CountryID >0 && l_NeighborID >0){
            System.out.println("both country exists in map: " + p_CountryName + " " + p_NeighborName);

            for(Border l_Border: currentMap.getListOfBorders()){
                // both are neighbour's to each other, so remove two times
                if(l_Border.getCountryId() == l_CountryID){
                    l_Border.removeNeighbour(l_NeighborID);

                }else if(l_Border.getCountryId() == l_NeighborID){
                    l_Border.removeNeighbour(l_CountryID);
                }
            }
        }else{
            System.out.println("not both country exists in map so unable to remove it as neighbours");
            return false;
        }
        return true;
    }

    public void showMap() {
        System.out.println("showing map..");
        for (Continent val : getCurrentMap().getListOfContinents()) {
            System.out.print(" ID: " + val.getID());
            System.out.print(" Continent Name : " + val.getName());
            System.out.print(" Army Count: " + val.getArmyCount());
            System.out.print(" Color : " + val.getColor());

            System.out.println(" \ncountries in this continent : ");
            for (Country country : getCurrentMap().getListOfCountries()) {
                if (country.getContinentID() == val.getID()) {
                    System.out.println(country.getCountryID()+ " " + country.getName() + " " + country.getContinentID()+  " x: " + country.getX() + " Y: " + country.getY());

                    // show borders
                    for(Border l_Border: getCurrentMap().getListOfBorders()){
                        if(l_Border.getCountryId() == country.getCountryID()){
                            int l_NeighbourCount = 0;
                            for(int l_NeighbourCountryID: l_Border.getNeighbours()){
                                for(Country lCountry2: getCurrentMap().getListOfCountries()){
                                    if(l_NeighbourCountryID == lCountry2.getCountryID()){
                                        l_NeighbourCount++;
                                        System.out.println("neighbour " + l_NeighbourCount +" is "+ lCountry2.getName() );
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
     * Save a map to a text file exactly as edited (using the “domination” game map format).
     * and automatically validates map before saving ..
     * @param mapPath
     * @throws IOException
     */
    public void saveMap(File mapPath) throws IOException {
        validateMap();
        writeMapFile(mapPath);
    }

    /**
     * Load a map from an existing “domination” map file,
     * or create a new map from scratch if the file does not exist.
     *
     *  and automatically validates map upon loading
     * @param mapPath
     * @throws IOException
     */
    public void editMap(File mapPath) throws IOException {
        readMapFile(mapPath);

        validateMap();
    }

    public boolean validateMap() {
        return new ValidateMap(getCurrentMap()).validate();
    }

    private void resetCurrentMap() {
        this.currentMap = new Map();
    }

    public Map readMapFile(File mapFile) throws IOException {
        resetCurrentMap();
        if(mapFile!= null && mapFile.exists()) {
            System.out.println("reading .map file from path: " + mapFile.getAbsolutePath());
            FileReader fr = new FileReader(mapFile);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                if (line.equalsIgnoreCase(HEADER_CONTINENT)) {

                    int continentID = 1;
                    while ((line = br.readLine()).length() > 0) {
                        System.out.println("continent : " + line);

                        String[] continentArray = line.trim().split(" ");
                        String continentName = continentArray[0];
                        String continentArmy = continentArray[1];
                        int continentArmyInteger = Integer.parseInt(continentArmy);
                        String continentColor = continentArray[2];

                        Continent continent = new Continent(continentID, continentName, continentArmyInteger, continentColor);
                        getCurrentMap().getListOfContinents().add(continent);
                        // note: the while loop is supposed to break on blank line..
                    }
                } else if (line.equalsIgnoreCase(HEADER_COUNTRIES)) {

                    while ((line = br.readLine()).length() > 0) {
                        System.out.println("country : " + line);

                        String[] countryArray = line.trim().split(" ");
                        String countryId = countryArray[0];
                        int countryIdInteger = Integer.parseInt(countryId);
                        String countryName = countryArray[1];
                        String continentId = countryArray[2];
                        int continentIdInteger = Integer.parseInt(continentId);
                        String xCoordinate = countryArray[3];
                        int xCoordinateInteger = Integer.parseInt(xCoordinate);
                        String yCoordinate = countryArray[4];
                        int yCoordinateInteger = Integer.parseInt(yCoordinate);

                        Country country = new Country(countryIdInteger, continentIdInteger, countryName, xCoordinateInteger, yCoordinateInteger);
                        getCurrentMap().getListOfCountries().add(country);

                    }
                } else if (line.equalsIgnoreCase(HEADER_BORDERS)) {
                    line = br.readLine();
                    while (line != null && line.length() > 0) {
                        System.out.println("border : " + line);

                        String[] borderArray = line.trim().split(" ");
                        int countryIdInteger = Integer.parseInt(borderArray[0]);
                        ArrayList<Integer> borderCountries = new ArrayList<Integer>();
                        for (int i = 1; i < borderArray.length; i++) {
                            borderCountries.add(Integer.parseInt(borderArray[i]));
                        }
                        Border border = new Border(countryIdInteger, borderCountries);
                        getCurrentMap().getListOfBorders().add(border);
                        line = br.readLine();
                    }
                }
            }
        }
        return getCurrentMap();
    }

    private void writeMapFile(File mapFile) throws IOException {
        System.out.println("writing .map file to path " + mapFile.getAbsolutePath());
        FileWriter fileWriter = new FileWriter(mapFile);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        String content = "; map:" + mapFile.getName() + "\r\n";
        content += "; created by team-11 \r\n";

        content += "\r\n[continents]\r\n";
        for (Continent continent : getCurrentMap().getListOfContinents()) {
            content += continent.getName() + " " + continent.getArmyCount() + " " + continent.getColor() + " \r\n";
        }

        content += "\r\n[countries]\r\n";
        for (Country country : getCurrentMap().getListOfCountries()) {
            content += country.getCountryID() + " " + country.getName() + " " + country.getContinentID() + " " + country.getX() + " " + country.getY() + " \r\n";
        }

        content += "\r\n[borders]\r\n";
        for (Border border : getCurrentMap().getListOfBorders()) {
            content += border.getCountryId() + " ";
            for (int neighbourCountryId : border.getNeighbours()) {
                content += neighbourCountryId + " ";
            }
            content += " \r\n";
        }

        bufferedWriter.write(content);
        bufferedWriter.close();
        fileWriter.close();

        System.out.println("Successfully written map to .map file at: " + mapFile.getAbsolutePath());
    }

    public Graph loadMapAsGraph(){
        Map map = this.getCurrentMap();
        int numberOfCountries = map.getListOfCountries().size();
        Graph graph = new Graph(numberOfCountries);
        for (int i = 0; i < map.getListOfBorders().size(); i++) {
            Border border = map.getListOfBorders().get(i);
            for (int countryId : border.getNeighbours()) {
                graph.addEdge(border.getCountryId(), countryId);
            }

        }
        return graph;
    }
}
