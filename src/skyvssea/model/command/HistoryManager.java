package skyvssea.model.command;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class HistoryManager {
    // Nick - TODO: Disable undo btn if there is nothing to undo;
    // Nick - TODO: Fix bug - Undo during sharking turn will potentially allow shark make the first move
    private Deque<List<Command>> history = new LinkedList<>();
    private List<Command> turnCommands = new ArrayList<>();

    public void startNewTurnCommand() {
        history.add(turnCommands);
        turnCommands = new ArrayList<>();
    }

    public void storeAndExecute(Command cmd) {
        turnCommands.add(cmd);
        cmd.execute();
        System.out.println("After store and execute | turnCommands isEmpty: " + turnCommands.isEmpty());
    }

    public void undoMyTurn() {
        System.out.println("Before undo | turnCommands size: " + turnCommands.size() + " | history size: " + history.size());
        if (turnCommands.isEmpty() && !history.isEmpty()) {
            System.out.println("turnCommands is empty");
            List<Command> currentTurnCommands = history.removeLast();
            undoTurn(currentTurnCommands);
        } else {
            System.out.println("turnCommands is not empty");
            undoTurn(turnCommands);
        }

        if (!history.isEmpty()) {
            System.out.println("history is not empty");
            undoTurn(history.removeLast());
        }
        System.out.println("After undo | turnCommands size: " + turnCommands.size() + " | history size: " + history.size());
    }

    public void undoTurn(List<Command> currentTurnCommands) {
        for (Command command : currentTurnCommands) {
            command.undo();
        }
        turnCommands.clear();
    }
}
