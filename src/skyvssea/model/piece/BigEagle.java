package skyvssea.model.piece;

import skyvssea.model.SpecialEffectFactory;
import skyvssea.model.specialeffect.SpecialEffectObject;

public class BigEagle extends AbstractEagle implements BigCharacter {	
	public BigEagle() {
		super("Big Eagle", DEFAULT_ATTACK_LEVEL, DEFAULT_DEFENCE_LEVEL, DEFAULT_MOVE_RANGE, DEFAULT_ATTACK_RANGE, SPECIAL_EFFECT_COOLDOWN);
	}
	
	@Override
	public SpecialEffectObject getPassiveEffect() {
		if (passiveEffect == null) {
			passiveEffect = SpecialEffectFactory.getInstance().createPassiveDefenceLevelPlus1(this);
		}
		return passiveEffect;
	}
	
	@Override
	public SpecialEffectObject getActiveEffect() {
		if (passiveEffect == null) {
			passiveEffect = SpecialEffectFactory.getInstance().createDoubleMoveRange(this);
		}
		return passiveEffect;
	}
}
