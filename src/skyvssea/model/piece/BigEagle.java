package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;
import skyvssea.model.SpecialEffectFactory;
import skyvssea.model.specialeffect.SpecialEffectTimer;
import skyvssea.model.specialeffect.ChangeLevelDecorator;
import skyvssea.model.specialeffect.ChangeMoveRangeDecorator;
import skyvssea.model.specialeffect.SpecialEffect;

public class BigEagle extends Eagle implements BigCharacter {
	public BigEagle() {
		super("Big Eagle", DEFAULT_LEVEL, DEFAULT_NUM_MOVE, DEFAULT_ATTACK_RANGE,
				new ChangeMoveRangeDecorator(0.5, new SpecialEffectTimer(SpecialEffect.DEFAULT_CASTER_TURN)),
				SPECIAL_EFFECT_COOLDOWN);
	}
}
