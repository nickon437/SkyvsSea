package skyvssea.model.piece;

public class BabyShark extends Shark implements BabyCharacter {
	public BabyShark() {
		super("Baby Shark", DEFAULT_ATTACK_LEVEL, DEFAULT_DEFENCE_LEVEL, DEFAULT_NUM_MOVE, DEFAULT_ATTACK_RANGE, null, SPECIAL_EFFECT_COOLDOWN);
	}
}
