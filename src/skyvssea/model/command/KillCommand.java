package skyvssea.model.command;

import skyvssea.model.Tile;
import skyvssea.model.piece.AbstractPiece;

public class KillCommand extends AbstractCommand {

    private AbstractPiece target;
    private Tile targetTile;

    public KillCommand(AbstractPiece target, Tile targetTile) {
        this.target = target;
        this.targetTile = targetTile;
    }

    @Override
    public void execute() {
        targetTile.removeGameObject();
    }

    @Override
    public void undo() {
        targetTile.setGameObject(target);
    }
}
