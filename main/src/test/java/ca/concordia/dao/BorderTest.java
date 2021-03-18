package ca.concordia.dao;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * This class contains test details of Border class
 */
public class BorderTest {
    private List<Integer> d_Neighbours = new LinkedList<>(Arrays.asList(1, 2, 3, 4));

    /**
     * This method uses assert statement to test expected CountryId with actual CountryId
     */
    @Test
    public void getCountryId() {
        Border l_B = new Border(4, d_Neighbours);
        assertEquals(4, l_B.getCountryId());
    }

    /**
     * This method tests whether expected neighbors matches with the actual neighbors
     */
    @Test
    public void getNeighbours() {
        Border l_B = new Border(4, d_Neighbours);
        assertEquals(d_Neighbours, l_B.getNeighbours());
    }

    /**
     * This method tests the method of addNeighbors in Border clas
     */
    @Test
    public void addNeighbour() {
        Border l_B = new Border(4, d_Neighbours);
        assertTrue(l_B.addNeighbour(7));

    }

    /**
     * This method tests whether removeNeighbor method in border class is correct or not
     */
    @Test
    public void removeNeighbour() {
        Border l_B = new Border(4, d_Neighbours);
        assertTrue(l_B.removeNeighbour(3));

    }

}