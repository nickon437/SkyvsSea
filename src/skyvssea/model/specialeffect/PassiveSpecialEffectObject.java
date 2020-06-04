package skyvssea.model.specialeffect;

import skyvssea.model.piece.AbstractPiece;

public class PassiveSpecialEffectObject extends SpecialEffectObject {
	final static int PASSIVE_DURATION = -1;

	public PassiveSpecialEffectObject(AbstractPiece caster, String name, TargetType targetType, AbstractSpecialEffectDecorator specialEffectDecorator) {
		super(caster, name, PASSIVE_DURATION, targetType, specialEffectDecorator);
	}

//	@Override
//	public boolean updateEffectiveDuration() {
//		return true;
//	}
	
	@Override
	public SpecialEffectObject copy() {
		return new PassiveSpecialEffectObject(caster, name, targetType, specialEffectDecorator.copy());
	}

	@Override
	public void setEffectiveDuration(int newEffectiveDuration) {
		// No implementation since passiveEffect has no real effective duration
		return;
	}

}
