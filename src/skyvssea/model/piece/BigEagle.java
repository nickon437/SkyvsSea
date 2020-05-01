package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;
import skyvssea.model.SpecialEffectFactory;

public class BigEagle extends Eagle implements BigCharacter {
	public BigEagle() {
		//TODO: create SpecialEffect object
		super("Big Eagle", BigCharacter.DEFAULT_LEVEL, BigCharacter.DEFAULT_NUM_MOVE,
                BigCharacter.DEFAULT_ATTACK_RANGE, BigCharacter.SPECIAL_EFFECT_COOLDOWN,
                SpecialEffectFactory.getInstance().createSpecialEffect(SpecialEffectCode.DOUBLE_MOVE_RANGE));
	}
}
