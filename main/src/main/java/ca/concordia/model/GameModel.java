package ca.concordia.model;

import ca.concordia.Observable;

/**
 * This is the Model class of MVC that extends Observable class
 */
public class GameModel extends Observable {

    private String d_DisplayString;

    /**
     * This method sets the updated DisplayString
     *
     * @param p_DisplayString This is to be displayed
     */

    public void setUpdateForViews(String p_DisplayString) {
        this.d_DisplayString = p_DisplayString;
        notifyObservers(this);
    }

    /**
     * Display the new updated String after the view has been notified
     *
     * @return d_DisplayString display new String after being updated
     */
    public String getDisplayString() {
        return d_DisplayString;
    }
}
