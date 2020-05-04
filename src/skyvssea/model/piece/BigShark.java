package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;
import skyvssea.model.SpecialEffectFactory;
import skyvssea.model.specialeffect.SpecialEffectTimer;
import skyvssea.model.specialeffect.ChangeAttackRangeDecorator;
import skyvssea.model.specialeffect.ChangeMoveRangeDecorator;
import skyvssea.model.specialeffect.SpecialEffect;

public class BigShark extends Shark implements BigCharacter {
	public BigShark() {
		super("Big Shark", DEFAULT_ATTACK_LEVEL, DEFAULT_DEFENCE_LEVEL, DEFAULT_NUM_MOVE, DEFAULT_ATTACK_RANGE,
				new ChangeAttackRangeDecorator(2, new SpecialEffectTimer(SpecialEffect.DEFAULT_CASTER_TURN)),
				SPECIAL_EFFECT_COOLDOWN);
	}
}
