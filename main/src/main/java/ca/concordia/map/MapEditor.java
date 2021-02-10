package ca.concordia.map;

import ca.concordia.IConstants;
import ca.concordia.model.Border;
import ca.concordia.model.Continent;
import ca.concordia.model.Country;
import ca.concordia.model.Map;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Create a singleton class, so that only instance of MapEditor will be there ...
public class MapEditor implements IConstants {

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

    public boolean addContinent(int continentID, String continentValue) {
        Continent continent = new Continent(continentID, continentValue);
        Map map = this.getCurrentMap();
        map.getListOfContinents().add(continent);
        System.out.println(map.getListOfContinents().size());
        return true;
    }

    public boolean removeContinent(int continentID) {
        Map map = this.getCurrentMap();
        for (Continent val : map.getListOfContinents()) {
            if (val.getID() == continentID) {
                map.getListOfContinents().remove(val);
                for (Country value : map.getListOfCountries()) {
                    if (value.getContinentID() == continentID) {
                        map.getListOfCountries().remove(continentID);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean addCountry(int countryID, int continentID) {
        Country country = new Country(countryID, continentID);
        Map map = this.currentMap;
        map.getListOfCountries().add(country);
        return true;
    }

    public boolean removeCountry(int countryID) {
        Map map = this.getCurrentMap();
        for (Country val : map.getListOfCountries()) {
            if (val.getCountryID() == countryID) {
                map.getListOfCountries().remove(countryID);
                return true;
            }
        }
        return false;
    }

    public boolean addNeighbor(int countryID, int neighborCountryID) {
        Map map = this.getCurrentMap();
        boolean foundFlag = false;
        for (Border val : map.getListOfBorders()) {
            if (val.getCountryId() == countryID) {
                foundFlag = true;
                val.addNeighbour(neighborCountryID);
            }
        }
        if (!foundFlag) {
            List<Integer> neighbours = new ArrayList<Integer>();
            neighbours.add(neighborCountryID);
            Border border = new Border(countryID, neighbours);
        }
        return true;
    }

    public boolean removeNeighbor(int countryID, int neighbourCountryID) {
        Map map = this.getCurrentMap();
        for (Border val : map.getListOfBorders()) {
            if (val.getCountryId() == countryID) {
                val.removeNeighbour(neighbourCountryID);
                return true;
            }
        }
        return false;
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
                    System.out.println(country.getName() + " x: " + country.getX() + " Y: " + country.getY());
                }
            }
        }
    }

    public void saveMap(File mapPath) throws IOException {
        writeMapFile(mapPath);
    }

    public void editMap(File mapPath) throws IOException {
        readMapFile(mapPath);
    }

    public boolean validateMap() {
        return new ValidateMap(getCurrentMap()).validate();

    }

    private void resetCurrentMap() {
        this.currentMap = new Map();
    }

    private void readMapFile(File mapFile) throws IOException {
        resetCurrentMap();
        System.out.println("reading .org.riskgame.model.map file from path: " + mapFile.getAbsolutePath());
        FileReader fr = new FileReader(mapFile);
        BufferedReader br = new BufferedReader(fr);

        String line;
        while ((line = br.readLine()) != null) {
            if (line.equalsIgnoreCase(HEADER_CONTINENT)) {

                int continentID = 0;
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

                while ((line = br.readLine()).length() > 0) {
                    System.out.println("border : " + line);

                    String[] borderArray = line.trim().split(" ");
                    int countryIdInteger = Integer.parseInt(borderArray[0]);
                    ArrayList<Integer> borderCountries = new ArrayList<Integer>();
                    for (int i = 1; i < borderArray.length; i++) {
                        borderCountries.add(Integer.parseInt(borderArray[i]));
                    }
                    Border border = new Border(countryIdInteger, borderCountries);
                    getCurrentMap().getListOfBorders().add(border);
                }
            }
        }
    }

    private void writeMapFile(File mapFile) throws IOException {
        System.out.println("writing .org.riskgame.model.map file to path " + mapFile.getAbsolutePath());
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

        System.out.println("Successfully written org.riskgame.model.map to org.riskgame.model.map file at: " + mapFile.getAbsolutePath());
    }

}
