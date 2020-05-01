package skyvssea.model.piece;

public class SmallShark extends Shark implements SmallCharacter {
	public SmallShark() {
		//TODO: create SpecialEffect object
		super("Small Shark", DEFAULT_LEVEL, DEFAULT_NUM_MOVE, DEFAULT_ATTACK_RANGE, null, SPECIAL_EFFECT_COOLDOWN);
	}
}
