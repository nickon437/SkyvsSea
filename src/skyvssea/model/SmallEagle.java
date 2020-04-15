package skyvssea.model;

public class SmallEagle extends Eagle implements SmallCharacter {
	public SmallEagle() {
		//TODO: create SpecialEffect object
		super("Small Eagle", SmallCharacter.DEFAULT_LEVEL, SmallCharacter.DEFAULT_NUM_MOVE, SmallCharacter.DEFAULT_ATTACK_RANGE, SmallCharacter.SPECIAL_EFFECT_COOLDOWN, null);
	}
	
    @Override
    protected void performSpeEff(Piece target) {

    }
}
