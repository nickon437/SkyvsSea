package skyvssea.model.command;

import skyvssea.model.piece.AbstractPiece;

public class UpdateCounterCommand extends AbstractCommand {

    private AbstractPiece piece;
    private int currentCounter;
    private int newCounter;

    public UpdateCounterCommand(AbstractPiece piece, int newCounter) {
        this.piece = piece;
        this.currentCounter = piece.getSpecialEffectCounter();
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
