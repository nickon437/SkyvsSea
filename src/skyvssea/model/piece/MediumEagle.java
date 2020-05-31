package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;
import skyvssea.model.SpecialEffectFactory;
import skyvssea.model.specialeffect.SpecialEffectObject;

public class MediumEagle extends AbstractEagle implements MediumCharacter {
	private static final SpecialEffectCode SPECIAL_EFFECT_CODE = SpecialEffectCode.RETARDING;
	
	public MediumEagle() {
		super("Medium Eagle", DEFAULT_ATTACK_LEVEL, DEFAULT_DEFENCE_LEVEL, DEFAULT_MOVE_RANGE, DEFAULT_ATTACK_RANGE,
				SPECIAL_EFFECT_CODE, SPECIAL_EFFECT_COOLDOWN);
	}
	
	@Override
	public SpecialEffectObject getPassiveEffect() {
		if (passiveEffect == null) {
			passiveEffect = SpecialEffectFactory.getInstance().createPassiveAttackLevelUpDefenceLevelDown(this);
		}
		return passiveEffect;
	}
}
