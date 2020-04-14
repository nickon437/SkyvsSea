package skyvssea.model;

public class MediumShark extends Shark implements MediumCharacter{
	public MediumShark() {
		//TODO: create SpecialEffect object
		super(MediumCharacter.DEFAULT_LEVEL, MediumCharacter.DEFAULT_NUM_MOVE, MediumCharacter.DEFAULT_ATTACK_RANGE, MediumCharacter.SPECIAL_EFFECT_COOLDOWN, null);
        super.name = "Big Shark";
	}
	
    @Override
    protected void performSpeEff(Piece target) {

    }
}
