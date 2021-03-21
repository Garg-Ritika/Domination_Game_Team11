package ca.concordia.patterns.observer;

public class LogEntryBuffer extends Observable {

    private String d_UpdateString;

    public String getUpdate() {
        return d_UpdateString;
    }

    public void setUpdate(String p_UpdateString) {
        d_UpdateString = p_UpdateString;
        notifyObservers(this);
    }

}
