package skyvssea.model;

public class MediumEagle extends Eagle implements MediumCharacter {
	public MediumEagle() {
		//TODO: create SpecialEffect object
		super("Medium Eagle", MediumCharacter.DEFAULT_LEVEL, MediumCharacter.DEFAULT_NUM_MOVE, MediumCharacter.DEFAULT_ATTACK_RANGE, MediumCharacter.SPECIAL_EFFECT_COOLDOWN, null);
	}
	
    @Override
    protected void performSpeEff(Piece target) {

    }
}
