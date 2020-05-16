package skyvssea.model;

import skyvssea.view.ActionPane;

public class Game {
    private GameState currentGameState;
    private ActionPane actionPane;

    public Game(ActionPane actionPane) {
        this.actionPane = actionPane;
        setCurrentGameState(GameState.READY_TO_MOVE);
    }

    public void setCurrentGameState(GameState newState) {
        this.currentGameState = newState;
        if (newState == GameState.READY_TO_MOVE) {
            actionPane.setDisable(true);
            actionPane.hideActionIndicator();
        } else {
            actionPane.setDisable(false);
        }
    }

    public GameState getCurrentGameState() { return currentGameState; }
}