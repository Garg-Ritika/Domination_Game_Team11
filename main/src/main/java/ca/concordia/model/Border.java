package ca.concordia.model;


import java.util.List;

/**
 * This class contains details of the border
 * //check details : http://domination.sourceforge.net/makemaps.shtml
 *  1 2 3 38  : the first number is the number of country and others are what country it is adjacent too
 */
public class Border {

    private int d_CountryId;
    private List<Integer> d_Neighbours;

    /**
     * Constructor initializes list of neighbors and the countryId
     *
     * @param p_CountryId  initializing the CountryID
     * @param p_Neighbours initializing the neighbour list with corresponding CountryId
     */
    public Border(int p_CountryId, List<Integer> p_Neighbours) {
        this.d_CountryId = p_CountryId;
        this.d_Neighbours = p_Neighbours;
    }

    /**
     * This method returns the CountryId
     *
     * @return CountryId
     */
    public int getCountryId() {

        return d_CountryId;
    }

    /**
     * This method sets the ID of the country.
     *
     * @param p_CountryId CountryId
     */
    public void setCountryId(int p_CountryId) {
        this.d_CountryId = p_CountryId;
    }

    /**
     * This method returns the Neighbours list associated with a country
     *
     * @return list of Neighbours
     */
    public List<Integer> getNeighbours() {
        return d_Neighbours;
    }

    /**
     * This method sets the Neighbours list of the country
     *
     * @param p_Neighbours list of Neighbours of the country
     */
    public void setNeighbours(List<Integer> p_Neighbours) {
        this.d_Neighbours = p_Neighbours;
    }

    /**
     * This method adds the NeighbourCountryID to the Neighbours list of the country
     *
     * @param p_NeighbourCountryId Neighbours Country ID
     * @return true if neighbourCountryId is succesfully added to lit of Neighbours
     */
    public boolean addNeighbour(int p_NeighbourCountryId) {
        getNeighbours().add(p_NeighbourCountryId);
        return true;
    }

    /**
     * This method removes the NeighbourCountryId value from the Neighbours list of the country if exists
     *
     * @param p_NeighbourCountryId Neighbours Country ID associated with a country to be removed
     * @return true if the neighbourCountryId is successfully deleted i.e it exists else return false
     */
    public boolean removeNeighbour(int p_NeighbourCountryId) {
        for (int l_Val : getNeighbours()) {
            if (l_Val == p_NeighbourCountryId) {
                getNeighbours().remove(l_Val);
                return true;
            }
        }
        return false;
    }

    /**
     * This method find the NeighbourCountryId value from the Neighbours list of the country if exists
     *
     * @param p_NeighbourCountryId Neighbours Country ID associated with a country to be checked
     * @return true if the the neighbour id is found as an existing neighbour
     */
    public boolean isNeighbour(int p_NeighbourCountryId) {
        for (int l_Val : getNeighbours()) {
            if (l_Val == p_NeighbourCountryId) {
                return true;
            }
        }
        return false;
    }
}
