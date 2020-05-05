package skyvssea.model.specialeffect;

import skyvssea.model.Hierarchy;

public abstract class ChangeHierarchyFieldDecorator extends AbstractSpecialEffectDecorator {
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
		
		assert getChange() != null || getSpecificLevel() != null;
		if (getChange() != null) {
			int newMagnitude = originalValue.magnitude + getChange();
			// if client gives an excessive change value resulting in an invalid level, this will return the appropriate max/min level
			if (newMagnitude > Hierarchy.maxLevel().magnitude) {
				newLevel = Hierarchy.maxLevel();
			} else if (newMagnitude < 0) {
				newLevel = Hierarchy.minLevel();
			} else {
				newLevel = Hierarchy.valueOfMagnitude(newMagnitude);
			}
		} else if (getSpecificLevel() != null) {
			newLevel = getSpecificLevel();
		}
		return newLevel;
	}

	public Integer getChange() {
		return change;
	}

	public Hierarchy getSpecificLevel() {
		return specificLevel;
	}
}
