package skyvssea.model.specialeffect;

import skyvssea.model.Hierarchy;
import skyvssea.model.piece.AbstractPiece;

public class ChangeAttackLevelDecorator extends ChangeHierarchyFieldDecorator {
	public ChangeAttackLevelDecorator(int change, SpecialEffect specialEffect) {
		super(change, specialEffect);
	}
	
	public ChangeAttackLevelDecorator(Hierarchy level, SpecialEffect specialEffect) {
		super(level, specialEffect);
	}
	
	@Override
	public void apply(AbstractPiece target) {
		setOriginalValue(target.getAttackLevel());
		Hierarchy newLevel = getNewLevel();
		target.setAttackLevel(newLevel);	
		super.apply(target);
	}
	   
	@Override
	public void remove(AbstractPiece target) {
		target.setAttackLevel(getOriginalValue());
		super.remove(target); 
	}
}
