package skyvssea.model.command;

import skyvssea.model.Game;
import skyvssea.model.GameState;

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
//        history.add(cmd);
//        cmd.execute();
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
//        history.removeLast().undo();

        for (Command command : currentTurnCommands) {
            command.undo();
        }

    }
}
