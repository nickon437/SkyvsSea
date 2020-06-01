package skyvssea.model.command;

import skyvssea.model.PlayerManager;
import skyvssea.model.Tile;
import skyvssea.model.piece.AbstractPiece;

public class MoveCommand implements Command {
	protected Tile currentTile;
    protected Tile newTile;
    protected AbstractPiece piece;
    protected PlayerManager playerManager;

    public MoveCommand(AbstractPiece piece, Tile currentTile, Tile newTile, PlayerManager playerManager) {
        this.piece = piece;
        this.currentTile = currentTile;
        this.newTile = newTile;
        this.playerManager = playerManager;
    }

    @Override
    public void execute() {
        if (!newTile.equals(currentTile)) {
        	currentTile.removeSpecialEffects();  // Remove passiveEffects from piece if they exist on currentTile
        	currentTile.removeGameObject();
            newTile.setGameObject(piece);
            newTile.applySpecialEffects(playerManager); // Apply passiveEffects to piece if they exist on newTile
        }
    }

    @Override
    public void undo() {
        if (!newTile.equals(currentTile)) {
        	newTile.removeSpecialEffects();
        	newTile.removeGameObject();
            currentTile.setGameObject(piece);
            currentTile.applySpecialEffects(playerManager); 
        }
    }
}
