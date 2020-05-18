package skyvssea.model.specialeffect;

import com.google.java.contract.Ensures;

import skyvssea.model.Hierarchy;
import skyvssea.model.piece.AbstractPiece;

public class ChangeDefenceLevelDecorator extends ChangeHierarchyFieldDecorator {
	public ChangeDefenceLevelDecorator(int change, AbstractSpecialEffectDecorator specialEffectWrappee) {
		super(change, specialEffectWrappee);
	}
	
	public ChangeDefenceLevelDecorator(Hierarchy level, AbstractSpecialEffectDecorator specialEffectWrappee) {
		super(level, specialEffectWrappee);
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

	@Ensures("result != null")
	@Override
	public AbstractSpecialEffectDecorator copy() {
		AbstractSpecialEffectDecorator copiedDecorator = null;
		AbstractSpecialEffectDecorator copiedWrappee = null;
		if (specialEffectWrappee != null) {
			copiedWrappee = specialEffectWrappee.copy();
		}
		
		if (change != null) {
			copiedDecorator = new ChangeDefenceLevelDecorator(change, copiedWrappee);
		} else if (specificLevel != null) {
			copiedDecorator = new ChangeDefenceLevelDecorator(specificLevel, copiedWrappee);
		}
	
		return copiedDecorator;
	}
}
