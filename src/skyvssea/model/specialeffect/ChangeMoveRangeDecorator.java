package skyvssea.model.specialeffect;

import skyvssea.model.piece.AbstractPiece;

public class ChangeMoveRangeDecorator extends ChangeIntFieldDecorator {
	
	public ChangeMoveRangeDecorator(double factor, SpecialEffect specialEffect) {
		super(factor, specialEffect);
	}
	
	@Override
	public void apply(AbstractPiece target) {
		changeMoveRange(target);
		super.apply(target);
	}
	   
	@Override
	public void remove(AbstractPiece target) {
		target.setMoveRange(getOriginalValue());
		super.remove(target); 
	}
	
	private void changeMoveRange(AbstractPiece target) {
		setOriginalValue(target.getMoveRange());
    	target.setMoveRange((int) (getOriginalValue() * getFactor()));
	}
}
