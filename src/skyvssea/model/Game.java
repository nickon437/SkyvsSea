package skyvssea.model;

public class Game {
    public GameState currentGameState = GameState.READY_TO_MOVE;

    public void setCurrentGameState(GameState newState) { this.currentGameState = newState; }

    public GameState getCurrentGameState() { return currentGameState; }
}