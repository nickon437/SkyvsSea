package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;

public class BigEagle extends AbstractEagle implements BigCharacter {
	private static final SpecialEffectCode SPECIAL_EFFECT_CODE = SpecialEffectCode.DOUBLE_MOVE_RANGE;
	
	public BigEagle() {
		super("Big Eagle", DEFAULT_LEVEL, DEFAULT_ATTACK_LEVEL, DEFAULT_DEFENCE_LEVEL, DEFAULT_MOVE_RANGE,
				DEFAULT_ATTACK_RANGE, SPECIAL_EFFECT_CODE, SPECIAL_EFFECT_COOLDOWN);
	}
}
