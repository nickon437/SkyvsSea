package skyvssea.model.command;

public interface Command {
    void execute();
    void undo();
}
