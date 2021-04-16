package ca.concordia.patterns.adapter;

import ca.concordia.dao.Border;
import ca.concordia.dao.Continent;
import ca.concordia.dao.Country;
import ca.concordia.dao.Map;
import ca.concordia.patterns.observer.LogUtil;

import java.io.*;
import java.util.ArrayList;

/**
 * This class includes methods to read and write map files
 * readMapFile also throws IOException
 */
public class ConquestMapHandler {

    public static final String HEADER_CONTINENTS = "[Continents]";
    public static final String HEADER_TERRITORIES = "[Territories]";


    private Map d_Map;

    public ConquestMapHandler() {
        d_Map = new Map();
    }

    public ConquestMapHandler(Map p_Map) {
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
            LogUtil.log(">reading .map file from path: " + p_MapFile.getAbsolutePath());
            FileReader l_Fr = new FileReader(p_MapFile);
            BufferedReader l_Br = new BufferedReader(l_Fr);

            String l_Line;
            while ((l_Line = l_Br.readLine()) != null) {
                if (l_Line.equalsIgnoreCase(HEADER_CONTINENTS)) {

                    // default continent id
                    int l_ContinentID = 0;
                    while ((l_Line = l_Br.readLine()).length() > 0) {
                        l_ContinentID++;
                        LogUtil.log("continent : " + l_Line);

                        String[] l_ContinentArray = l_Line.trim().split("=");
                        String l_ContinentName = l_ContinentArray[0];
                        String l_ContinentArmy = l_ContinentArray[1];
                        int l_ContinentArmyInteger = Integer.parseInt(l_ContinentArmy);
                        // A default colour for conquest maps
                        String l_ContinentColor = "white";

                        Continent l_Continent = new Continent(l_ContinentID, l_ContinentName, l_ContinentArmyInteger, l_ContinentColor);
                        d_Map.getListOfContinents().add(l_Continent);
                    }
                } else if (l_Line.equalsIgnoreCase(HEADER_TERRITORIES)) {

                    int l_CountryId = 0;
                    while ((l_Line = l_Br.readLine()) != null) {
                        LogUtil.log("country : " + l_Line);

                        if (l_Line.length() > 0) {

                            String[] l_CountryArray = l_Line.trim().split(",");
                            // create the country id as per order;
                            l_CountryId++;
                            int l_CountryIdInteger = l_CountryId;
                            String l_CountryName = l_CountryArray[0];
                            String l_XCoordinate = l_CountryArray[1];
                            int l_XCoordinateInteger = Integer.parseInt(l_XCoordinate);
                            String l_YCoordinate = l_CountryArray[2];
                            int l_YCoordinateInteger = Integer.parseInt(l_YCoordinate);
                            String l_ContinentName = l_CountryArray[3];

                            int l_ContinentIdInteger = 0;
                            for (Continent continent : d_Map.getListOfContinents()) {
                                if (continent.getName().equalsIgnoreCase(l_ContinentName)) {
                                    l_ContinentIdInteger = continent.getID();
                                }
                            }

                            Country l_Country = new Country(l_CountryIdInteger, l_ContinentIdInteger, l_CountryName, l_XCoordinateInteger, l_YCoordinateInteger);
                            d_Map.getListOfCountries().add(l_Country);

                        }
                    }
                }
            }
            l_Br.close();
            l_Fr.close();

            readBordersFile(p_MapFile);
        }
        return d_Map;
    }

    /**
     * Read borders in second pass such that the country id should already be in the map
     *
     * @param p_MapFile map file
     * @throws IOException throws exception if map file foes not exist
     */
    public void readBordersFile(File p_MapFile) throws IOException {
        System.out.println("--> read borders file ");
        if (p_MapFile != null && p_MapFile.exists()) {
            LogUtil.log(">reading .map file from path: " + p_MapFile.getAbsolutePath());
            FileReader l_Fr = new FileReader(p_MapFile);
            BufferedReader l_Br = new BufferedReader(l_Fr);

            String l_Line;
            int l_CountryId = 0;
            while ((l_Line = l_Br.readLine()) != null) {
                if (l_Line.equalsIgnoreCase(HEADER_TERRITORIES)) {

                    while ((l_Line = l_Br.readLine()) != null) {
                        LogUtil.log("country : " + l_Line);

                        if (l_Line.length() > 0) {

                            String[] l_CountryArray = l_Line.trim().split(",");
                            // create the country id as per order;
                            l_CountryId++;
                            int l_CountryIdInteger = l_CountryId;
                            String l_CountryName = l_CountryArray[0];

                            ArrayList<Integer> l_BorderCountries = new ArrayList<Integer>();
                            // borders entries starting from index 4 upto the length of array
                            for (int l_I = 4; l_I < l_CountryArray.length; l_I++) {
                                String l_BorderCountryName = (l_CountryArray[l_I]);
                                // find id from the map
                                int l_BorderCountryId = 0;
                                for (Country l_Country : d_Map.getListOfCountries()) {
                                    if (l_Country.getName().equalsIgnoreCase(l_BorderCountryName)) {
                                        l_BorderCountryId = l_Country.getCountryID();
                                        break;
                                    }
                                }
                                l_BorderCountries.add(l_BorderCountryId);
                            }
                            Border l_Border = new Border(l_CountryIdInteger, l_BorderCountries);
                            d_Map.getListOfBorders().add(l_Border);
                        }
                    }
                }
            }
            l_Br.close();
            l_Fr.close();
        }
    }


    /**
     * Private method to write the map file
     *
     * @param p_MapFile the map file object
     */
    public void writeMapFile(File p_MapFile) {
        try {
            System.out.println(d_Map);

            FileWriter l_FileWriter = new FileWriter(p_MapFile);
            BufferedWriter l_BufferedWriter = new BufferedWriter(l_FileWriter);

            String l_Content = "[Map]" + "\r\n";
            l_Content += "author=Team11 \r\n";

            l_Content += "\r\n" + HEADER_CONTINENTS + "\r\n";
            System.out.println(d_Map.getListOfContinents().size());
            for (Continent l_Continent : d_Map.getListOfContinents()) {
                l_Content += l_Continent.getName() + "=" + l_Continent.getArmyCount() + "\r\n";
            }

            l_Content += "\r\n" + HEADER_TERRITORIES + "\r\n";
            for (Country l_Country : d_Map.getListOfCountries()) {
                String l_BorderCountries = getBorderCountryNames(l_Country);
                l_Content += l_Country.getName() + l_Country.getX() + " " + l_Country.getY() + "," + l_Country.getName() + l_BorderCountries + " \r\n";
            }


            l_Content += " \r\n";
            l_BufferedWriter.write(l_Content);
            l_BufferedWriter.close();
            l_FileWriter.close();
        } catch (IOException io) {

        }
    }

    /**
     * Helper class to find the border country names that can be used to append to the territory line
     *
     * @param p_Country country object
     * @return borders for the country provided
     */
    private String getBorderCountryNames(Country p_Country) {
        StringBuilder borderString = new StringBuilder();
        for (Border l_Border : d_Map.getListOfBorders()) {
            if (l_Border.getCountryId() == p_Country.getCountryID()) {
                //found, now create string
                for (int l_BorderCountryID : l_Border.getNeighbours()) {
                    for (Country l_Country : d_Map.getListOfCountries()) {
                        borderString.append("," + l_Country.getName());
                    }
                }
                break;
            }
        }
        return borderString.toString();
    }
}
