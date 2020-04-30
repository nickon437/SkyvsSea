package skyvssea.model.piece;

public class BigShark extends Shark implements BigCharacter {
	public BigShark() {
		//TODO: create SpecialEffect object
		super("Big Shark", BigCharacter.DEFAULT_LEVEL, BigCharacter.DEFAULT_NUM_MOVE, BigCharacter.DEFAULT_ATTACK_RANGE, BigCharacter.SPECIAL_EFFECT_COOLDOWN, null);
	}
	
    @Override
    protected void performSpeEff(Piece target) {
    
    }
}
