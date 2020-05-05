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

	@Ensures("result != null")
	@Override
	public SpecialEffect copy() {
		if (getChange() != null) {
			return new ChangeAttackLevelDecorator(getChange(), getSpecialEffect().copy());
		} else if (getSpecificLevel() != null) {
			return new ChangeAttackLevelDecorator(getSpecificLevel(), getSpecialEffect().copy());
		}
		return null;
	}
}
