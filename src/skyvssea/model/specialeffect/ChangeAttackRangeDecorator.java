package skyvssea.model.specialeffect;

import skyvssea.model.piece.Piece;

public class ChangeAttackRangeDecorator extends ChangeIntFieldDecorator {
	
	public ChangeAttackRangeDecorator(double factor, SpecialEffect specialEffect) {
		super(factor, specialEffect);
	}
	
	@Override
	public void apply(Piece target) {
		changeAttackRange(target);
		super.apply(target);
	}
	   
	@Override
	public void remove(Piece target) {
		target.setAttackRange(getOriginalValue());
		super.remove(target); 
	}
	
	private void changeAttackRange(Piece target) {
		setOriginalValue(target.getAttackRange());
    	target.setAttackRange((int) (getOriginalValue() * getFactor()));
	}
}
