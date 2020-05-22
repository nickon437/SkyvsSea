package skyvssea.model.specialeffect;

import skyvssea.model.PlayerManager;
import skyvssea.model.piece.AbstractPiece;

public class PassiveSpecialEffectContainer extends SpecialEffectContainer {
	final static int PASSIVE_DURATION = -1;

	public PassiveSpecialEffectContainer(AbstractPiece caster, String name, TargetType targetType, AbstractSpecialEffectDecorator specialEffectDecorator) {
		super(caster, name, PASSIVE_DURATION, targetType, specialEffectDecorator);
	}

	public boolean updateEffectiveDuration() {
		return true;
	}
	
	@Override
	public boolean usableOnPiece(AbstractPiece target, PlayerManager playerManager) {
		// Prevent a caster getting its own passiveEffect with TargetType.COMRADES
		if (targetType != TargetType.SELF && target.getPassiveEffect().equals(this)) {
			return false;
		}
		
		return super.usableOnPiece(target, playerManager);
	}
	
	@Override
	public SpecialEffectContainer copy() {
		return new PassiveSpecialEffectContainer(caster, name, targetType, specialEffectDecorator.copy());
	}

}
