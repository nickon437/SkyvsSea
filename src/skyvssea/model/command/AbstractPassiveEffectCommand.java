package skyvssea.model.command;

import skyvssea.model.piece.AbstractPiece;

public abstract class AbstractPassiveEffectCommand implements Command {
	protected AbstractPiece piece;
	
	public AbstractPassiveEffectCommand(AbstractPiece piece) {
		this.piece = piece;
	}
}
