package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;

public class SmallShark extends AbstractShark implements SmallCharacter {
	public SmallShark() {
		super("Small Shark", DEFAULT_LEVEL, DEFAULT_MOVE_RANGE, DEFAULT_ATTACK_RANGE,
				SpecialEffectCode.FREEZING, SPECIAL_EFFECT_COOLDOWN);
	}
}
