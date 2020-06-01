package skyvssea.model.command;

import java.util.List;

import skyvssea.model.Board;
import skyvssea.model.PlayerManager;
import skyvssea.model.Tile;
import skyvssea.model.piece.AbstractPiece;

public class DeactivateTransmittablePassiveEffectCommand extends DeactivatePassiveEffectCommand {
	private Tile currentTile;
	
	public DeactivateTransmittablePassiveEffectCommand(AbstractPiece piece, Tile currentTile, Board board, PlayerManager playerManager) {
		super(piece, board, playerManager);
		this.currentTile = currentTile;
	}
	
	@Override
	public void execute() {
		super.execute();
		
		// Remove transmittable passiveEffect from surrounding tiles and pieces
		if (piece.isPassiveEffectTransmittable()) {
        	List<Tile> surroundingTiles = board.getSurroundingTiles(currentTile);      
        	surroundingTiles.forEach(tile -> tile.removeSpecialEffect(piece.getPassiveEffect()));
        }
	}

	@Override
	public void undo() {
		super.undo();
		
		if (piece.isPassiveEffectTransmittable()) {
        	List<Tile> surroundingTiles = board.getSurroundingTiles(currentTile);      
        	surroundingTiles.forEach(tile -> tile.addSpecialEffect(piece.getPassiveEffect(), playerManager));
        }
	}

}
