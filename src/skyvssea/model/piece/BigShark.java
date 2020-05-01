package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;
import skyvssea.model.SpecialEffectFactory;

public class BigShark extends Shark implements BigCharacter {
	public BigShark() {
		//TODO: create SpecialEffect object
		super("Big Shark", BigCharacter.DEFAULT_LEVEL, BigCharacter.DEFAULT_NUM_MOVE,
                BigCharacter.DEFAULT_ATTACK_RANGE, BigCharacter.SPECIAL_EFFECT_COOLDOWN,
                SpecialEffectFactory.getInstance().createSpecialEffect(SpecialEffectCode.DOUBLE_ATTACK_RANGE));
	}
}
