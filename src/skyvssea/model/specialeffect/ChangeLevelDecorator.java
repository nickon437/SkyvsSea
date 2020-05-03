package skyvssea.model.specialeffect;

import com.google.java.contract.Invariant;

import skyvssea.model.Hierarchy;
import skyvssea.model.piece.Piece;

@Invariant("change == null || specificLevel == null")
public class ChangeLevelDecorator extends AbstractSpecialEffectDecorator {
	private Hierarchy originalLevel;
	private Integer change;
	private Hierarchy specificLevel;
	
	public ChangeLevelDecorator(int change, SpecialEffect specialEffect) {
		super(specialEffect);
		this.change = change;
	}
	
	public ChangeLevelDecorator(Hierarchy level, SpecialEffect specialEffect) {
		super(specialEffect);
		this.specificLevel = level;
	}
	
	@Override
	public void apply(Piece target) {
		changeLevel(target);
		super.apply(target);
	}
	   
	@Override
	public void remove(Piece target) {
		target.setLevel(originalLevel);
		super.remove(target); 
	}
	
	private void changeLevel(Piece target) {
		originalLevel = target.getLevel();
		Hierarchy newLevel = null;
		
		if (change != null) {
			int newMagnitude = originalLevel.magnitude + change;
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
		
		target.setLevel(newLevel);			
		
	}
}
