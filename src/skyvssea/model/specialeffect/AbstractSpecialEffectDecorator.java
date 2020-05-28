package skyvssea.model.specialeffect;

import skyvssea.model.Hierarchy;
import skyvssea.model.piece.AbstractPiece;

public abstract class AbstractSpecialEffectDecorator implements SpecialEffect {
	protected AbstractSpecialEffectDecorator specialEffectWrappee;
	
	public AbstractSpecialEffectDecorator(AbstractSpecialEffectDecorator specialEffectWrappee) {
		// If specialEffectWrappee is null, this decorator object is the last object in this chain
		this.specialEffectWrappee = specialEffectWrappee;
	}

	@Override
	public void apply(AbstractPiece target) { 
		if (specialEffectWrappee != null) {
			specialEffectWrappee.apply(target); 			
		}
	}
	  
	@Override
	public void remove(AbstractPiece target) { 
		if (specialEffectWrappee != null) {
			specialEffectWrappee.remove(target); 			
		}
	}
	
	@Override
	public AbstractSpecialEffectDecorator copy() {
		if (specialEffectWrappee == null) {
			return null;			
		}
		return specialEffectWrappee.copy();
	}

	public SpecialEffect getSpecialEffect() { return specialEffectWrappee; }
}
