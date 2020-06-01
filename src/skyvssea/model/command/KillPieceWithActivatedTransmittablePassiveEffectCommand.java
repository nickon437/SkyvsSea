package skyvssea.model.command;

import java.util.List;

import com.google.java.contract.Requires;

import skyvssea.model.Board;
import skyvssea.model.PlayerManager;
import skyvssea.model.Tile;
import skyvssea.model.piece.AbstractPiece;

public class KillPieceWithActivatedTransmittablePassiveEffectCommand extends KillCommand {
    private Board board; 
    private PlayerManager playerManager;
	
	public KillPieceWithActivatedTransmittablePassiveEffectCommand(AbstractPiece target, Tile targetTile, Board board, PlayerManager playerManager) {
		super(target, targetTile);
		this.board = board;
		this.playerManager = playerManager;
	}
	
	@Requires("target.isPassiveEffectActivated() && target.isPassiveEffectTransmittable()")
    @Override
    public void execute() {
        super.execute();
    	List<Tile> surroundingTiles = board.getSurroundingTiles(targetTile);       
    	surroundingTiles.forEach(tile -> tile.removeSpecialEffect(target.getPassiveEffect()));
    }

    @Override
    public void undo() {
        super.undo();
    	List<Tile> surroundingTiles = board.getSurroundingTiles(targetTile);       
    	surroundingTiles.forEach(tile -> tile.addSpecialEffect(target.getPassiveEffect(), playerManager));	
    }
}
