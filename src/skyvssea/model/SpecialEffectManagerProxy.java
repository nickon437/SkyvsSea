package skyvssea.model;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import skyvssea.model.command.HistoryManager;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.specialeffect.SpecialEffectObject;

import java.util.ArrayList;
import java.util.List;

public class SpecialEffectManagerProxy implements SpecialEffectManagerInterface {

    private SpecialEffectManagerInterface specialEffectManager;
    private AbstractPiece target;

    public SpecialEffectManagerProxy(AbstractPiece piece) {
        target = piece;
    }

    @Requires("specialEffect != null")
    @Ensures("specialEffectManager != null")
    @Override
    public void add(SpecialEffectObject specialEffect) {
        if (specialEffectManager == null) {
            specialEffectManager = new SpecialEffectManager(target);
        }
        specialEffectManager.add(specialEffect);
    }

    @Override
    public void remove(SpecialEffectObject specialEffect) {
    	if (specialEffectManager != null) {
    		specialEffectManager.remove(specialEffect);
    	}
    }

    @Requires("historyManager != null")
    @Override
    public void updateEffectiveDuration(HistoryManager historyManager) {
        if (specialEffectManager != null) {
            specialEffectManager.updateEffectiveDuration(historyManager);
        }
    }

    public void setEffectiveDuration(String specialEffectName, int effectiveDuration) {
       if (specialEffectManager != null) {
           specialEffectManager.setEffectiveDuration(specialEffectName, effectiveDuration);
       }
    }

    @Override
    public List<SpecialEffectObject> getAppliedSpecialEffects() {
        if (specialEffectManager != null) {
            return specialEffectManager.getAppliedSpecialEffects();
        } else {
            return new ArrayList<>();
        }
    }

	@Override
	public boolean isImmuneToEnemySpecialEffect() {
		if (specialEffectManager == null) {
            specialEffectManager = new SpecialEffectManager(target);
        }
		return specialEffectManager.isImmuneToEnemySpecialEffect();
	}

	@Override
	public void setImmuneToEnemySpecialEffect(boolean immuneToSpecialEffect) {
		if (specialEffectManager == null) {
            specialEffectManager = new SpecialEffectManager(target);
        }
		specialEffectManager.setImmuneToEnemySpecialEffect(immuneToSpecialEffect);
	}

	@Override
	public void removeEnemySpecialEffect() {
		if (specialEffectManager == null) {
            specialEffectManager = new SpecialEffectManager(target);
        }
		specialEffectManager.removeEnemySpecialEffect();
	}
}
