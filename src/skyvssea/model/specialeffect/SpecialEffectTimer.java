package skyvssea.model.specialeffect;

import skyvssea.model.piece.Piece;

public class SpecialEffectTimer implements SpecialEffect {
	private Integer effectiveDuration;
	
	public SpecialEffectTimer(int effectiveDuration) {
		this.effectiveDuration = effectiveDuration; 
	}
	
	@Override
    public boolean updateEffectiveDuration() {
        boolean isActive = true;
        effectiveDuration--;
        if (effectiveDuration <= 0) {
            isActive = false;
        }
        return isActive;
    }
    
	@Override
	public void apply(Piece target) {
		// empty
	}

	@Override
	public void remove(Piece target) {
		// empty
	}

}
