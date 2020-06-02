package skyvssea.model.piece;

import skyvssea.model.specialeffect.SpecialEffectObject;

public class BabyShark extends AbstractShark implements BabyCharacter {
	public BabyShark() {
		super("Baby Shark", DEFAULT_ATTACK_LEVEL, DEFAULT_DEFENCE_LEVEL, DEFAULT_MOVE_RANGE, DEFAULT_ATTACK_RANGE, SPECIAL_EFFECT_COOLDOWN);
	}
	
	@Override
	public SpecialEffectObject getPassiveEffect() {
		// Baby character does not have a passive effect
		return null;
	}
	
	@Override
	public SpecialEffectObject getActiveEffect() {
		return null;
	}
}
