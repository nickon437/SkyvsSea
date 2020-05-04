package skyvssea.model.piece;

import skyvssea.model.Hierarchy;

public interface MediumCharacter {
	Hierarchy DEFAULT_ATTACK_LEVEL = Hierarchy.MEDIUM;
	Hierarchy DEFAULT_DEFENCE_LEVEL = Hierarchy.SMALL;
	int DEFAULT_NUM_MOVE = 4;
    int DEFAULT_ATTACK_RANGE = 3;
    int SPECIAL_EFFECT_COOLDOWN = 4;
}
