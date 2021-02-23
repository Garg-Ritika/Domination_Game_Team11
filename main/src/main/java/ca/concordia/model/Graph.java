package ca.concordia.model;

/**
 * Adjacency Matrix representation of the Maps
 */

public class Graph {

    private boolean d_AdjMatrix[][];
    private int d_NumberOfCountries; // represent number of vertices

    /**
     * Constructor initializes the no of Countries associated at a given time to represent matrix representation of map
     *
     * @param p_NumberOfCountries no of Countries in a map
     */
    public Graph(int p_NumberOfCountries) {
        this.d_NumberOfCountries = p_NumberOfCountries + 1;
        this.d_AdjMatrix = new boolean[this.d_NumberOfCountries][this.d_NumberOfCountries];
    }

    /**
     * This method is used to add edges to graph representation for each connected country in a map
     *
     * @param p_I Borders country Id
     * @param p_J country Id
     */
    public void addEdge(int p_I, int p_J) {
        d_AdjMatrix[p_I][p_J] = true;
        d_AdjMatrix[p_J][p_I] = true;
    }

    /**
     * This method is used to remove edges from graph representation for each removed connected country in a map
     *
     * @param p_I Borders country Id
     * @param p_J country Id
     */
    public void removeEdge(int p_I, int p_J) {
        d_AdjMatrix[p_I][p_J] = false;
        d_AdjMatrix[p_J][p_I] = false;
    }

    /**
     * This method returns the matrix formed to check for the validation of the map if it is connected and valid
     *
     * @return the matrix representation of the map
     */
    public boolean[][] getAdjMatrix() {
        return d_AdjMatrix;
    }

    /**
     * This method returns the size of the graph that is of matrix table
     *
     * @return (no of countries)^2
     */
    public int size() {
        return this.d_NumberOfCountries * this.d_NumberOfCountries;
    }

    /**
     * Create string format of Adjacency matrix
     *
     * @return matrix string
     */
    public String toString() {
        StringBuilder l_Sb = new StringBuilder();
        for (int l_I = 0; l_I < d_NumberOfCountries; l_I++) {
            l_Sb.append(l_I + ": ");
            for (boolean l_J : d_AdjMatrix[l_I]) {
                l_Sb.append((l_J ? 1 : 0) + " ");
            }
            l_Sb.append("\n");
        }
        return l_Sb.toString();
    }
}
