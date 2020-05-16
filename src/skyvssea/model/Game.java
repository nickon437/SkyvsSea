package skyvssea.model;

import skyvssea.controller.Controller;
import skyvssea.view.ActionPane;

public class Game {
    private GameState currentGameState;
    private Controller controller;
    private ActionPane actionPane;

    public Game(Controller controller, ActionPane actionPane) {
        this.controller = controller;
        this.actionPane = actionPane;
        setCurrentGameState(GameState.READY_TO_MOVE);
    }

    public void setCurrentGameState(GameState newState) {
        this.currentGameState = newState;
        if (newState == GameState.READY_TO_MOVE) {
            actionPane.setRegularActionPaneDisable(true);
            actionPane.hideActionIndicator();
            controller.clearCache();
        } else {
            actionPane.setRegularActionPaneDisable(false);
        }
    }

    public GameState getCurrentGameState() { return currentGameState; }
}