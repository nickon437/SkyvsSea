package skyvssea.model.command;

import skyvssea.model.Board;
import skyvssea.model.PlayerManager;
import skyvssea.model.Tile;
import skyvssea.model.piece.AbstractPiece;

public class MoveCommand implements Command {
	private Tile currentTile;
	private Tile newTile;
    private AbstractPiece piece;
    private PlayerManager playerManager;
    private Command performPassiveEffectCommand;

    public MoveCommand(AbstractPiece piece, Tile currentTile, Tile newTile, PlayerManager playerManager, Board board) {
        this.piece = piece;
        this.currentTile = currentTile;
        this.newTile = newTile;
        this.playerManager = playerManager;
        
        if (piece.isPassiveEffectActivated() && piece.isPassiveEffectTransmittable()) {
        	performPassiveEffectCommand = new PerformPassiveEffectCommand(piece, currentTile, newTile, playerManager, board);
        }
    }

    @Override
    public void execute() {
        if (!newTile.equals(currentTile)) {
        	currentTile.removeSpecialEffects();  // Remove passiveEffects from piece if they exist on currentTile
        	currentTile.removeGameObject();
            newTile.setGameObject(piece);
            newTile.applySpecialEffects(playerManager); // Apply passiveEffects to piece if they exist on newTile
        }
        
        if (performPassiveEffectCommand != null) {
    		performPassiveEffectCommand.execute();
    	}
    }

    @Override
    public void undo() {
    	if (performPassiveEffectCommand != null) {
    		performPassiveEffectCommand.undo();
    	}
    	
        if (!newTile.equals(currentTile)) {
        	newTile.removeSpecialEffects();
        	newTile.removeGameObject();
            currentTile.setGameObject(piece);
            currentTile.applySpecialEffects(playerManager); 
        }
    }
}
