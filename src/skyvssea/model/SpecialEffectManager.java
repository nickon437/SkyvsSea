package skyvssea.model;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.specialeffect.SpecialEffect;

import java.util.ArrayList;
import java.util.List;

public class SpecialEffectManager implements SpecialEffectManagerInterface {
    private List<SpecialEffect> appliedSpecialEffects = new ArrayList<>();
    private AbstractPiece target;

    public SpecialEffectManager(AbstractPiece piece) {
        this.target = piece;
    }

    @Requires("specialEffect != null")
    @Ensures("appliedSpecialEffects.size() == old(appliedSpecialEffects.size()) + 1 && appliedSpecialEffects.contains(specialEffect)")
    @Override
    public void add(SpecialEffect specialEffect) {
        specialEffect.apply(target);
        appliedSpecialEffects.add(specialEffect);
    }

    @Requires("specialEffect != null")
    @Ensures("!appliedSpecialEffects.contains(specialEffect) && appliedSpecialEffects.size() == old(appliedSpecialEffects.size()) - 1")
    public void remove(SpecialEffect specialEffect) {
        specialEffect.remove(target);
        appliedSpecialEffects.remove(target);
    }

    @Override
    @Ensures("appliedSpecialEffects.size() <= old(appliedSpecialEffects.size())")
    public void updateEffectiveDuration() {
        List<SpecialEffect> toRemove = new ArrayList<>();
        for (SpecialEffect specialEffect : appliedSpecialEffects) {
            boolean isActive = specialEffect.updateEffectiveDuration();
            if (!isActive) {
                specialEffect.remove(target);
                toRemove.add(specialEffect);
            }
        }
        appliedSpecialEffects.removeAll(toRemove);
    }
}
