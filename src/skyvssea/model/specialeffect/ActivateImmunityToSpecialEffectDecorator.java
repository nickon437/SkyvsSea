package skyvssea.model.specialeffect;

import skyvssea.model.piece.AbstractPiece;

public class ActivateImmunityToSpecialEffectDecorator extends AbstractSpecialEffectDecorator {

	public ActivateImmunityToSpecialEffectDecorator(AbstractSpecialEffectDecorator specialEffectWrappee) {
		super(specialEffectWrappee);
	}

	@Override
	public void apply(AbstractPiece target) { 
		target.getSpecialEffectManager().setImmuneToEnemySpecialEffect(true);
		target.getSpecialEffectManager().removeEnemySpecialEffect();
		super.apply(target);
	}
	  
	@Override
	public void remove(AbstractPiece target) { 
		target.getSpecialEffectManager().setImmuneToEnemySpecialEffect(false);
		//TODO: would like to reapply any enemy's passive effect that is within range (ie still exists in the current tile)
		super.remove(target);
	}
	
	@Override
	public AbstractSpecialEffectDecorator copy() {
		if (specialEffectWrappee != null) {
			return new ActivateImmunityToSpecialEffectDecorator(specialEffectWrappee.copy());
		} else {
			return new ActivateImmunityToSpecialEffectDecorator(null);
		}
	}
}
