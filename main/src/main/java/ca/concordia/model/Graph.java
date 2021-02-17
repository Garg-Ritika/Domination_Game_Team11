package ca.concordia.model;

/**
 * Adjacency Matrix representation of the Maps
 *
 * @author xyz "Please change the name later, it is temp fix for correcting build"
 */

public class Graph {

    private boolean adjMatrix[][];
    private int numberOfCountries; // represent number of vertices

    public Graph(int numberOfCountries) {
        this.numberOfCountries = numberOfCountries+1;
        this.adjMatrix = new boolean[this.numberOfCountries][this.numberOfCountries];
    }

    public void addEdge(int i, int j) {
        adjMatrix[i][j] = true;
        adjMatrix[j][i] = true;
    }

    public void removeEdge(int i, int j) {
        adjMatrix[i][j] = false;
        adjMatrix[j][i] = false;
    }

    public boolean[][] getAdjMatrix() {
        return adjMatrix;
    }

    public int size() {
        return this.numberOfCountries * this.numberOfCountries;
    }

    /**
     * Create string format of Adjacency matrix
     *
     * @return matrix string
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfCountries; i++) {
            sb.append(i + ": ");
            for (boolean j : adjMatrix[i]) {
                sb.append((j ? 1 : 0) + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
