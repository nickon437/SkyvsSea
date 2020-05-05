package skyvssea.model.specialeffect;

import com.google.java.contract.Ensures;

import skyvssea.model.piece.AbstractPiece;

public abstract class AbstractSpecialEffectDecorator implements SpecialEffect {
	private SpecialEffect specialEffect;
	
	@Ensures("specialEffect != null")
	public AbstractSpecialEffectDecorator(SpecialEffect specialEffect) {
		this.specialEffect = specialEffect;
	}

	@Override
	public void apply(AbstractPiece target) {
		getSpecialEffect().apply(target);			
		
	}
	   
	@Override
	public void remove(AbstractPiece target) {
		getSpecialEffect().remove(target); 			
	}
	
	@Override
	public boolean updateEffectiveDuration() {
		return getSpecialEffect().updateEffectiveDuration();
	}
	
	@Override
	public int getEffectiveDuration() {
		return getSpecialEffect().getEffectiveDuration();
	}

	@Override
	public String getName() {
		return getSpecialEffect().getName();
	}

	public SpecialEffect getSpecialEffect() {
		return specialEffect;
	}
	
}
