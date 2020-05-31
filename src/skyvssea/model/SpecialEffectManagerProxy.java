package skyvssea.model;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.specialeffect.SpecialEffectObject;

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
    public void updateEffectiveDuration() {
        if (specialEffectManager != null) {
            specialEffectManager.updateEffectiveDuration();
        }
    }

	@Override
	public void remove(SpecialEffectObject specialEffect) {
		if (specialEffectManager != null) {
            specialEffectManager.remove(specialEffect);
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
