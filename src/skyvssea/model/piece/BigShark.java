package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;
import skyvssea.model.SpecialEffectFactory;
import skyvssea.model.specialeffect.SpecialEffectObject;

public class BigShark extends AbstractShark implements BigCharacter {
	private static final SpecialEffectCode SPECIAL_EFFECT_CODE = SpecialEffectCode.DOUBLE_ATTACK_RANGE;
	
	public BigShark() {
		super("Big Shark", DEFAULT_ATTACK_LEVEL, DEFAULT_DEFENCE_LEVEL, DEFAULT_MOVE_RANGE, DEFAULT_ATTACK_RANGE,
				SPECIAL_EFFECT_CODE, SPECIAL_EFFECT_COOLDOWN);
	}
	
	@Override
	public SpecialEffectObject getPassiveEffect() {
		if (passiveEffect == null) {
			passiveEffect = SpecialEffectFactory.getInstance().createPassiveAttackLevelPlus1(this);
		}
		return passiveEffect;
	}
}
