package skyvssea.model.piece;

import skyvssea.model.Hierarchy;

public interface SmallCharacter {
	Hierarchy DEFAULT_LEVEL = Hierarchy.SMALL;
	int DEFAULT_NUM_MOVE = 6;
    int DEFAULT_ATTACK_RANGE = 3;
    int SPECIAL_EFFECT_COOLDOWN = 4;
}
