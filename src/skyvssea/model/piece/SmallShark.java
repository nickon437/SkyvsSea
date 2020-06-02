package skyvssea.model.piece;

import skyvssea.model.SpecialEffectFactory;
import skyvssea.model.specialeffect.SpecialEffectObject;

public class SmallShark extends AbstractShark implements SmallCharacter {
	public SmallShark() {
		super("Small Shark", DEFAULT_ATTACK_LEVEL, DEFAULT_DEFENCE_LEVEL, DEFAULT_MOVE_RANGE, DEFAULT_ATTACK_RANGE, SPECIAL_EFFECT_COOLDOWN);
	}
	
	@Override
	public SpecialEffectObject getPassiveEffect() {
		if (passiveEffect == null) {
			passiveEffect = SpecialEffectFactory.getInstance().createPassiveFreezing(this);
		}
		return passiveEffect;
	}
	
	@Override
	public SpecialEffectObject getActiveEffect() {
		if (passiveEffect == null) {
			passiveEffect = SpecialEffectFactory.getInstance().createStrengthening(this);
		}
		return passiveEffect;
	}
}
