package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;

public class MediumEagle extends AbstractEagle implements MediumCharacter {
	public MediumEagle() {
		super("Medium Eagle", DEFAULT_LEVEL, DEFAULT_MOVE_RANGE, DEFAULT_ATTACK_RANGE, SpecialEffectCode.RETARDING,
				SPECIAL_EFFECT_COOLDOWN);
	}
}
