package skyvssea.model.piece;

import skyvssea.model.Hierarchy;

public interface BabyCharacter {
	Hierarchy DEFAULT_ATTACK_LEVEL = Hierarchy.BABY;
	Hierarchy DEFAULT_DEFENCE_LEVEL = Hierarchy.BABY;	
	int DEFAULT_NUM_MOVE = 1;
    int DEFAULT_ATTACK_RANGE = 0;
    int SPECIAL_EFFECT_COOLDOWN = 0;
}
