package skyvssea.model.piece;

import skyvssea.model.SpecialEffectFactory;
import skyvssea.model.specialeffect.SpecialEffectObject;

public class SmallEagle extends AbstractEagle implements SmallCharacter {
	public SmallEagle() {
		super("Small Eagle", DEFAULT_LEVEL, DEFAULT_ATTACK_LEVEL, DEFAULT_DEFENCE_LEVEL, DEFAULT_MOVE_RANGE, DEFAULT_ATTACK_RANGE, SPECIAL_EFFECT_COOLDOWN);
	}
	
	@Override
	public SpecialEffectObject getPassiveEffect() {
		if (passiveEffect == null) {
			passiveEffect = SpecialEffectFactory.getInstance().createPassiveAntiSpecialEffect(this);
		}
		return passiveEffect;
	}
	
	@Override
	public SpecialEffectObject getActiveEffect() {
		if (activeEffect == null) {
			activeEffect = SpecialEffectFactory.getInstance().createFreezing(this);
		}
		return activeEffect;
	}
}
