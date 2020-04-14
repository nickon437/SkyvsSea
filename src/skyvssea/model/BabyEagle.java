package skyvssea.model;

public class BabyEagle extends Eagle implements BabyCharacter{
	public BabyEagle() {
		super(BabyCharacter.DEFAULT_LEVEL, BabyCharacter.DEFAULT_NUM_MOVE, BabyCharacter.DEFAULT_ATTACK_RANGE, BabyCharacter.SPECIAL_EFFECT_COOLDOWN, null);
        super.name = "Baby Eagle";
	}
	
    @Override
    protected void performSpeEff(Piece target) {

    }
}
