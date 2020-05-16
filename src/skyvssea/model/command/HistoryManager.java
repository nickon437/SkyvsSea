package skyvssea.model.command;

import java.util.*;

public class HistoryManager {
    // Nick - TODO: Disable undo btn if there is nothing to undo;
    // Nick - TODO: Fix bug - Undo during sharking turn will potentially allow shark make the first move
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
        if (!history.isEmpty()) {
            if (turnCommands.isEmpty()) {
                undoTurn(history.pop());
            } else {
                undoTurn(turnCommands);
            }
        }

        if (!history.isEmpty()) {
            undoTurn(history.pop());
        }
    }

    private void undoTurn(Stack<Command> currentTurnCommands) {
        while(currentTurnCommands.size() > 0) {
            currentTurnCommands.pop().undo();
        }
        currentTurnCommands.clear();
    }
}
