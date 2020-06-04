package skyvssea.model.observer;

import skyvssea.model.EventType;

public interface Subject {
    void attach(Observer observer);
    void notifyObservers(EventType event, Object arg);
}
