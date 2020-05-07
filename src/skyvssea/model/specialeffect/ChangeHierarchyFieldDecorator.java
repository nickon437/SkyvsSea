package skyvssea.model.specialeffect;

import com.google.java.contract.Requires;
import skyvssea.model.Hierarchy;

public abstract class ChangeHierarchyFieldDecorator extends AbstractSpecialEffectDecorator {
	protected Hierarchy originalValue;
	protected Integer change;
	protected Hierarchy specificLevel;
	
	public ChangeHierarchyFieldDecorator(int change, SpecialEffect specialEffect) {
		super(specialEffect);
		this.change = change;
	}
	
	public ChangeHierarchyFieldDecorator(Hierarchy level, SpecialEffect specialEffect) {
		super(specialEffect);
		this.specificLevel = level;
	}

	@Requires("change != null || specificLevel != null")
	public Hierarchy getNewLevel() {
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
}
