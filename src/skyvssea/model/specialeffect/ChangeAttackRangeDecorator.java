package skyvssea.model.specialeffect;

import skyvssea.model.piece.AbstractPiece;

public class ChangeAttackRangeDecorator extends ChangeIntFieldDecorator {
	
	public ChangeAttackRangeDecorator(double factor, AbstractSpecialEffectDecorator specialEffectWrappee) {
		super(factor, specialEffectWrappee);
	}
	
	@Override
	public void apply(AbstractPiece target) {
		originalValue = target.getAttackRange();
		target.setAttackRange((int) (originalValue * factor));
		super.apply(target);
	}
	   
	@Override
	public void remove(AbstractPiece target) {
		target.setAttackRange(originalValue);
		super.remove(target); 
	}

	@Override
	public AbstractSpecialEffectDecorator copy() {
		if (specialEffectWrappee != null) {
			return new ChangeAttackRangeDecorator(factor, specialEffectWrappee.copy());			
		} else {
			return new ChangeAttackRangeDecorator(factor, null);			
		}
	}
}
