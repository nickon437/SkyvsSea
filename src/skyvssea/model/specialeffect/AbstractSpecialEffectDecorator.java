package skyvssea.model.specialeffect;

import com.google.java.contract.Ensures;

import skyvssea.model.piece.Piece;

public abstract class AbstractSpecialEffectDecorator implements SpecialEffect {
	private SpecialEffect specialEffect;
	
	@Ensures("specialEffect != null")
	public AbstractSpecialEffectDecorator(SpecialEffect specialEffect) {
		this.specialEffect = specialEffect;
	}

	@Override
	public void apply(Piece target) {
		specialEffect.apply(target);			
		
	}
	   
	@Override
	public void remove(Piece target) {
		specialEffect.remove(target); 			
	}
	
	@Override
	public boolean updateEffectiveDuration() {
		return specialEffect.updateEffectiveDuration();
	}
	
}
