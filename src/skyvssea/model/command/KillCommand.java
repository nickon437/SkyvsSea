package skyvssea.model.command;

import java.util.List;

import skyvssea.model.Board;
import skyvssea.model.PlayerManager;
import skyvssea.model.Tile;
import skyvssea.model.piece.AbstractPiece;

public class KillCommand implements Command {
	private AbstractPiece target;
    private Tile targetTile;
    private Board board; 
    private PlayerManager playerManager;
    private boolean isTargetPassiveEffectActivatedAndTransmittable;

    public KillCommand(AbstractPiece target, Tile targetTile, Board board, PlayerManager playerManager) {
        this.target = target;
        this.targetTile = targetTile;
        this.board = board;
		this.playerManager = playerManager;
		
		if (target.isPassiveEffectActivated() && target.isPassiveEffectTransmittable()) {
			isTargetPassiveEffectActivatedAndTransmittable = true;
        } else {
        	isTargetPassiveEffectActivatedAndTransmittable = false;
        }
    }

    @Override
    public void execute() {
        targetTile.removeGameObject();
        
        if (isTargetPassiveEffectActivatedAndTransmittable) {
        	List<Tile> surroundingTiles = board.getSurroundingTiles(targetTile);       
        	surroundingTiles.forEach(tile -> tile.removeSpecialEffect(target.getPassiveEffect()));
        }
    }

    @Override
    public void undo() {
        targetTile.setGameObject(target);
        
        if (isTargetPassiveEffectActivatedAndTransmittable) {
        	List<Tile> surroundingTiles = board.getSurroundingTiles(targetTile);       
        	surroundingTiles.forEach(tile -> tile.addSpecialEffect(target.getPassiveEffect(), playerManager));	        	
        }
    }
}
