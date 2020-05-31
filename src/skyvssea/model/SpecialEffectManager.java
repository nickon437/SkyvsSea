package skyvssea.model;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.specialeffect.SpecialEffectContainer;
import skyvssea.model.specialeffect.TargetType;

import java.util.ArrayList;
import java.util.List;

public class SpecialEffectManager implements SpecialEffectManagerInterface {
	private List<SpecialEffectContainer> appliedSpecialEffects = new ArrayList<>();
	private boolean immuneToEnemySpecialEffect;
	private AbstractPiece target;

	public SpecialEffectManager(AbstractPiece piece) {
		this.target = piece;
		immuneToEnemySpecialEffect = false;
	}

	@Requires("specialEffect != null")
//	@Ensures("appliedSpecialEffects.size() == old(appliedSpecialEffects.size()) + 1 && appliedSpecialEffects.contains(specialEffect)")
	@Override
	public void add(SpecialEffectContainer specialEffect) {
		specialEffect.apply(target);
		appliedSpecialEffects.add(specialEffect);
	}

	@Override
	@Ensures("appliedSpecialEffects.size() <= old(appliedSpecialEffects.size())")
	public void updateEffectiveDuration() {
		List<SpecialEffectContainer> toRemove = new ArrayList<>();
		for (SpecialEffectContainer specialEffect : appliedSpecialEffects) {
			boolean isActive = specialEffect.updateEffectiveDuration();
			if (!isActive) {
				specialEffect.remove(target);
				toRemove.add(specialEffect);
			}
		}
		appliedSpecialEffects.removeAll(toRemove);

		// Reapply all existing appliedSpecialEffects because the specialEffect removal
		// just now might cancel special effects which are still effective
		for (SpecialEffectContainer specialEffect : appliedSpecialEffects) {
			specialEffect.apply(target);
		}
	}

	@Override
	public void remove(SpecialEffectContainer specialEffect) {
		SpecialEffectContainer toRemove = null;
		for (SpecialEffectContainer appliedSpecialEffect : appliedSpecialEffects) {
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

	public boolean isImmuneToEnemySpecialEffect() {
		return immuneToEnemySpecialEffect;
	}

	public void setImmuneToEnemySpecialEffect(boolean immuneToSpecialEffect) {
		this.immuneToEnemySpecialEffect = immuneToSpecialEffect;
	}

	@Override
	public void removeEnemySpecialEffect() {
		List<SpecialEffectContainer> toRemove = new ArrayList<>();
		for (SpecialEffectContainer specialEffect : appliedSpecialEffects) {
			if (specialEffect.getTargetType() == TargetType.ENEMIES) {
				specialEffect.remove(target);
				toRemove.add(specialEffect);
			}
		}
		appliedSpecialEffects.removeAll(toRemove);
	}
}
