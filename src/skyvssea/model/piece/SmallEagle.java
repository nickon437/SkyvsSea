package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;

public class SmallEagle extends AbstractEagle implements SmallCharacter {
	public SmallEagle() {
		super("Small Eagle", DEFAULT_LEVEL, DEFAULT_MOVE_RANGE, DEFAULT_ATTACK_RANGE, SpecialEffectCode.STRENGTHENING,
				SPECIAL_EFFECT_COOLDOWN);
	}
}
