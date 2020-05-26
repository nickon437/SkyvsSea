package skyvssea.model.command;

import skyvssea.controller.Controller;
import skyvssea.model.Hierarchy;
import skyvssea.model.PieceManager;
import skyvssea.model.Tile;
import skyvssea.model.piece.AbstractPiece;

public class KillCommand implements Command {

    private AbstractPiece target;
    private Tile targetTile;
    private Controller controller;
    private PieceManager pieceManager;

    public KillCommand(AbstractPiece target, Tile targetTile, Controller controller, PieceManager pieceManager) {
        this.target = target;
        this.targetTile = targetTile;
        this.controller = controller;
        this.pieceManager = pieceManager;
    }

    @Override
    public void execute() {
        targetTile.removeGameObject();

        int remainingPiecesInHierarchy = pieceManager.removePiece(target);
        if (target.getLevel() == Hierarchy.BABY && remainingPiecesInHierarchy == 0) {
            controller.declareWinner();
        }
    }

    @Override
    public void undo() {
        targetTile.setGameObject(target);
        pieceManager.addPiece(target);
    }
}
