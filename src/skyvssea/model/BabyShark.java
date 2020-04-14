package skyvssea.model;

public class BabyShark extends Shark implements BabyCharacter{
	public BabyShark() {
		super(BabyCharacter.DEFAULT_LEVEL, BabyCharacter.DEFAULT_NUM_MOVE, BabyCharacter.DEFAULT_ATTACK_RANGE, BabyCharacter.SPECIAL_EFFECT_COOLDOWN, null);
        super.name = "Baby Shark";
	}
	
    @Override
    protected void performSpeEff(Piece target) {

    }
}
