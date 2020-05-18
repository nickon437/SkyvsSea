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
	public AbstractSpecialEffectDecorator copy() {
		AbstractSpecialEffectDecorator copy = null;
		if (change != null) {
			copy = new ChangeAttackLevelDecorator(change, specialEffectWrappee.copy());
		} else if (specificLevel != null) {
			copy = new ChangeAttackLevelDecorator(specificLevel, specialEffectWrappee.copy());
		}
		return copy;
	}
}
