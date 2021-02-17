package ca.concordia.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class BorderTest {
    private List<Integer> neighbours= new LinkedList<>(Arrays.asList(1,2,3,4));


    @Test
    public void getCountryId() {
        Border b= new Border(4, neighbours  );
        assertEquals(4, b.getCountryId());
    }

    @Test
    public void getNeighbours() {
        Border b= new Border(4, neighbours  );
        assertEquals(neighbours, b.getNeighbours());
    }

    @Test
    public void addNeighbour() {
        Border b= new Border(4, neighbours  );
        assertTrue(b.addNeighbour(7));

    }

    @Test
    public void removeNeighbour() {
        Border b= new Border(4, neighbours  );
        assertTrue(b.removeNeighbour(3));

    }

}
