package skyvssea.model.observer;

import skyvssea.model.EventType;

public interface Observer {
    void update(Subject subject, EventType event, Object arg);
}
