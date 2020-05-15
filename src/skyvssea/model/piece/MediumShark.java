package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;

public class MediumShark extends AbstractShark implements MediumCharacter {
	public MediumShark() {
		super("Medium Shark", DEFAULT_LEVEL, DEFAULT_MOVE_RANGE, DEFAULT_ATTACK_RANGE,
				SpecialEffectCode.WEAKENING, SPECIAL_EFFECT_COOLDOWN);
	}
}
