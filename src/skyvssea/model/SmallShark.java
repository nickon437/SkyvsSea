package skyvssea.model;

public class SmallShark extends Shark implements SmallCharacter{
	public SmallShark() {
		//TODO: create SpecialEffect object
		super("Small Shark", Team.SHARK, SmallCharacter.DEFAULT_LEVEL, SmallCharacter.DEFAULT_NUM_MOVE, SmallCharacter.DEFAULT_ATTACK_RANGE, SmallCharacter.SPECIAL_EFFECT_COOLDOWN, null);
	}
	
    @Override
    protected void performSpeEff(Piece target) {

    }
}
