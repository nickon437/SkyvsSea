package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;

public class BigEagle extends Eagle implements BigCharacter {
	public BigEagle() {
		super("Big Eagle", DEFAULT_LEVEL, DEFAULT_NUM_MOVE, DEFAULT_ATTACK_RANGE, SpecialEffectCode.DOUBLE_MOVE_RANGE,
				SPECIAL_EFFECT_COOLDOWN);
	}
}
