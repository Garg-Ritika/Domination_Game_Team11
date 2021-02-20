package ca.concordia.model;

/**
 * This class contains details of the continent
 *
 * @author to be updated
 *
 */
public class Continent {

    // TODO : why continents doesn't have unique-id like countries,
    // TODO : can I assume their id (starting from 1 ..) as the order goes in the .com.riskgame.org.riskgame.model.map file ?
    // final because continent name cannot be changed, but color and army-count can change
    private int d_ID;
    private String d_name;
    private String d_color;
    private int d_armyCount;

    /**
     *Constructor initializes Id and name member variables of Continent class
     *
     * @param p_ID
     * @param p_name
     */
    public Continent(int p_ID, String p_name) {
        this.d_ID = p_ID;
        this.d_name = p_name;
    }

    /**
     * Constructor initializes all the member variables of Continent class
     *
     * @param p_ID
     * @param p_name
     * @param p_armyCount
     * @param p_color
     */
    public Continent(int p_ID, String p_name, int p_armyCount, String p_color) {
        this.d_ID = p_ID;
        this.d_name = p_name;
        this.d_armyCount = p_armyCount;
        this.d_color = p_color;
    }

    /**
     * This method returns the id of the continent
     *
     * @return d_ID
     */
    public int getID() {
        return d_ID;
    }

    /**
     * This method sets the id of the continent
     *
     * @param p_countryID
     */
    public void setID(int p_countryID) {
        this.d_ID = p_countryID;
    }

    /**
     * This method returns the name of the continent
     *
     * @return d_name
     */
    public String getName() {
        return d_name;
    }

    /**
     * This method sets the name of the continent
     *
     * @param p_name
     */
    public void setName(String p_name) {
        this.d_name = p_name;
    }

    /**
     * This method sets the color of the continent
     *
     * @param p_color
     */
    public void setColor(String p_color) {
        this.d_color = p_color;
    }

    /**
     * This method returns the color of the continent
     *
     * @return d_color
     */
    public String getColor() {
        return d_color;
    }

    /**
     * This method sets the army count.
     *
     * @param p_armyCount
     */
    public void setArmyCount(int p_armyCount) {
        this.d_armyCount = p_armyCount;
    }

    /**
     * This method returns the army count
     *
     * @return d_armyCount
     */
    public int getArmyCount() {
        return d_armyCount;
    }
}
