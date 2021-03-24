package ca.concordia.mapworks;

import ca.concordia.dao.Graph;
import ca.concordia.dao.Map;
import ca.concordia.patterns.observer.LogUtil;

import java.util.Stack;

/**
 * Class ValidateMap is used to check whether the given map is directed graph or not.
 * If it is directed graph then it is valid map.
 * DFS algorithm is used to check the connectivity of graph.\
 */
public class ValidateMap {

    private final Map d_Map;

    /**
     * This method load the map in ValidateMap class as it is constructor.
     * Constructor is automatically called whenever  class is called.
     *
     * @param p_Map map is passed as parameter which is to be validated.
     */
    public ValidateMap(Map p_Map) {
        this.d_Map = p_Map;
    }

    /**
     * this method checks if no continent and countries are present in a map
     * Then return as output that no countries and continents are available for our game.
     * Additionally, It check if there are duplicates countries and continents are present or not
     * Also,using Depth First Search checking whether graph is connected or not.
     *
     * @return the boolean false value to show it is invalid map or true if map is valid.
     */
    public boolean validate() {

        if (d_Map.getListOfContinents().size() <= 0) {
            LogUtil.log(" No continents available in the map ");
            return false;
        }

        if (d_Map.getListOfCountries().size() <= 0) {
            LogUtil.log(" No countries available in the map ");
            return false;
        }

        if (!noDuplicates()) {
            return false;
        }

        if (!DFS(this.d_Map.getAdjacencyMatrix())) {
            return false;
        }

        LogUtil.log("map validated successfully ");
        LogUtil.log("map validated successfully ");
        return true;

    }

    /**
     * This is a method to check if duplicate countries or continents exists ..
     * If found it prints Duplicate continent/country found.
     */
    private boolean noDuplicates() {

        for (int l_I = 0; l_I < d_Map.getListOfContinents().size() - 1; l_I++) {
            for (int l_J = l_I + 1; l_J < d_Map.getListOfContinents().size(); l_J++) {

                if (d_Map.getListOfContinents().get(l_I).getName().equals(d_Map.getListOfContinents().get(l_J).getName())) {
                    LogUtil.log("duplicate continent found: " + d_Map.getListOfContinents().get(l_I).getID());
                    return false;
                }
            }
        }

        for (int l_I = 0; l_I < d_Map.getListOfCountries().size() - 1; l_I++) {
            for (int l_J = l_I + 1; l_J < d_Map.getListOfCountries().size(); l_J++) {

                if (d_Map.getListOfCountries().get(l_I).getName().equals(d_Map.getListOfCountries().get(l_J).getName())) {
                    LogUtil.log("duplicate country found : " + d_Map.getListOfCountries().get(l_I).getCountryID());
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Use depth-first-search in connected graph  to find whether it is connected or not.
     *
     * @param p_Graph given graph is passed as an parameter
     * @return connected - prints the output graph is connected or it is not connected
     */
    private boolean DFS(Graph p_Graph) {
        if (p_Graph.size() < 1) {
            return false;
        }

        Stack<Integer> l_Stack = new Stack<Integer>();
        int l_Source = 1;
        int l_NodeCount = p_Graph.getAdjMatrix()[l_Source].length - 1;
        boolean[][] l_Matrix = p_Graph.getAdjMatrix();
        boolean l_Visited[] = new boolean[l_NodeCount + 1];
        int l_Element = l_Source;
        int l_I = l_Source;
        l_Visited[l_Source] = true;
        l_Stack.push(l_Source);
        while (!l_Stack.isEmpty()) {
            l_Element = l_Stack.peek();
            l_I = l_Element;
            while (l_I <= l_NodeCount) {
                if (l_Matrix[l_Element][l_I] == true && l_Visited[l_I] == false) {
                    l_Stack.push(l_I);
                    l_Visited[l_I] = true;
                    l_Element = l_I;
                    l_I = 1;
                    continue;
                }
                l_I++;
            }
            l_Stack.pop();
        }

        boolean l_Connected = false;
        for (int l_Node = 1; l_Node <= l_NodeCount; l_Node++) {
            if (l_Visited[l_Node] == true) {
                l_Connected = true;
            } else {
                l_Connected = false;
                break;
            }
        }

        if (l_Connected) {
            LogUtil.log("it is a connected graph");
        } else {
            LogUtil.log("It is NOT a connected graph");
        }
        return l_Connected;
    }

}