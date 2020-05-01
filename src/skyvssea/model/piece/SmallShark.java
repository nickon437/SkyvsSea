package skyvssea.model.piece;

public class SmallShark extends Shark implements SmallCharacter{
	public SmallShark() {
		//TODO: create SpecialEffect object
		super("Small Shark", SmallCharacter.DEFAULT_LEVEL, SmallCharacter.DEFAULT_NUM_MOVE, SmallCharacter.DEFAULT_ATTACK_RANGE, SmallCharacter.SPECIAL_EFFECT_COOLDOWN, null);
	}
}
