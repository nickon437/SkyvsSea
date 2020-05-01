package skyvssea.model.piece;

public class MediumShark extends Shark implements MediumCharacter {
	public MediumShark() {
		//TODO: create SpecialEffect object
		super("Medium Shark", DEFAULT_LEVEL, DEFAULT_NUM_MOVE, DEFAULT_ATTACK_RANGE, null, SPECIAL_EFFECT_COOLDOWN);
	}
}
