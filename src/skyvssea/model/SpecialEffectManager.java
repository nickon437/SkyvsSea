package skyvssea.model;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
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
	@Ensures("appliedSpecialEffects.size() <= old(appliedSpecialEffects.size())")
	public void updateEffectiveDuration() {
		List<SpecialEffectObject> toRemove = new ArrayList<>();
		for (SpecialEffectObject specialEffect : appliedSpecialEffects) {
			boolean isActive = specialEffect.updateEffectiveDuration();
			if (!isActive) {
				specialEffect.remove(target);
				toRemove.add(specialEffect);
			}
		}
		appliedSpecialEffects.removeAll(toRemove);

		// Reapply all existing appliedSpecialEffects because the specialEffect removal
		// just now might cancel special effects which are still effective
		for (SpecialEffectObject specialEffect : appliedSpecialEffects) {
			specialEffect.apply(target);
		}
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
}
