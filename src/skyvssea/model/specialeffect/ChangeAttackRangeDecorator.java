package skyvssea.model.specialeffect;

import skyvssea.model.piece.AbstractPiece;

public class ChangeAttackRangeDecorator extends ChangeIntFieldDecorator {
	
	public ChangeAttackRangeDecorator(double factor, AbstractSpecialEffectDecorator specialEffectWrappee) {
		super(factor, specialEffectWrappee);
	}
	
	@Override
	public void apply(AbstractPiece target) {
		int originalValue = target.getInitialAttackRange();
		int newValue = (int) (originalValue * factor);
		if (isNewValueValid(originalValue, target.getAttackRange(), newValue)) {
			target.setAttackRange(newValue);
		}
		super.apply(target);
	}
	   
	@Override
	public void remove(AbstractPiece target) {
		target.setAttackRange(target.getInitialAttackRange());
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
