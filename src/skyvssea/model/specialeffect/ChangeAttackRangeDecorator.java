package skyvssea.model.specialeffect;

import skyvssea.model.piece.AbstractPiece;

public class ChangeAttackRangeDecorator extends ChangeIntFieldDecorator {
	
	public ChangeAttackRangeDecorator(double factor, SpecialEffect specialEffect) {
		super(factor, specialEffect);
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
	public SpecialEffect copy() {
		return new ChangeAttackRangeDecorator(factor, specialEffect.copy());
	}
}
