package skyvssea.model.specialeffect;

import skyvssea.model.Hierarchy;
import skyvssea.model.piece.AbstractPiece;

public class ChangeDefenceLevelDecorator extends ChangeHierarchyFieldDecorator {
	public ChangeDefenceLevelDecorator(int change, SpecialEffect specialEffect) {
		super(change, specialEffect);
	}
	
	public ChangeDefenceLevelDecorator(Hierarchy level, SpecialEffect specialEffect) {
		super(level, specialEffect);
	}
	
	@Override
	public void apply(AbstractPiece target) {
		originalValue = target.getDefenceLevel();
		target.setDefenceLevel(getNewLevel());
		super.apply(target);
	}
	   
	@Override
	public void remove(AbstractPiece target) {
		target.setDefenceLevel(originalValue);
		super.remove(target); 
	}

	@Override
	public SpecialEffect copy() {
		if (change != null) {
			return new ChangeDefenceLevelDecorator(change, specialEffect.copy());
		} else if (specificLevel != null) {
			return new ChangeDefenceLevelDecorator(specificLevel, specialEffect.copy());
		}
		return null;
	}
}
