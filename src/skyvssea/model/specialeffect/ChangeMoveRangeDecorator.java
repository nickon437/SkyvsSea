package skyvssea.model.specialeffect;

import skyvssea.model.piece.AbstractPiece;

public class ChangeMoveRangeDecorator extends ChangeIntFieldDecorator {
	
	public ChangeMoveRangeDecorator(double factor, AbstractSpecialEffectDecorator specialEffectWrappee) {
		super(factor, specialEffectWrappee);
	}
	
	@Override
	public void apply(AbstractPiece target) {
		changeMoveRange(target);
		super.apply(target);
		
	}
	   
	@Override
	public void remove(AbstractPiece target) {
		target.setMoveRange(target.getInitialMoveRange());
		super.remove(target); 
	}
	
	private void changeMoveRange(AbstractPiece target) {
		int originalValue = target.getInitialMoveRange();
		int newValue = (int) (originalValue * factor);
		if (isNewValueValid(originalValue, target.getMoveRange(), newValue)) {
			target.setMoveRange(newValue);			
		}
	}

	@Override
	public AbstractSpecialEffectDecorator copy() { 
		if (specialEffectWrappee != null) {
			return new ChangeMoveRangeDecorator(factor, specialEffectWrappee.copy());			
		} else {
			return new ChangeMoveRangeDecorator(factor, null);			
		}
	}
}
