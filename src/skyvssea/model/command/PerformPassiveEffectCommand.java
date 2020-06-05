package skyvssea.model.command;

import java.util.List;

import com.google.java.contract.Requires;

import skyvssea.model.Board;
import skyvssea.model.PlayerManager;
import skyvssea.model.Tile;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.specialeffect.SpecialEffectObject;

public class PerformPassiveEffectCommand implements Command {
	private Tile currentTile;
	private Tile newTile;
    private AbstractPiece piece;
    private PlayerManager playerManager;
	private List<Tile> tilesAroundCurrentTile;
	private List<Tile> tilesAroundNewTile;
	private boolean isPassiveEffectActivatedInPreviousTurn = false;
	
	public PerformPassiveEffectCommand(AbstractPiece piece, Tile currentTile, Tile newTile, PlayerManager playerManager, Board board) {
		this.piece = piece;
        this.currentTile = currentTile;
        this.newTile = newTile;
        this.playerManager = playerManager;
		tilesAroundCurrentTile = board.getSurroundingTiles(currentTile);
		tilesAroundNewTile = board.getSurroundingTiles(newTile);
	}

	@Requires("piece.isPassiveEffectActivated() && piece.isPassiveEffectTransmittable()")
	@Override
	public void execute() {
		// Determine if the piece activates its passiveEffect in this turn or not
    	for (SpecialEffectObject specialEffect : tilesAroundCurrentTile.get(0).getSpecialEffects()) {
    		if (specialEffect == piece.getPassiveEffect()) {
    			isPassiveEffectActivatedInPreviousTurn = true;
    			break;
    		}
    	}    		
    	
    	// Handle setting up passive effect on tilesAroundNewTile so that it can be applied to a piece moving to any tilesAroundNewTile
    	if (!newTile.equals(currentTile) && isPassiveEffectActivatedInPreviousTurn) {
    		tilesAroundCurrentTile.forEach(tile -> tile.removeSpecialEffect(piece.getPassiveEffect()));					
    	}
    	
    	// If tilesAroundNewTile already has this piece's passiveEffect, this line of code won't have any effect
    	tilesAroundNewTile.forEach(tile -> tile.addSpecialEffect(piece.getPassiveEffect(), playerManager));

	}

	@Override
	public void undo() {
		if (!newTile.equals(currentTile) || !isPassiveEffectActivatedInPreviousTurn) {
    		tilesAroundNewTile.forEach(tile -> tile.removeSpecialEffect(piece.getPassiveEffect()));    		
    	}
    	
    	if (isPassiveEffectActivatedInPreviousTurn) {
    		tilesAroundCurrentTile.forEach(tile -> tile.addSpecialEffect(piece.getPassiveEffect(), playerManager));    		
    	}
	}
}
