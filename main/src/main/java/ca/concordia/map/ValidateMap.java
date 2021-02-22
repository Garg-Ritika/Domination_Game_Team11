package ca.concordia.map;
import ca.concordia.model.Border;
import ca.concordia.model.Graph;
import ca.concordia.model.Map;

import java.util.Stack;

/**
 * Class ValidateMap is used to check whether the given map is directed graph or not.
 * If it is directed graph then it is valid map.
 * DFS algorithm is used to check the connectivity of graph.\
 * @author Satinder Pal Singh
 */
public class ValidateMap {

    private final Map map;

    /**
     * This method load the map in ValidateMap class as it is constructor.
     *Constructor is automatically called whenever  class is called.
     * @param map map is passed as parameter which is to be validated.
     */
    public ValidateMap(Map map) {
        this.map = map;
    }

    /**
     * this method checks if no continent and countries are present in a map
     * Then return as output that no countries and continents are available for our game.
     * Additionally, It check if there are duplicates countries and continents are present or not
     * Also,using Depth First Search checking whether graph is connected or not.
     * @return the boolean false value to show it is invalid map or true if map is valid.
     */
    public boolean validate() {

        if (map.getListOfContinents().size() < 0 || map.getListOfCountries().size() < 0) {
            System.out.println(" No countries or continents available ");
            return false;
        }

        if (!noDuplicates()) {
            return false;
        }

        if (!DFS(this.map.getAdjacencyMatrix())) {
            return false;
        }

        return true;

    }

    /**
     * This is a method to check if duplicate countries or continents exists ..
     * If found it prints Duplicate continent/country found.
     */
    private boolean noDuplicates() {

        for (int i = 0; i < map.getListOfContinents().size() - 1; i++) {
            for (int j = i + 1; j < map.getListOfContinents().size(); j++) {

                if (map.getListOfContinents().get(i).getID() == map.getListOfContinents().get(j).getID()) {
                    System.out.println("duplicate continent found: " + map.getListOfContinents().get(i).getID());
                    return false;
                }
            }
        }

        for (int i = 0; i < map.getListOfCountries().size() - 1; i++) {
            for (int j = i + 1; j < map.getListOfCountries().size(); j++) {

                if (map.getListOfCountries().get(i).getCountryID() == map.getListOfCountries().get(j).getCountryID()) {
                    System.out.println("duplicate country found : " + map.getListOfCountries().get(i).getCountryID());
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Use depth-first-search in directed connected graph  to find whether it is connected or not.
     * @param graph given graph is passed as an parameter
     * @return connected - prints the output graph is connected or it is not connected
     */
    private boolean DFS(Graph graph) {
        if (graph.size() < 1) {
            return false;
        }

        Stack<Integer> stack = new Stack<Integer>();
        int source = 1; // assume the source starts from country ID 1
        int nodeCount = graph.getAdjMatrix()[source].length - 1; // another way of finding number of countries, starting from 0
        boolean[][] matrix = graph.getAdjMatrix();
        boolean visited[] = new boolean[nodeCount + 1];
        int element = source;
        int i = source;

        // use stack to create a visited list
        visited[source] = true;
        stack.push(source);
        while (!stack.isEmpty()) {
            element = stack.peek();
            i = element;

            while (i <= nodeCount) {

                if (matrix[element][i] == true && visited[i] == false) {
                    stack.push(i);
                    visited[i] = true;
                    element = i;
                    i = 1;
                    continue;
                }
                i++;
            }
            stack.pop();
        }

        // find connectivity from visited list
        boolean connected = false;
        for (int node = 1; node <= nodeCount; node++) {
            if (visited[node] == true) {
                connected = true;
            } else {
                connected = false;
                break;
            }
        }

        if (connected) {
            System.out.println("it is a connected graph");
        } else {
            System.out.println("It is NOT a connected graph");
        }
        return connected;
    }

}