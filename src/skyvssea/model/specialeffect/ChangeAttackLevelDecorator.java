package skyvssea.model.specialeffect;

import com.google.java.contract.Ensures;

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

	@Ensures("result != null")
	@Override
	public SpecialEffect copy() {
		SpecialEffect copy = null;
		if (change != null) {
			copy = new ChangeAttackLevelDecorator(change, specialEffect.copy());
		} else if (specificLevel != null) {
			copy = new ChangeAttackLevelDecorator(specificLevel, specialEffect.copy());
		}
		return copy;
	}

}
