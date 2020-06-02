package skyvssea.model.piece;

import skyvssea.model.SpecialEffectFactory;
import skyvssea.model.specialeffect.SpecialEffectObject;

public class MediumShark extends AbstractShark implements MediumCharacter {
	public MediumShark() {
		super("Medium Shark", DEFAULT_ATTACK_LEVEL, DEFAULT_DEFENCE_LEVEL, DEFAULT_MOVE_RANGE, DEFAULT_ATTACK_RANGE, SPECIAL_EFFECT_COOLDOWN);
	}
	
	@Override
	public SpecialEffectObject getPassiveEffect() {
		if (passiveEffect == null) {
			passiveEffect = SpecialEffectFactory.getInstance().createPassiveDefenceLevelUpAttackLevelDown(this);
		}
		return passiveEffect;
	}
	
	@Override
	public SpecialEffectObject getActiveEffect() {
		if (passiveEffect == null) {
			passiveEffect = SpecialEffectFactory.getInstance().createWeakening(this);
		}
		return passiveEffect;
	}
}
