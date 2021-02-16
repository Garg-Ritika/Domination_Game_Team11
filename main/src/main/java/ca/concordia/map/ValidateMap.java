package ca.concordia.map;

import ca.concordia.model.Border;
import ca.concordia.model.Graph;
import ca.concordia.model.Map;

import java.util.Stack;

public class ValidateMap {

    private final Map map;

    public ValidateMap(Map map) {
        this.map = map;
    }

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
     * method to check if duplicate countries or continents exists ..
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
     *
     * @param graph
     * @return
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