package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;

public class BabyEagle extends AbstractEagle implements BabyCharacter {
	public BabyEagle() {
		super("Baby Eagle", DEFAULT_LEVEL, DEFAULT_MOVE_RANGE, DEFAULT_ATTACK_RANGE,
				SpecialEffectCode.NOT_APPLICABLE, SPECIAL_EFFECT_COOLDOWN);
	}
}
