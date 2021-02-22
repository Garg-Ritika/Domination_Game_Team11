package ca.concordia;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that implements the connection/disconnection mechanism between
 * IObservers and observables (subject). It also implements the notification
 * mechanism that the observable will trigger when its state changes.
 */

public class Observable {
    private List<Observer> d_Observers = new ArrayList<Observer>();
    /**
     * attach a view to the model.
     * @param p_o: view to be added to the list of IObservers to be notified.
     */
    public void attach(Observer p_o) {
        this.d_Observers.add(p_o);
    }
    /**
     * detach a view from the model.
     * @param p_o: view to be removed from the list of IObservers.
     */
    public void detach(Observer p_o) {
        if (!d_Observers.isEmpty()) {
            d_Observers.remove(p_o);
        }
    }
    /**
     * Notify all the views attached to the model.
     * @param p_o: object that contains the information to be observed.
     */
    public void notifyObservers(Observable p_o) {
        for (Observer Observer : d_Observers) {
            Observer.update(p_o);
        }
    }
}
