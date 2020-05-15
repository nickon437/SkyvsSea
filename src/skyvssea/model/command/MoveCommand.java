package skyvssea.model.command;

import skyvssea.model.Tile;
import skyvssea.model.piece.AbstractPiece;

public class MoveCommand extends AbstractCommand {

    private Tile currentTile;
    private Tile newTile;
    private AbstractPiece piece;

    public MoveCommand(AbstractPiece piece, Tile currentTile, Tile newTile) {
        this.piece = piece;
        this.currentTile = currentTile;
        this.newTile = newTile;
    }

    @Override
    public void execute() {
        if (!newTile.equals(currentTile)) {
            newTile.setGameObject(piece);
            currentTile.removeGameObject();
        }
    }

    @Override
    public void undo() {
        if (!newTile.equals(currentTile)) {
            System.out.println("undo move");
            currentTile.setGameObject(piece);
            newTile.removeGameObject();
        }
    }
}
