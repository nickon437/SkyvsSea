package skyvssea.model.piece;

public class MediumEagle extends Eagle implements MediumCharacter {
	public MediumEagle() {
		//TODO: create SpecialEffect object
		super("Medium Eagle", DEFAULT_LEVEL, DEFAULT_NUM_MOVE, DEFAULT_ATTACK_RANGE, null, SPECIAL_EFFECT_COOLDOWN);
	}
}
