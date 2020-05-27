package skyvssea.model;

import skyvssea.controller.Controller;
import skyvssea.view.ActionPane;
import skyvssea.view.InfoPane;

public class Game {
    private GameState currentGameState;
    private Controller controller;
    private ActionPane actionPane;
    private InfoPane infoPane;

    public Game(Controller controller, ActionPane actionPane, InfoPane infoPane) {
        this.controller = controller;
        this.actionPane = actionPane;
        this.infoPane = infoPane;
        setCurrentGameState(GameState.READY_TO_MOVE);
    }

    public void setCurrentGameState(GameState newState) {
        this.currentGameState = newState;

        infoPane.setCurrentGameState(currentGameState);

        if (newState == GameState.READY_TO_MOVE) {
            actionPane.setRegularActionPaneDisable(true);
            actionPane.hideActionIndicator();

            boolean isUndoEmpty = !controller.getPlayerManager().getCurrentPlayer().isUndoAvailabile();
            boolean hasHistory = controller.getHistoryManager().isUndoAvailable();
            boolean isUndoAvailable = !isUndoEmpty && hasHistory;
            actionPane.setUndoBtnDisable(!isUndoAvailable);

            controller.clearCache();
        } else {
            actionPane.setRegularActionPaneDisable(false);
        }
    }

    public GameState getCurrentGameState() { return currentGameState; }
}