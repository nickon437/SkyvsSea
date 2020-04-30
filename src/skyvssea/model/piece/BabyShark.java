package skyvssea.model.piece;

public class BabyShark extends Shark implements BabyCharacter{
	public BabyShark() {
		super("Baby Shark", BabyCharacter.DEFAULT_LEVEL, BabyCharacter.DEFAULT_NUM_MOVE, BabyCharacter.DEFAULT_ATTACK_RANGE, BabyCharacter.SPECIAL_EFFECT_COOLDOWN, null);
	}
	
    @Override
    protected void performSpeEff(Piece target) {

    }
}
