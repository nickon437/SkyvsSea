package skyvssea.model;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import skyvssea.model.command.HistoryManager;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.specialeffect.SpecialEffect;

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
    public void add(SpecialEffect specialEffect) {
        if (specialEffectManager == null) {
            specialEffectManager = new SpecialEffectManager(target);
        }
        specialEffectManager.add(specialEffect);
    }

    @Requires("specialEffect != null")
    @Override
    public void remove(SpecialEffect specialEffect) {
        if (specialEffect != null) {
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
    public List<SpecialEffect> getAppliedSpecialEffects() {
        if (specialEffectManager != null) {
            return specialEffectManager.getAppliedSpecialEffects();
        } else {
            return new ArrayList<>();
        }
    }
}
