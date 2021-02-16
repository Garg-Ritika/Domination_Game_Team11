package ca.concordia.model;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private List<Continent> listOfContinents;
    private List<Country> listOfCountries;
    private List<Border> listOfBorders;

    public Map() {
        this.listOfContinents = new ArrayList<Continent>();
        this.listOfCountries = new ArrayList<Country>();
        this.listOfBorders = new ArrayList<Border>();
    }


    public List<Continent> getListOfContinents() {
        return listOfContinents;
    }

    public void setListOfContinents(List<Continent> listOfContinents) {
        this.listOfContinents = listOfContinents;
    }

    public List<Country> getListOfCountries() {
        return listOfCountries;
    }

    public void setListOfCountries(List<Country> listOfCountries) {
        this.listOfCountries = listOfCountries;
    }

    public List<Border> getListOfBorders() {
        return listOfBorders;
    }

    public void setListOfBorders(List<Border> listOfBorders) {
        this.listOfBorders = listOfBorders;
    }

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
