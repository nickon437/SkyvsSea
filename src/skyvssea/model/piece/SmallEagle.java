package skyvssea.model.piece;

import skyvssea.model.specialeffect.SpecialEffectTimer;
import skyvssea.model.specialeffect.ChangeAttackRangeDecorator;
import skyvssea.model.specialeffect.ChangeMoveRangeDecorator;
import skyvssea.model.specialeffect.SpecialEffect;

public class SmallEagle extends Eagle implements SmallCharacter {
	public SmallEagle() {
		super("Small Eagle", DEFAULT_LEVEL, DEFAULT_NUM_MOVE, DEFAULT_ATTACK_RANGE, 
				new ChangeMoveRangeDecorator(0, new ChangeAttackRangeDecorator(0, new SpecialEffectTimer(SpecialEffect.DEFAULT_CASTER_TURN))), 
				SPECIAL_EFFECT_COOLDOWN);
	}
}
