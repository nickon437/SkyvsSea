package skyvssea.model.piece;

public class MediumEagle extends Eagle implements MediumCharacter {
	public MediumEagle() {
		//TODO: create SpecialEffect object
		super("Medium Eagle", MediumCharacter.DEFAULT_LEVEL, MediumCharacter.DEFAULT_NUM_MOVE, MediumCharacter.DEFAULT_ATTACK_RANGE, MediumCharacter.SPECIAL_EFFECT_COOLDOWN, null);
	}
}
