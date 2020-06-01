package skyvssea.model.command;

import java.util.List;

import skyvssea.model.Board;
import skyvssea.model.PlayerManager;
import skyvssea.model.Tile;
import skyvssea.model.piece.AbstractPiece;

public class DeactivatePassiveEffectCommand extends AbstractPassiveEffectCommand {
	protected Board board;
	protected PlayerManager playerManager;
	
	public DeactivatePassiveEffectCommand(AbstractPiece piece, Board board, PlayerManager playerManager) {
		super(piece);
		this.board = board;
		this.playerManager = playerManager;
	}
	
	@Override
	public void execute() {
		piece.setPassiveEffectActivated(false);
		
		// Remove transmittable passiveEffect from surrounding tiles and pieces
		if (piece.isPassiveEffectTransmittable()) {
        	List<Tile> surroundingTiles = board.getSurroundingTiles(board.getRegisteredTile());      
        	surroundingTiles.forEach(tile -> tile.removeSpecialEffect(piece.getPassiveEffect()));
        }
	}

	@Override
	public void undo() {
		piece.setPassiveEffectActivated(true);
		
		if (piece.isPassiveEffectTransmittable()) {
        	List<Tile> surroundingTiles = board.getSurroundingTiles(board.getRegisteredTile());      
        	surroundingTiles.forEach(tile -> tile.addSpecialEffect(piece.getPassiveEffect(), playerManager));
        }
	}

}
