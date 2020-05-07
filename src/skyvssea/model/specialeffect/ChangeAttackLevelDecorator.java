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
		originalValue = target.getAttackLevel();
		target.setAttackLevel(getNewLevel());
		super.apply(target);
	}
	   
	@Override
	public void remove(AbstractPiece target) {
		target.setAttackLevel(originalValue);
		super.remove(target); 
	}

	@Override
	public SpecialEffect copy() {
		if (change != null) {
			return new ChangeAttackLevelDecorator(change, specialEffect.copy());
		} else if (specificLevel != null) {
			return new ChangeAttackLevelDecorator(specificLevel, specialEffect.copy());
		}
		return null;
	}
}
