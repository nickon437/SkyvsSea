package skyvssea.model;

import skyvssea.view.ActionPane;

public class Game {
    private GameState currentGameState;

    public Game() {
        setCurrentGameState(GameState.READY_TO_MOVE);
    }

    public void setCurrentGameState(GameState newState) {
        this.currentGameState = newState;
    }

    public GameState getCurrentGameState() { return currentGameState; }
}