package skyvssea.model;

public class MediumShark extends Shark implements MediumCharacter{
	public MediumShark() {
		//TODO: create SpecialEffect object
		super("Medium Shark", Team.SHARK, MediumCharacter.DEFAULT_LEVEL, MediumCharacter.DEFAULT_NUM_MOVE, MediumCharacter.DEFAULT_ATTACK_RANGE, MediumCharacter.SPECIAL_EFFECT_COOLDOWN, null);
	}
	
    @Override
    protected void performSpeEff(Piece target) {

    }
}
