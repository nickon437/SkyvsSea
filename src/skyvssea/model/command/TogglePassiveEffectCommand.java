package skyvssea.model.command;

import java.util.List;

import skyvssea.model.Board;
import skyvssea.model.PlayerManager;
import skyvssea.model.Tile;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.specialeffect.SpecialEffectObject;
import skyvssea.model.specialeffect.TargetType;

public class TogglePassiveEffectCommand implements Command {
	private AbstractPiece piece;
	private Tile currentTile;
	private Board board;
	private PlayerManager playerManager;
	
	public TogglePassiveEffectCommand(AbstractPiece piece, Tile currentTile, Board board, PlayerManager playerManager) {
		this.piece = piece;
		this.currentTile = currentTile;
		this.board = board;
		this.playerManager = playerManager;
	}
	
	/**
	 * Handle applying/removing passive effect to/from itself if the passive effect is TargetType.SELF
	 */
	protected void handlePassiveEffectForSelf() {
		SpecialEffectObject passiveEffect = piece.getPassiveEffect();
		if (piece.isPassiveEffectActivated()) {
			// No need to duplicate passiveEffect since it is only applied to the piece itself
			piece.getSpecialEffectManagerProxy().add(passiveEffect); 
		} else {
			piece.getSpecialEffectManagerProxy().remove(passiveEffect); 
		}			
	}

	@Override
	public void execute() {
		boolean passiveEffectStatus = piece.isPassiveEffectActivated();
		piece.setPassiveEffectActivated(!passiveEffectStatus);
		
		if (piece.isPassiveEffectTransmittable()) {
			if (!piece.isPassiveEffectActivated()) {
				// Remove transmittable passiveEffect from surrounding tiles and pieces
				List<Tile> surroundingTiles = board.getSurroundingTiles(currentTile);      
				surroundingTiles.forEach(tile -> tile.removeSpecialEffect(piece.getPassiveEffect()));				
			}
        } else {
        	handlePassiveEffectForSelf();
        }
	}

	@Override
	public void undo() {
		boolean passiveEffectStatus = piece.isPassiveEffectActivated();
		piece.setPassiveEffectActivated(!passiveEffectStatus);
		
		if (piece.isPassiveEffectTransmittable()) {
			if (piece.isPassiveEffectActivated()) {
				List<Tile> surroundingTiles = board.getSurroundingTiles(currentTile);      
				surroundingTiles.forEach(tile -> tile.addSpecialEffect(piece.getPassiveEffect(), playerManager));				
			}
        } else {
        	handlePassiveEffectForSelf();
        }
	}
}
