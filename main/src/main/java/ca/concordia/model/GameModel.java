package ca.concordia.model;

import ca.concordia.Observable;

public class GameModel extends Observable {

    private String d_DisplayString;

    public void setUpdateForViews(String p_DisplayString){
        this.d_DisplayString = p_DisplayString;
        notifyObservers(this);
    }

    public String getDisplayString(){
        return d_DisplayString;
    }
}
