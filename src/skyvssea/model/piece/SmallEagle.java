package skyvssea.model.piece;

public class SmallEagle extends Eagle implements SmallCharacter {
	public SmallEagle() {
		//TODO: create SpecialEffect object
		super("Small Eagle", DEFAULT_LEVEL, DEFAULT_NUM_MOVE, DEFAULT_ATTACK_RANGE, null, SPECIAL_EFFECT_COOLDOWN);
	}
}
