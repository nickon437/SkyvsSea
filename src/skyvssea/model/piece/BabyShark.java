package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;

public class BabyShark extends AbstractShark implements BabyCharacter {
	public BabyShark() {
		super("Baby Shark", DEFAULT_LEVEL, DEFAULT_MOVE_RANGE, DEFAULT_ATTACK_RANGE,
				SpecialEffectCode.NOT_APPLICABLE, SPECIAL_EFFECT_COOLDOWN);
	}
}
