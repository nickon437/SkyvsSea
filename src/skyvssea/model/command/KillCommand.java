package skyvssea.model.command;

import skyvssea.controller.Controller;
import skyvssea.model.PieceManager;
import skyvssea.model.Tile;
import skyvssea.model.piece.AbstractPiece;

public class KillCommand implements Command {

    private AbstractPiece target;
    private Tile targetTile;
    private PieceManager pieceManager;

    public KillCommand(AbstractPiece target, Tile targetTile, Controller controller, PieceManager pieceManager) {
        this.target = target;
        this.targetTile = targetTile;
        this.pieceManager = pieceManager;
    }

    @Override
    public void execute() {
        targetTile.removeGameObject();
        pieceManager.removePiece(target);
    }

    @Override
    public void undo() {
        targetTile.setGameObject(target);
        pieceManager.addPiece(target);
    }
}
