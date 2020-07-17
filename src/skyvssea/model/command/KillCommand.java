package skyvssea.model.command;

import skyvssea.model.Board;
import skyvssea.model.PieceManager;
import skyvssea.model.PlayerManager;
import skyvssea.model.Tile;
import skyvssea.model.piece.AbstractPiece;

import java.util.List;

public class KillCommand implements Command {
	private AbstractPiece target;
    private Tile targetTile;
    private Board board; 
    private PlayerManager playerManager;
    private PieceManager pieceManager;
    private boolean hasTransmittablePassiveEffectActivated;

    public KillCommand(AbstractPiece target, Tile targetTile, Board board, PlayerManager playerManager, PieceManager pieceManager) {
        this.target = target;
        this.targetTile = targetTile;
        this.board = board;
		this.playerManager = playerManager;
		this.pieceManager = pieceManager;
		
		if (target.isPassiveEffectActivated() && target.isPassiveEffectTransmittable()) {
			hasTransmittablePassiveEffectActivated = true;
        } else {
        	hasTransmittablePassiveEffectActivated = false;
        }
    }

    @Override
    public void execute() {
        targetTile.removeGameObject();
        pieceManager.removePiece(target);
        
        if (hasTransmittablePassiveEffectActivated) {
        	List<Tile> surroundingTiles = board.getSurroundingTiles(targetTile);
        	surroundingTiles.forEach(tile -> tile.removeSpecialEffect(target.getPassiveEffect()));
        }
    }

    @Override
    public void undo() {
        targetTile.setGameObject(target);
        pieceManager.addPiece(target);
        
        if (hasTransmittablePassiveEffectActivated) {
        	List<Tile> surroundingTiles = board.getSurroundingTiles(targetTile);       
        	surroundingTiles.forEach(tile -> tile.addSpecialEffect(target.getPassiveEffect(), playerManager));	        	
        }
    }
}
