package skyvssea.model.observer;

import skyvssea.model.EventType;

public interface Observer {
    void update(EventType event, Object arg);
}
