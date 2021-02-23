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
    private String d_Name;
    private String d_Color;
    private int d_ArmyCount;


    /**
     * Constructor initializes all the member variables of Continent class
     *
     * @param p_ID continent id
     * @param p_Name continent name
     * @param p_ArmyCount army count
     * @param p_Color continent color
     */
    public Continent(int p_ID, String p_Name, int p_ArmyCount, String p_Color) {
        this.d_ID = p_ID;
        this.d_Name = p_Name;
        this.d_ArmyCount = p_ArmyCount;
        this.d_Color = p_Color;
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
     * @param p_CountryID country id
     */
    public void setID(int p_CountryID) {
        this.d_ID = p_CountryID;
    }

    /**
     * This method returns the name of the continent
     *
     * @return d_Name
     */
    public String getName() {
        return d_Name;
    }

    /**
     * This method sets the name of the continent
     *
     * @param p_Name country name
     */
    public void setName(String p_Name) {
        this.d_Name = p_Name;
    }

    /**
     * This method sets the color of the continent
     *
     * @param p_Color country color
     */
    public void setColor(String p_Color) {
        this.d_Color = p_Color;
    }

    /**
     * This method returns the color of the continent
     *
     * @return d_Color
     */
    public String getColor() {
        return d_Color;
    }

    /**
     * This method sets the army count.
     *
     * @param p_ArmyCount army count
     */
    public void setArmyCount(int p_ArmyCount) {
        this.d_ArmyCount = p_ArmyCount;
    }

    /**
     * This method returns the army count
     *
     * @return d_ArmyCount
     */
    public int getArmyCount() {
        return d_ArmyCount;
    }
}
