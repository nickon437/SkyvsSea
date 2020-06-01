package skyvssea.model.specialeffect;

import com.google.java.contract.Requires;

import skyvssea.model.piece.AbstractPiece;

public class ActiveSpecialEffectObject extends SpecialEffectObject {
	final static int DEFAULT_CASTER_TURN = 3;
	
	public ActiveSpecialEffectObject(AbstractPiece caster, String name, TargetType targetType, AbstractSpecialEffectDecorator specialEffectDecorator) {
		super(caster, name, DEFAULT_CASTER_TURN, targetType, specialEffectDecorator);
	}
	
	public ActiveSpecialEffectObject(AbstractPiece caster, String name, int effectiveDuration, TargetType targetType, AbstractSpecialEffectDecorator specialEffectDecorator) {
		super(caster, name, effectiveDuration, targetType, specialEffectDecorator);
	}
	
//	@Override
//	@Requires("effectiveDuration > 0")
//	public boolean updateEffectiveDuration() {
//		boolean isActive = true;
//        effectiveDuration--;
//        if (effectiveDuration == 0) {
//            isActive = false;
//        }
//        return isActive;
//	}
	
	@Override
	public SpecialEffectObject copy() {
		return new ActiveSpecialEffectObject(caster, name, effectiveDuration, targetType, specialEffectDecorator.copy());
	}
	
	@Override
	public void setEffectiveDuration(int newEffectiveDuration) {
		effectiveDuration = newEffectiveDuration;
	}
}
