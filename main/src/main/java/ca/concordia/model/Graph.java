package ca.concordia.model;

/**
 * Adjacency Matrix representation of the Maps
 *
 * @author xyz "Please change the name later, it is temp fix for correcting build"
 */

public class Graph {

    private boolean d_AdjMatrix[][];
    private int d_NumberOfCountries; // represent number of vertices

    public Graph(int p_NumberOfCountries) {
        this.d_NumberOfCountries = p_NumberOfCountries +1;
        this.d_AdjMatrix = new boolean[this.d_NumberOfCountries][this.d_NumberOfCountries];
    }

    public void addEdge(int p_I, int p_J) {
        d_AdjMatrix[p_I][p_J] = true;
        d_AdjMatrix[p_J][p_I] = true;
    }

    public void removeEdge(int p_I, int p_J) {
        d_AdjMatrix[p_I][p_J] = false;
        d_AdjMatrix[p_J][p_I] = false;
    }

    public boolean[][] getAdjMatrix() {
        return d_AdjMatrix;
    }

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
