package ca.concordia.model;

public class Continent {

    // TODO : why continents doesn't have unique-id like countries,
    // TODO : can I assume their id (starting from 1 ..) as the order goes in the .com.riskgame.org.riskgame.model.map file ?
    // final because continent name cannot be changed, but color and army-count can change
    private int ID;
    private String name;
    private String color;
    private int armyCount;

    public Continent(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public Continent(int ID, String name, int armyCount, String color) {
        this.ID = ID;
        this.name = name;
        this.armyCount = armyCount;
        this.color = color;
    }

    /**
     * Getters and Setters
     * @return ID
     */
    public int getID() {
        return ID;
    }

    public void setID(int countryID) {
        this.ID = countryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setArmyCount(int armyCount) {
        this.armyCount = armyCount;
    }

    public int getArmyCount() {
        return armyCount;
    }
}
