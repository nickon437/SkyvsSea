package skyvssea.model.specialeffect;

import com.google.java.contract.Ensures;

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
		setOriginalValue(target.getDefenceLevel());
		Hierarchy newLevel = getNewLevel();
		target.setDefenceLevel(newLevel);	
		super.apply(target);
	}
	   
	@Override
	public void remove(AbstractPiece target) {
		target.setDefenceLevel(getOriginalValue());
		super.remove(target); 
	}

	@Ensures("result != null")
	@Override
	public SpecialEffect copy() {
		if (getChange() != null) {
			return new ChangeDefenceLevelDecorator(getChange(), getSpecialEffect().copy());
		} else if (getSpecificLevel() != null) {
			return new ChangeDefenceLevelDecorator(getSpecificLevel(), getSpecialEffect().copy());
		}
		return null;
	}

	
}
