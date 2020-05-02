package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;

public class SmallEagle extends Eagle implements SmallCharacter {
	public SmallEagle() {
		super("Small Eagle", DEFAULT_LEVEL, DEFAULT_NUM_MOVE, DEFAULT_ATTACK_RANGE,
				SpecialEffectCode.STRENGTHENING, SPECIAL_EFFECT_COOLDOWN);
	}
}
