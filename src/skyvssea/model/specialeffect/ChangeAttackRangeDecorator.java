package skyvssea.model.specialeffect;

import skyvssea.model.piece.AbstractPiece;

public class ChangeAttackRangeDecorator extends ChangeIntFieldDecorator {
	
	public ChangeAttackRangeDecorator(double factor, SpecialEffect specialEffect) {
		super(factor, specialEffect);
	}
	
	@Override
	public void apply(AbstractPiece target) {
		changeAttackRange(target);
		super.apply(target);
	}
	   
	@Override
	public void remove(AbstractPiece target) {
		target.setAttackRange(getOriginalValue());
		super.remove(target); 
	}
	
	private void changeAttackRange(AbstractPiece target) {
		setOriginalValue(target.getAttackRange());
    	target.setAttackRange((int) (getOriginalValue() * getFactor()));
	}
}
