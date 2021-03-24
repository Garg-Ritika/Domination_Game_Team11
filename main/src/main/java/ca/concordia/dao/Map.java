package ca.concordia.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * Map class contains the details of the: <br>
 * 1.list of continents in a map. <br>
 * 2.list of countries in a map. <br>
 * 3.list of borders in a map. <br>
 * The main feature of this class in that it checks the adjacency between the countries which is required in Graph class
 */
public class Map {

    private List<Continent> d_ListOfContinents;
    private List<Country> d_ListOfCountries;
    private List<Border> d_ListOfBorders;

    /**
     * Constructor generates  an empty list with an initial capacity of ten for storing: <br>
     * 1.Continents. <br>
     * 2.Countries. <br>
     * 3.Borders.
     */
    public Map() {
        this.d_ListOfContinents = new ArrayList<Continent>();
        this.d_ListOfCountries = new ArrayList<Country>();
        this.d_ListOfBorders = new ArrayList<Border>();
    }

    /**
     * This method shows the details of continents present in a map. <br>
     * Additionally, returns the list of continents present in a map.
     *
     * @return d_ListOfContinents the list of continents
     */
    public List<Continent> getListOfContinents() {
        return d_ListOfContinents;
    }

    /**
     * This method sets the list of continent by implementing Continent class.
     *
     * @param p_ListOfContinents initialising the continents in the list.
     */
    public void setListOfContinents(List<Continent> p_ListOfContinents) {
        this.d_ListOfContinents = p_ListOfContinents;
    }

    /**
     * This method shows the details of countries present in a map. <br>
     * Also, returns the list of countries present in a map.
     *
     * @return d_ListOfCountries the list of continents
     */
    public List<Country> getListOfCountries() {
        return d_ListOfCountries;
    }

    /**
     * This method sets the list of continent by implementing Continent class.
     *
     * @param p_ListOfCountries initialising the countries in the list to this method.
     */
    public void setListOfCountries(List<Country> p_ListOfCountries) {
        this.d_ListOfCountries = p_ListOfCountries;
    }

    /**
     * This method shows the details of borders present in a map. <br>
     * It returns the list of borders present in a map.
     *
     * @return d_ListOfBorders the list of borders.
     */
    public List<Border> getListOfBorders() {
        return d_ListOfBorders;
    }

    /**
     * This method sets the list of continent by implementing Border class.
     *
     * @param p_ListOfBorders initialising the borders in the list.
     */
    public void setListOfBorders(List<Border> p_ListOfBorders) {
        this.d_ListOfBorders = p_ListOfBorders;
    }

    /**
     * This getter method is used to find the continent name corresponding to argument passed as territory
     * @param name Territory name is passed as an argument
     * @return l_Continent
     */
    public Territory getTerritoryByName(String name) {
        System.out.println(" --> find terrority named : " + name);
        for (Continent l_Continent : d_ListOfContinents) {
            if (name.equalsIgnoreCase(l_Continent.getName())) {
                System.out.println("--> found continent : " + name);
                return l_Continent;
            }
        }

        for (Country l_Country : d_ListOfCountries) {
            if (name.equalsIgnoreCase(l_Country.getName())) {
                System.out.println("--> found continent : " + name);
                return l_Country;
            }
        }
        System.out.println("Error : unable to find " + name + " in the map ");
        return null;
    }


    /**
     * This method shows the adjacency between the countries and returns the connected graph.
     *
     * @return graph showing adjacency.
     */
    public Graph getAdjacencyMatrix() {
        int l_NumberOfCountries = this.getListOfCountries().size();
        Graph l_Graph = new Graph(l_NumberOfCountries);
        for (int l_I = 0; l_I < this.getListOfBorders().size(); l_I++) {
            Border l_Border = this.getListOfBorders().get(l_I);
            for (int l_CountryId : l_Border.getNeighbours()) {
                l_Graph.addEdge(l_Border.getCountryId(), l_CountryId);
            }

        }
        return l_Graph;
    }

    /**
     * This getter method checks for the passed Continent Name in the Array List of Continents and saves the corresponding continent ID for the matching strings.
     * @param p_ContinentName It is of type string and is used to compare the Continent name from the list of Continents
     * @return l_Graph ContinentSubGraph is returned in l_Graph
     */
    public Graph getContinentSubGraph(String p_ContinentName) {
        List<Integer> l_TempCountryIDList = new ArrayList<>();
        for (Continent l_Continent : this.getListOfContinents()) {
            if (l_Continent.getName().equalsIgnoreCase(p_ContinentName)) {
                int l_ContinentID = l_Continent.getID();

                for (Country l_Country : this.getListOfCountries()) {
                    if (l_Country.getContinentID() == l_ContinentID) {
                        l_TempCountryIDList.add(l_Country.getCountryID());
                    }
                }
            }
        }

        int l_NumberOfCountries = l_TempCountryIDList.size();
        Graph l_Graph = new Graph(l_NumberOfCountries);
        for (int l_TempCountryId : l_TempCountryIDList) {
            for (Border l_Border : this.getListOfBorders()) {
                if (l_TempCountryId == l_Border.getCountryId()) {
                    for (int l_CountryId : l_Border.getNeighbours()) {
                        if (l_TempCountryIDList.contains(l_CountryId)) {
                            l_Graph.addEdge(l_Border.getCountryId(), l_CountryId);
                        }
                    }
                }
            }
        }
        return l_Graph;
    }
}
