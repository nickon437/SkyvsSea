package skyvssea.model.piece;

public class BabyEagle extends Eagle implements BabyCharacter{
	public BabyEagle() {
		super("Baby Eagle", BabyCharacter.DEFAULT_LEVEL, BabyCharacter.DEFAULT_NUM_MOVE, BabyCharacter.DEFAULT_ATTACK_RANGE, BabyCharacter.SPECIAL_EFFECT_COOLDOWN, null);
	}
}
