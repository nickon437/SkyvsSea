package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;
import skyvssea.model.SpecialEffectFactory;

public class BigShark extends Shark implements BigCharacter {
	public BigShark() {
		super("Big Shark", DEFAULT_LEVEL, DEFAULT_NUM_MOVE, DEFAULT_ATTACK_RANGE,
                SpecialEffectFactory.getInstance().createSpecialEffect(SpecialEffectCode.DOUBLE_ATTACK_RANGE),
				SPECIAL_EFFECT_COOLDOWN);
	}
}
