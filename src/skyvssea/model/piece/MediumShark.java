package skyvssea.model.piece;

import skyvssea.model.specialeffect.ChangeAttackLevelDecorator;
import skyvssea.model.specialeffect.ChangeDefenceLevelDecorator;
import skyvssea.model.specialeffect.SpecialEffect;
import skyvssea.model.specialeffect.SpecialEffectTimer;

public class MediumShark extends Shark implements MediumCharacter {
	public MediumShark() {
		super("Medium Shark", DEFAULT_ATTACK_LEVEL, DEFAULT_DEFENCE_LEVEL, DEFAULT_NUM_MOVE, DEFAULT_ATTACK_RANGE, 
				new ChangeAttackLevelDecorator(-1, new ChangeDefenceLevelDecorator(-1, new SpecialEffectTimer(SpecialEffect.DEFAULT_CASTER_TURN))), 
				SPECIAL_EFFECT_COOLDOWN);
	}
}
