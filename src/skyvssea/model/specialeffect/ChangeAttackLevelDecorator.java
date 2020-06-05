package skyvssea.model.specialeffect;

import com.google.java.contract.Ensures;

import skyvssea.model.Hierarchy;
import skyvssea.model.piece.AbstractPiece;

public class ChangeAttackLevelDecorator extends ChangeHierarchyFieldDecorator {
	public ChangeAttackLevelDecorator(int change, AbstractSpecialEffectDecorator specialEffectWrappee) {
		super(change, specialEffectWrappee);
	}
	
	public ChangeAttackLevelDecorator(Hierarchy level, AbstractSpecialEffectDecorator specialEffectWrappee) {
		super(level, specialEffectWrappee);
	}
	
	@Override
	public void apply(AbstractPiece target) {
		Hierarchy originalValue = target.getInitialAttackLevel();
		Hierarchy newValue = getNewLevel(originalValue);
		if (isNewValueValid(originalValue, target.getAttackLevel(), newValue)) {
			target.setAttackLevel(newValue);
		}		
		super.apply(target);
	}

	@Override
	public void remove(AbstractPiece target) {
		target.setAttackLevel(target.getInitialAttackLevel());
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
			copiedDecorator = new ChangeAttackLevelDecorator(change, copiedWrappee);
		} else if (specificLevel != null) {
			copiedDecorator = new ChangeAttackLevelDecorator(specificLevel, copiedWrappee);
		}
	
		return copiedDecorator;
	}
}
