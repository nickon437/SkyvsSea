package skyvssea.model.command;

import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.specialeffect.SpecialEffectObject;
import skyvssea.model.specialeffect.TargetType;

public class ActivatePassiveEffectCommand extends AbstractPassiveEffectCommand {
	public ActivatePassiveEffectCommand(AbstractPiece piece) {
		super(piece);
	}
	
	@Override
	public void execute() {
		piece.setPassiveEffectActivated(true);
		handlePassiveEffectForSelf();
	}

	@Override
	public void undo() {
		piece.setPassiveEffectActivated(false);
		handlePassiveEffectForSelf();
	}
	
	/**
	 * Handle applying/removing passive effect to/from itself if the passive effect is TargetType.SELF
	 */
	private void handlePassiveEffectForSelf() {
		SpecialEffectObject passiveEffect = piece.getPassiveEffect();
		if (passiveEffect.getTargetType() == TargetType.SELF) {
			if (piece.isPassiveEffectActivated()) {
				// No need to duplicate passiveEffect since it is only applied to the piece itself
				piece.getSpecialEffectManagerProxy().add(passiveEffect); 
			} else {
				piece.getSpecialEffectManagerProxy().remove(passiveEffect); 
			}			
		}
	}

}
