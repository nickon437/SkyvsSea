package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;

public class SmallShark extends Shark implements SmallCharacter {
	public SmallShark() {
		super("Small Shark", DEFAULT_LEVEL, DEFAULT_NUM_MOVE, DEFAULT_ATTACK_RANGE,
				SpecialEffectCode.FREEZING, SPECIAL_EFFECT_COOLDOWN);
	}
}
