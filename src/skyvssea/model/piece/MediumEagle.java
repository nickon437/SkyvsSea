package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;

public class MediumEagle extends Eagle implements MediumCharacter {
	public MediumEagle() {
		super("Medium Eagle", DEFAULT_LEVEL, DEFAULT_NUM_MOVE, DEFAULT_ATTACK_RANGE,
				SpecialEffectCode.RETARDING, SPECIAL_EFFECT_COOLDOWN);
	}
}
