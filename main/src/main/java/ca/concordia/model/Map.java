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

    private List<Continent> listOfContinents;
    private List<Country> listOfCountries;
    private List<Border> listOfBorders;

    /**
     *Constructor generates  an empty list with an initial capacity of ten for storing :
     * 1.Continents
     * 2.Countries
     * 3.Borders.
     */
    public Map() {
        this.listOfContinents = new ArrayList<Continent>();
        this.listOfCountries = new ArrayList<Country>();
        this.listOfBorders = new ArrayList<Border>();
    }

    /**
     *This method shows the details of continents present in a map.
     * additionally, returns the list of continents present in a map.
     * @return the list of continents
     */
    public List<Continent> getListOfContinents() {
        return listOfContinents;
    }

    /**
     *This method sets the list of continent by implementing Continent class.
     * @param listOfContinents initialising the continents in the list.
     */
    public void setListOfContinents(List<Continent> listOfContinents) {
        this.listOfContinents = listOfContinents;
    }

    /**
     *This method shows the details of countries present in a map.
     * Also, returns the list of countries present in a map.
     * @return the list of continents
     */
    public List<Country> getListOfCountries() {
        return listOfCountries;
    }

    /**
     *This method sets the list of continent by implementing Continent class.
     * @param listOfCountries initialising the countries in the list to this method.
     */
    public void setListOfCountries(List<Country> listOfCountries) {
        this.listOfCountries = listOfCountries;
    }

    /**
     * This method shows the details of borders present in a map.
     * It returns the list of borders present in a map.
     * @return the list of borders.
     */
    public List<Border> getListOfBorders() {
        return listOfBorders;
    }

    /**
     * This method sets the list of continent by implementing Border class.
     * @param listOfBorders initialising the borders in the list.
     */
    public void setListOfBorders(List<Border> listOfBorders) {
        this.listOfBorders = listOfBorders;
    }

    /**
     *This method shows the adjacency between the countries and returns the connected graph.
     * @return graph showing adjacency.
     */
    public Graph getAdjacencyMatrix() {
        int numberOfCountries = this.getListOfCountries().size();
        Graph graph = new Graph(numberOfCountries);
        for (int i = 0; i < this.getListOfBorders().size(); i++) {
            Border border = this.getListOfBorders().get(i);
            for (int countryId : border.getNeighbours()) {
                graph.addEdge(border.getCountryId(), countryId);
            }

        }
        return graph;
    }
}
