package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;
import skyvssea.model.SpecialEffectFactory;

public class BigEagle extends Eagle implements BigCharacter {
	public BigEagle() {
		super("Big Eagle", DEFAULT_LEVEL, DEFAULT_NUM_MOVE, DEFAULT_ATTACK_RANGE,
                SpecialEffectFactory.getInstance().createSpecialEffect(SpecialEffectCode.DOUBLE_MOVE_RANGE),
				SPECIAL_EFFECT_COOLDOWN);
	}
}
