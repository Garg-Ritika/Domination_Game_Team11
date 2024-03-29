package ca.concordia.dao;


/**
 * This class contains details of the country
 * <p>
 * Details of maps can be found at : <a href = "http://domination.sourceforge.net/makemaps.shtml">http://domination.sourceforge.net/makemaps.shtml</a>
 */
public class Country extends Territory {

    private int d_CountryID;
    private String d_Name;
    private int d_ContinentID;
    private int d_X;
    private int d_Y;
    private int d_ArmyCount;
    private Player d_OwnerName;

    /**
     * Constructor initializes all the member variables of Country class
     *
     * @param p_CountryID   countryId
     * @param p_ContinentID continentId
     * @param p_Name        country name
     * @param p_X           x Coordinate Integer of Country's location on map
     * @param p_Y           y Coordinate Integer of Country's location on map
     */
    public Country(int p_CountryID, int p_ContinentID, String p_Name, int p_X, int p_Y) {
        this.d_CountryID = p_CountryID;
        this.d_ContinentID = p_ContinentID;
        this.d_Name = p_Name;
        this.d_X = p_X;
        this.d_Y = p_Y;
    }

    /**
     * This method returns name of the country
     *
     * @return Country Name
     */
    public String getName() {
        return d_Name;
    }

    /**
     * This method sets the name of the country.
     *
     * @param p_Name Country Name
     */
    public void setName(String p_Name) {
        this.d_Name = p_Name;
    }

    /**
     * This method returns id of the country
     *
     * @return Country Id
     */
    public int getCountryID() {
        return d_CountryID;
    }

    /**
     * This method sets the Id of the country.
     *
     * @param p_CountryID Country Id
     */
    public void setCountryID(int p_CountryID) {
        this.d_CountryID = p_CountryID;
    }

    /**
     * This method returns id of the continent
     *
     * @return Continent Id
     */
    public int getContinentID() {
        return d_ContinentID;
    }

    /**
     * This method sets the Id of the continent.
     *
     * @param p_ContinentID Continent Id
     */
    public void setContinentID(int p_ContinentID) {
        this.d_ContinentID = p_ContinentID;
    }

    /**
     * This method returns the x Coordinate Integer of Country's location on map.
     *
     * @return x Coordinate Integer
     */
    public int getX() {
        return d_X;
    }

    /**
     * This method sets the x Coordinate Integer of Country's location on map.
     *
     * @param p_X x Coordinate Integer
     */
    public void setX(int p_X) {
        this.d_X = p_X;
    }

    /**
     * This method returns the y Coordinate Integer of Country's location on map.
     *
     * @return y Coordinate Integer
     */
    public int getY() {
        return d_Y;
    }

    /**
     * This method sets the y Coordinate Integer of Country's location on map.
     *
     * @param p_Y y Coordinate Integer
     */
    public void setY(int p_Y) {
        this.d_Y = p_Y;
    }

    /**
     * This method returns the army count value of the country
     *
     * @return d_ArmyCount
     */
    public int getArmyCount() {
        return d_ArmyCount;
    }

    /**
     * This method sets the army count value of the country
     *
     * @param p_ArmyCount ArmyCount
     */
    public void setArmyCount(int p_ArmyCount) {
        this.d_ArmyCount = p_ArmyCount;
    }

    /**
     * this method gets the player name of the source/target country
     *
     * @return d_OwnerName
     */
    @Override
    public Player getOwner() {
        return d_OwnerName;
    }

    /**
     * this method sets the passed owner name
     *
     * @param p_OwnerName ownerName of a target/source country is passed
     */
    @Override
    public void setOwner(Player p_OwnerName) {
        this.d_OwnerName = p_OwnerName;
    }
}
