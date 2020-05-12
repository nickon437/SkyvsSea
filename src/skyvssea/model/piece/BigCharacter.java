package skyvssea.model.piece;

import skyvssea.model.Hierarchy;

public interface BigCharacter {
	Hierarchy DEFAULT_ATTACK_LEVEL = Hierarchy.BIG;
	Hierarchy DEFAULT_DEFENCE_LEVEL = Hierarchy.MEDIUM;
	int DEFAULT_MOVE_RANGE = 2;
    int DEFAULT_ATTACK_RANGE = 1;
    int SPECIAL_EFFECT_COOLDOWN = 4;
}
