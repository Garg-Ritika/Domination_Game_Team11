package ca.concordia.model;

// check details : http://domination.sourceforge.net/makemaps.shtml
// 1 2 3 38  : the first number is the number of country and others are what country it is adjacent too

import java.util.List;

public class Border {

    private int countryId;
    private List<Integer> neighbours;

    public Border(int countryId, List<Integer> neighbours) {
        this.countryId = countryId;
        this.neighbours = neighbours;
    }

    public int getCountryId() {
        return countryId;
    }

    public List<Integer> getNeighbours() {
        return neighbours;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public void setNeighbours(List<Integer> neighbours) {
        this.neighbours = neighbours;
    }

    public boolean addNeighbour(int neighbourCountryId) {
        getNeighbours().add(neighbourCountryId);
        return true;
    }

    public boolean removeNeighbour(int neighbourCountryID) {
        for (int val : getNeighbours()) {
            if (val == neighbourCountryID) {
                getNeighbours().remove(val);
                return true;
            }
        }
        return false;
    }
}
