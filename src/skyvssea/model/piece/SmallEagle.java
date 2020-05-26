package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;

public class SmallEagle extends AbstractEagle implements SmallCharacter {
	private static final SpecialEffectCode SPECIAL_EFFECT_CODE = SpecialEffectCode.FREEZING;
	
	public SmallEagle() {
		super("Small Eagle", DEFAULT_LEVEL, DEFAULT_ATTACK_LEVEL, DEFAULT_DEFENCE_LEVEL, DEFAULT_MOVE_RANGE,
				DEFAULT_ATTACK_RANGE, SPECIAL_EFFECT_CODE, SPECIAL_EFFECT_COOLDOWN);
	}
}
