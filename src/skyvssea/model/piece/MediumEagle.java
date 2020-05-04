package skyvssea.model.piece;

import skyvssea.model.specialeffect.ChangeMoveRangeDecorator;
import skyvssea.model.specialeffect.SpecialEffect;
import skyvssea.model.specialeffect.SpecialEffectTimer;

public class MediumEagle extends Eagle implements MediumCharacter {
	public MediumEagle() {
		super("Medium Eagle", DEFAULT_ATTACK_LEVEL, DEFAULT_DEFENCE_LEVEL, DEFAULT_NUM_MOVE, DEFAULT_ATTACK_RANGE, 
				new ChangeMoveRangeDecorator(0.5, new SpecialEffectTimer(SpecialEffect.DEFAULT_CASTER_TURN)), SPECIAL_EFFECT_COOLDOWN);
	}
}
