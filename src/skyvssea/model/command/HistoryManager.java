package skyvssea.model.command;

import java.util.*;

public class HistoryManager {
    private Stack<Stack<Command>> history = new Stack<>();
    private Stack<Command> turnCommands = new Stack<>();

    public void startNewTurnCommand() {
        history.add(turnCommands);
        turnCommands = new Stack<>();
    }

    public void storeAndExecute(Command cmd) {
        turnCommands.push(cmd);
        cmd.execute();
    }

    public void undoToMyTurn() {
        if (isUndoAvailable()) {
            if (turnCommands.isEmpty()) {
            	// Player undoes before making any move in the current turn
            	// Undo both players' latest moves
                undoTurn(history.pop());
                undoTurn(history.pop());
            } else {
            	// Player undoes after it has made any kind of move
                undoTurn(turnCommands);
            }
        }  
    }

    private void undoTurn(Stack<Command> currentTurnCommands) {
        while(currentTurnCommands.size() > 0) {
            currentTurnCommands.pop().undo();
        }
        currentTurnCommands.clear();
    }

    public boolean isUndoAvailable() {
        return history.size() >= 2;
    }
}
