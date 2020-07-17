package skyvssea.model.command;

import skyvssea.model.piece.AbstractPiece;

public class UpdateCounterCommand implements Command {

    private AbstractPiece piece;
    private int currentCounter;
    private int newCounter;

    public UpdateCounterCommand(AbstractPiece piece, int newCounter) {
        this.piece = piece;
        this.currentCounter = piece.getActiveEffectCounter();
        this.newCounter = newCounter;
    }

    @Override
    public void execute() {
        piece.setSpecialEffectCounter(newCounter);
    }

    @Override
    public void undo() {
        piece.setSpecialEffectCounter(currentCounter);
    }
}
