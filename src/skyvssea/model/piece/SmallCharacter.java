package skyvssea.model.piece;

import skyvssea.model.Hierarchy;

public interface SmallCharacter {
	Hierarchy DEFAULT_LEVEL = Hierarchy.SMALL;
	Hierarchy DEFAULT_ATTACK_LEVEL = Hierarchy.SMALL;
	Hierarchy DEFAULT_DEFENCE_LEVEL = Hierarchy.BABY;
	int DEFAULT_MOVE_RANGE = 6;
    int DEFAULT_ATTACK_RANGE = 3;
    int SPECIAL_EFFECT_COOLDOWN = 4;
}
