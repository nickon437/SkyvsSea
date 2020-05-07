package skyvssea.model;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.specialeffect.SpecialEffect;

public class SpecialEffectManagerProxy implements SpecialEffectManagerInterface {

    private SpecialEffectManager specialEffectManager;
    private AbstractPiece target;

    public SpecialEffectManagerProxy(AbstractPiece piece) {
        target = piece;
    }

    @Requires("specialEffect != null")
    @Ensures("specialEffectManager != null")
    @Override
    public void add(SpecialEffect specialEffect) {
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
}
