package skyvssea.model.specialeffect;

import com.google.java.contract.Requires;

import skyvssea.model.PlayerManager;
import skyvssea.model.piece.AbstractPiece;

public class ActiveSpecialEffectContainer extends SpecialEffectContainer {
	final static int DEFAULT_CASTER_TURN = 3;
	
	public ActiveSpecialEffectContainer(AbstractPiece caster, String name, TargetType targetType, AbstractSpecialEffectDecorator specialEffectDecorator) {
		super(caster, name, DEFAULT_CASTER_TURN, targetType, specialEffectDecorator);
	}
	
	public ActiveSpecialEffectContainer(AbstractPiece caster, String name, int effectiveDuration, TargetType targetType, AbstractSpecialEffectDecorator specialEffectDecorator) {
		super(caster, name, effectiveDuration, targetType, specialEffectDecorator);
	}
	
	@Requires("effectiveDuration > 0")
	public boolean updateEffectiveDuration() {
		boolean isActive = true;
        effectiveDuration--;
        if (effectiveDuration == 0) {
            isActive = false;
        }
        return isActive;
	}
	
	@Override
	public SpecialEffectContainer copy() {
		return new ActiveSpecialEffectContainer(caster, name, effectiveDuration, targetType, specialEffectDecorator.copy());
	}
}
