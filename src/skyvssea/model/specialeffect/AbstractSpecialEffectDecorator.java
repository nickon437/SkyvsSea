package skyvssea.model.specialeffect;

import com.google.java.contract.Ensures;

import skyvssea.model.piece.AbstractPiece;

public abstract class AbstractSpecialEffectDecorator implements SpecialEffect {
	protected SpecialEffect specialEffect;
	
	@Ensures("specialEffect != null")
	public AbstractSpecialEffectDecorator(SpecialEffect specialEffect) {
		this.specialEffect = specialEffect;
	}

	@Override
	public void apply(AbstractPiece target) { specialEffect.apply(target); }
	   
	@Override
	public void remove(AbstractPiece target) { specialEffect.remove(target); }
	
	@Override
	public boolean updateEffectiveDuration() { return specialEffect.updateEffectiveDuration(); }
	
	@Override
	public int getEffectiveDuration() { return specialEffect.getEffectiveDuration(); }

	@Override
	public String getName() { return specialEffect.getName(); }

	public SpecialEffect getSpecialEffect() { return specialEffect; }
}
