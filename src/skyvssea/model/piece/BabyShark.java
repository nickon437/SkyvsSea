package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;

public class BabyShark extends AbstractShark implements BabyCharacter {
	private static final SpecialEffectCode SPECIAL_EFFECT_CODE = SpecialEffectCode.NOT_APPLICABLE;
	
	public BabyShark() {
		super("Baby Shark", DEFAULT_ATTACK_LEVEL, DEFAULT_DEFENCE_LEVEL, DEFAULT_MOVE_RANGE, DEFAULT_ATTACK_RANGE, SPECIAL_EFFECT_CODE, SPECIAL_EFFECT_COOLDOWN);
	}

	@Override
	public void performSpecialEffect(AbstractPiece target) {
		// TODO Auto-generated method stub
		
	}
}
