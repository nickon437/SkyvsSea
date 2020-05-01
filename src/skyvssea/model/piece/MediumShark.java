package skyvssea.model.piece;

public class MediumShark extends Shark implements MediumCharacter{
	public MediumShark() {
		//TODO: create SpecialEffect object
		super("Medium Shark", MediumCharacter.DEFAULT_LEVEL, MediumCharacter.DEFAULT_NUM_MOVE, MediumCharacter.DEFAULT_ATTACK_RANGE, MediumCharacter.SPECIAL_EFFECT_COOLDOWN, null);
	}
}
