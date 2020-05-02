package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;

public class MediumShark extends Shark implements MediumCharacter {
	public MediumShark() {
		super("Medium Shark", DEFAULT_LEVEL, DEFAULT_NUM_MOVE, DEFAULT_ATTACK_RANGE,
				SpecialEffectCode.WEAKENING, SPECIAL_EFFECT_COOLDOWN);
	}
}
