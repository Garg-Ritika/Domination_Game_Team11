package ca.concordia.dao;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for Graph
 */
public class GraphTest {

    Graph d_Graph;

    /**
     * Create an graph instance before starting to test ..
     */
    @Before
    public void before() {
        d_Graph = new Graph(5);
    }

    /**
     * Test method for graph method creating adjacency matrix.
     */
    @Test
    public void testGraphMethods() {
        assertEquals(36, d_Graph.size());
        assertNotNull(d_Graph.getAdjMatrix());
        d_Graph.addEdge(1, 2);
        d_Graph.removeEdge(2, 3);
    }
}
