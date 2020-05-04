package skyvssea.model.specialeffect;

import skyvssea.model.piece.AbstractPiece;

public class SpecialEffectBase implements SpecialEffect {
	private int effectiveDuration;
	private String name;
	
	public SpecialEffectBase(String name, int effectiveDuration) {
		this.name = name;
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
	public void apply(AbstractPiece target) {
		// empty
	}

	@Override
	public void remove(AbstractPiece target) {
		// empty
	}

	@Override
	public int getEffectiveDuration() {
		return effectiveDuration;
	}

	@Override
	public String getName() {
		return name;
	}

}
