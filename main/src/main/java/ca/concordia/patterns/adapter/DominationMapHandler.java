package ca.concordia.patterns.adapter;

import ca.concordia.dao.Border;
import ca.concordia.dao.Continent;
import ca.concordia.dao.Country;
import ca.concordia.dao.Map;
import ca.concordia.patterns.observer.LogUtil;

import java.io.*;
import java.util.ArrayList;

public class DominationMapHandler {

    public static final String HEADER_CONTINENT = "[continents]";
    public static final String HEADER_COUNTRIES = "[countries]";
    public static final String HEADER_BORDERS = "[borders]";

    private Map d_Map;

    public DominationMapHandler() {
        d_Map = new Map();
    }

    public DominationMapHandler(Map p_Map) {
        d_Map = p_Map;
    }

    /**
     * Helper method to read the map file from filesystem
     *
     * @param p_MapFile the file object of map file
     * @return map object
     * @throws IOException if an error is found, it will throw an error of InputOutput type
     */
    public Map readMapFile(File p_MapFile) throws IOException {

        if (p_MapFile != null && p_MapFile.exists()) {
            LogUtil.log("reading .map file from path: " + p_MapFile.getAbsolutePath());
            FileReader l_Fr = new FileReader(p_MapFile);
            BufferedReader l_Br = new BufferedReader(l_Fr);

            String l_Line;
            while ((l_Line = l_Br.readLine()) != null) {
                if (l_Line.equalsIgnoreCase(HEADER_CONTINENT)) {

                    int l_ContinentID = 0;
                    while ((l_Line = l_Br.readLine()).length() > 0) {
                        LogUtil.log("continent : " + l_Line);
                        // create continent id as per order
                        l_ContinentID++;
                        String[] l_ContinentArray = l_Line.trim().split(" ");
                        String l_ContinentName = l_ContinentArray[0];
                        String l_ContinentArmy = l_ContinentArray[1];
                        int l_ContinentArmyInteger = Integer.parseInt(l_ContinentArmy);
                        String l_ContinentColor = l_ContinentArray[2];

                        Continent l_Continent = new Continent(l_ContinentID, l_ContinentName, l_ContinentArmyInteger, l_ContinentColor);
                        d_Map.getListOfContinents().add(l_Continent);
                    }
                } else if (l_Line.equalsIgnoreCase(HEADER_COUNTRIES)) {

                    while ((l_Line = l_Br.readLine()).length() > 0) {
                        LogUtil.log("country : " + l_Line);

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
                        d_Map.getListOfCountries().add(l_Country);

                    }
                } else if (l_Line.equalsIgnoreCase(HEADER_BORDERS)) {
                    l_Line = l_Br.readLine();
                    while (l_Line != null && l_Line.length() > 0) {
                        LogUtil.log("border : " + l_Line);

                        String[] l_BorderArray = l_Line.trim().split(" ");
                        int l_CountryIdInteger = Integer.parseInt(l_BorderArray[0]);
                        ArrayList<Integer> l_BorderCountries = new ArrayList<Integer>();
                        for (int l_I = 1; l_I < l_BorderArray.length; l_I++) {
                            l_BorderCountries.add(Integer.parseInt(l_BorderArray[l_I]));
                        }
                        Border l_Border = new Border(l_CountryIdInteger, l_BorderCountries);
                        d_Map.getListOfBorders().add(l_Border);
                        l_Line = l_Br.readLine();
                    }
                }
            }
        }
        return d_Map;
    }

    /**
     * Private method to write the map file
     *
     * @param p_MapFile the map file object
     */
    public void writeMapFile(File p_MapFile) {
        try {

            FileWriter l_FileWriter = new FileWriter(p_MapFile);
            BufferedWriter l_BufferedWriter = new BufferedWriter(l_FileWriter);

            String l_Content = "; map:" + p_MapFile.getName() + "\r\n";
            l_Content += "; created by team-11 \r\n";

            l_Content += "\r\n[continents]\r\n";
            for (Continent l_Continent : d_Map.getListOfContinents()) {
                l_Content += l_Continent.getName() + " " + l_Continent.getArmyCount() + " " + l_Continent.getColor() + " \r\n";
            }

            l_Content += "\r\n[countries]\r\n";
            for (Country l_Country : d_Map.getListOfCountries()) {
                l_Content += l_Country.getCountryID() + " " + l_Country.getName() + " " + l_Country.getContinentID() + " " + l_Country.getX() + " " + l_Country.getY() + " \r\n";
            }

            l_Content += "\r\n[borders]\r\n";
            for (Border l_Border : d_Map.getListOfBorders()) {
                l_Content += l_Border.getCountryId() + " ";
                for (int neighbourCountryId : l_Border.getNeighbours()) {
                    l_Content += neighbourCountryId + " ";
                }
                l_Content += " \r\n";
            }

            l_BufferedWriter.write(l_Content);
            l_BufferedWriter.close();
            l_FileWriter.close();
        } catch (IOException io) {

        }
    }
}
