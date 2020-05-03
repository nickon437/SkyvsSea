package skyvssea.model.specialeffect;

import skyvssea.model.piece.Piece;

public class ChangeMoveRangeDecorator extends ChangeIntFieldDecorator {
	
	public ChangeMoveRangeDecorator(double factor, SpecialEffect specialEffect) {
		super(factor, specialEffect);
	}
	
	@Override
	public void apply(Piece target) {
		changeMoveRange(target);
		super.apply(target);
	}
	   
	@Override
	public void remove(Piece target) {
		target.setNumMove(getOriginalValue());
		super.remove(target); 
	}
	
	private void changeMoveRange(Piece target) {
		setOriginalValue(target.getNumMove());
    	target.setNumMove((int) (getOriginalValue() * getFactor()));
	}
}
