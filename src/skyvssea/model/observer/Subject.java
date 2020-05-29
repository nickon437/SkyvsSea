package skyvssea.model.observer;

public interface Subject {
    void attach(Observer observer);
    void notifyObservers(Object arg);
}
