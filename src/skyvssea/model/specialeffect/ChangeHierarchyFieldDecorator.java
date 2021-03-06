package skyvssea.model.specialeffect;

import com.google.java.contract.Requires;
import skyvssea.model.Hierarchy;

public abstract class ChangeHierarchyFieldDecorator extends AbstractSpecialEffectDecorator {
	protected Integer change;
	protected Hierarchy specificLevel;
	
	public ChangeHierarchyFieldDecorator(int change, AbstractSpecialEffectDecorator specialEffectWrappee) {
		super(specialEffectWrappee);
		this.change = change;
	}
	
	public ChangeHierarchyFieldDecorator(Hierarchy level, AbstractSpecialEffectDecorator specialEffectWrappee) {
		super(specialEffectWrappee);
		this.specificLevel = level;
	}

	@Requires("change != null || specificLevel != null")
	public Hierarchy getNewLevel(Hierarchy originalValue) {
		Hierarchy newLevel = null;

		if (change != null) {
			int newMagnitude = originalValue.magnitude + change;
			// if client gives an excessive change value resulting in an invalid level, this will return the appropriate max/min level
			if (newMagnitude > Hierarchy.maxLevel().magnitude) {
				newLevel = Hierarchy.maxLevel();
			} else if (newMagnitude < 0) {
				newLevel = Hierarchy.minLevel();
			} else {
				newLevel = Hierarchy.valueOfMagnitude(newMagnitude);
			}
		} else if (specificLevel != null) {
			newLevel = specificLevel;
		}
		return newLevel;
	}
	
	protected boolean isNewValueValid(Hierarchy originalValue, Hierarchy currentValue, Hierarchy newValue) {
		return (newValue.magnitude <= originalValue.magnitude && newValue.magnitude < currentValue.magnitude) || 
				(newValue.magnitude >= originalValue.magnitude && newValue.magnitude > currentValue.magnitude);
	}
}
