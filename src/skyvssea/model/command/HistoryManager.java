package skyvssea.model.command;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class HistoryManager {
    // Nick - TODO: Disable undo btn if there is nothing to undo;
    private Deque<List<Command>> history = new LinkedList<>();
    private List<Command> turnCommands = new ArrayList<>();

    public void startNewTurnCommand() {
        history.add(turnCommands);
        turnCommands = new ArrayList<>();
    }

    public void storeAndExecute(Command cmd) {
        turnCommands.add(cmd);
        cmd.execute();
    }

    public void undoMyTurn() {
        if (turnCommands.isEmpty()) {
            List<Command> currentTurnCommands = history.removeLast();
            undoTurn(currentTurnCommands);
        } else {
            undoTurn(turnCommands);
        }

        if (!history.isEmpty()) {
            undoTurn(history.removeLast());
        }
    }


    public void undoTurn(List<Command> currentTurnCommands) {
        for (Command command : currentTurnCommands) {
            command.undo();
        }
    }
}
