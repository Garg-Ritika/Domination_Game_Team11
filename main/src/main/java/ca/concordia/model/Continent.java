package ca.concordia.model;

public class Continent {

    // TODO : why continents doesn't have unique-id like countries,
    // TODO : can I assume their id (starting from 1 ..) as the order goes in the .com.riskgame.org.riskgame.model.map file ?
    // final because continent name cannot be changed, but color and army-count can change
    private int d_ID;
    private String d_name;
    private String d_color;
    private int d_armyCount;

    public Continent(int p_ID, String p_name) {
        this.d_ID = p_ID;
        this.d_name = p_name;
    }

    public Continent(int p_ID, String p_name, int p_armyCount, String p_color) {
        this.d_ID = p_ID;
        this.d_name = p_name;
        this.d_armyCount = p_armyCount;
        this.d_color = p_color;
    }

    /**
     * Getters and Setters
     * @return ID
     */
    public int getID() {
        return d_ID;
    }

    public void setID(int p_countryID) {
        this.d_ID = p_countryID;
    }

    public String getName() {
        return d_name;
    }

    public void setName(String p_name) {
        this.d_name = p_name;
    }

    public void setColor(String p_color) {
        this.d_color = p_color;
    }

    public String getColor() {
        return d_color;
    }

    public void setArmyCount(int p_armyCount) {
        this.d_armyCount = p_armyCount;
    }

    public int getArmyCount() {
        return d_armyCount;
    }
}
