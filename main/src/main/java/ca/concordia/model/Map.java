package ca.concordia.model;

import java.util.ArrayList;
import java.util.List;

/**
 *Map class contains the details of the:
 * 1.list of continents in a map.
 * 2.list of countries in a map.
 * 3.list of borders in a map
 * The main feature of this class in that it checks the adjacency between the countries which is required in Graph class
 */
public class Map {

    private List<Continent> d_ListOfContinents;
    private List<Country> d_ListOfCountries;
    private List<Border> d_ListOfBorders;

    /**
     *Constructor generates  an empty list with an initial capacity of ten for storing :
     * 1.Continents
     * 2.Countries
     * 3.Borders.
     */
    public Map() {
        this.d_ListOfContinents = new ArrayList<Continent>();
        this.d_ListOfCountries = new ArrayList<Country>();
        this.d_ListOfBorders = new ArrayList<Border>();
    }

    /**
     *This method shows the details of continents present in a map.
     * additionally, returns the list of continents present in a map.
     * @return the list of continents
     */
    public List<Continent> getListOfContinents() {
        return d_ListOfContinents;
    }

    /**
     *This method sets the list of continent by implementing Continent class.
     * @param p_ListOfContinents initialising the continents in the list.
     */
    public void setListOfContinents(List<Continent> p_ListOfContinents) {
        this.d_ListOfContinents = p_ListOfContinents;
    }

    /**
     *This method shows the details of countries present in a map.
     * Also, returns the list of countries present in a map.
     * @return the list of continents
     */
    public List<Country> getListOfCountries() {
        return d_ListOfCountries;
    }

    /**
     *This method sets the list of continent by implementing Continent class.
     * @param p_ListOfCountries initialising the countries in the list to this method.
     */
    public void setListOfCountries(List<Country> p_ListOfCountries) {
        this.d_ListOfCountries = p_ListOfCountries;
    }

    /**
     * This method shows the details of borders present in a map.
     * It returns the list of borders present in a map.
     * @return the list of borders.
     */
    public List<Border> getListOfBorders() {
        return d_ListOfBorders;
    }

    /**
     * This method sets the list of continent by implementing Border class.
     * @param p_ListOfBorders initialising the borders in the list.
     */
    public void setListOfBorders(List<Border> p_ListOfBorders) {
        this.d_ListOfBorders = p_ListOfBorders;
    }

    /**
     *This method shows the adjacency between the countries and returns the connected graph.
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
}
