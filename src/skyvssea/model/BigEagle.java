package skyvssea.model;

public class BigEagle extends Eagle implements BigCharacter {
	public BigEagle() {
		//TODO: create SpecialEffect object
		super("Big Eagle", BigCharacter.DEFAULT_LEVEL, BigCharacter.DEFAULT_NUM_MOVE, BigCharacter.DEFAULT_ATTACK_RANGE, BigCharacter.SPECIAL_EFFECT_COOLDOWN, null);
	}
	
    @Override
    protected void performSpeEff(Piece target) {

    }
}
