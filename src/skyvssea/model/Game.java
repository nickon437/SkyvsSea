package skyvssea.model;

public class Game {
    private GameState currentGameState;

    public void setCurrentGameState(GameState newState) {
        this.currentGameState = newState;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }
}