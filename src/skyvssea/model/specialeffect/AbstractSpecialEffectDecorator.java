package skyvssea.model.specialeffect;

import com.google.java.contract.Ensures;

import skyvssea.model.piece.AbstractPiece;

public abstract class AbstractSpecialEffectDecorator implements SpecialEffect {
	protected AbstractSpecialEffectDecorator specialEffectWrappee;
	
	@Ensures("specialEffectWrappee != null")
	public AbstractSpecialEffectDecorator(AbstractSpecialEffectDecorator specialEffectWrappee) {
		this.specialEffectWrappee = specialEffectWrappee;
	}

	@Override
	public void apply(AbstractPiece target) { specialEffectWrappee.apply(target); }
	   
	@Override
	public void remove(AbstractPiece target) { specialEffectWrappee.remove(target); }
	
	@Override
	public AbstractSpecialEffectDecorator copy() {
		return specialEffectWrappee.copy();
	}

	public SpecialEffect getSpecialEffect() { return specialEffectWrappee; }
}
