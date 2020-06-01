package skyvssea.model.command;

import skyvssea.model.piece.AbstractPiece;

public class ActivatePassiveEffectCommand extends AbstractPassiveEffectCommand {
	public ActivatePassiveEffectCommand(AbstractPiece piece) {
		super(piece);
	}
	
	@Override
	public void execute() {
		piece.setPassiveEffectActivated(true);
	}

	@Override
	public void undo() {
		piece.setPassiveEffectActivated(false);
	}
}
