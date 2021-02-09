package ca.concordia.model;

/**
 * Details of maps can be found at : http://domination.sourceforge.net/makemaps.shtml
 */

public class Country {

    private int countryID;
    private String name;
    private int continentID;
    private int x;
    private int y;

    public Country(int countryID, int continentID) {
        this.countryID = countryID;
        this.continentID = continentID;
    }

    public Country(int countryID, int continentID, String name, int x, int y) {
        this.countryID = countryID;
        this.continentID = continentID;
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public int getContinentID() {
        return continentID;
    }

    public void setContinentID(int continentID) {
        this.continentID = continentID;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

}
