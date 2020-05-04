package skyvssea.model.specialeffect;

import skyvssea.model.Hierarchy;

public class ChangeHierarchyFieldDecorator extends AbstractSpecialEffectDecorator {
	private Hierarchy originalValue;
	private Integer change;
	private Hierarchy specificLevel;
	
	public ChangeHierarchyFieldDecorator(int change, SpecialEffect specialEffect) {
		super(specialEffect);
		this.change = change;
	}
	
	public ChangeHierarchyFieldDecorator(Hierarchy level, SpecialEffect specialEffect) {
		super(specialEffect);
		this.specificLevel = level;
	}
	
	public Hierarchy getOriginalValue() {
		return originalValue;
	}

	public void setOriginalValue(Hierarchy originalValue) {
		this.originalValue = originalValue;
	}
	
	public Hierarchy getNewLevel() {
		Hierarchy newLevel = null;
		
		assert change != null || specificLevel != null;
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
