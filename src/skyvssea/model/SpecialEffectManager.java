package skyvssea.model;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import skyvssea.model.command.Command;
import skyvssea.model.command.HistoryManager;
import skyvssea.model.command.UpdateEffectiveDurationCommand;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.specialeffect.SpecialEffectObject;
import skyvssea.model.specialeffect.TargetType;

import java.util.ArrayList;
import java.util.List;

public class SpecialEffectManager implements SpecialEffectManagerInterface {
	private List<SpecialEffectObject> appliedSpecialEffects = new ArrayList<>();
	private boolean immuneToEnemySpecialEffect;
	private AbstractPiece target;

	public SpecialEffectManager(AbstractPiece piece) {
		this.target = piece;
		immuneToEnemySpecialEffect = false;
	}

	@Requires("specialEffect != null")
	@Override
	public void add(SpecialEffectObject specialEffect) {
		specialEffect.apply(target);
		appliedSpecialEffects.add(specialEffect);
	}

	@Override
	public void remove(SpecialEffectObject specialEffect) {
		SpecialEffectObject toRemove = null;
		for (SpecialEffectObject appliedSpecialEffect : appliedSpecialEffects) {
			if (specialEffect.equals(appliedSpecialEffect)) {
				appliedSpecialEffect.remove(target);
				toRemove = appliedSpecialEffect;
				break;
			}
		}
		if (toRemove != null) {
			appliedSpecialEffects.remove(toRemove);
		}
	}

	@Override
	public boolean isImmuneToEnemySpecialEffect() {
		return immuneToEnemySpecialEffect;
	}

	@Override
	public void setImmuneToEnemySpecialEffect(boolean immuneToSpecialEffect) {
		this.immuneToEnemySpecialEffect = immuneToSpecialEffect;
	}

	@Override
	public void removeEnemySpecialEffect() {
		List<SpecialEffectObject> toRemove = new ArrayList<>();
		for (SpecialEffectObject specialEffect : appliedSpecialEffects) {
			if (specialEffect.getTargetType() == TargetType.ENEMIES) {
				specialEffect.remove(target);
				toRemove.add(specialEffect);
			}
		}
		appliedSpecialEffects.removeAll(toRemove);
	}
	
    @Override
    @Ensures("appliedSpecialEffects.size() <= old(appliedSpecialEffects.size())")
    public void updateEffectiveDuration(HistoryManager historyManager) {
        for (int i = appliedSpecialEffects.size() - 1; i >= 0; i--) {
        	SpecialEffectObject specialEffect = appliedSpecialEffects.get(i);

            int currentEffectiveDuration = specialEffect.getEffectiveDuration();
            int newEffectiveDuration = currentEffectiveDuration - 1;
            Command updateEffectiveDurationCommand = new UpdateEffectiveDurationCommand(this, specialEffect, newEffectiveDuration);
            historyManager.storeAndExecute(updateEffectiveDurationCommand);
        }
        
		// Reapply all existing appliedSpecialEffects because the specialEffect removal
		// just now might cancel special effects which are still effective
		for (SpecialEffectObject specialEffect : appliedSpecialEffects) {
			specialEffect.apply(target);
		}
    }
}
