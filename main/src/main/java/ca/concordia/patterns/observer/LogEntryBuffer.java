package ca.concordia.patterns.observer;

/**
 * This is the LogEntryBuffer class which extends Observable and notify observers
 */
public class LogEntryBuffer extends Observable {

    private String d_UpdateString;

    /**
     * This method returns the updated string
     *
     * @return d_UpdateString new updated string
     */
    public String getUpdate() {
        return d_UpdateString;
    }

    /**
     * This method sets the value of string
     * Also it notify observers about this updated string
     *
     * @param p_UpdateString new update string
     */
    public void setUpdate(String p_UpdateString) {
        d_UpdateString = p_UpdateString;
        notifyObservers(this);
    }

}
