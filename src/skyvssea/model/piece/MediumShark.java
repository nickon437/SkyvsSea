package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;

public class MediumShark extends AbstractShark implements MediumCharacter {
	private static final SpecialEffectCode SPECIAL_EFFECT_CODE = SpecialEffectCode.STRENGTHENING;
	
	public MediumShark() {
		super("Medium Shark", DEFAULT_LEVEL, DEFAULT_ATTACK_LEVEL, DEFAULT_DEFENCE_LEVEL, DEFAULT_MOVE_RANGE,
				DEFAULT_ATTACK_RANGE, SPECIAL_EFFECT_CODE, SPECIAL_EFFECT_COOLDOWN);
	}
}
