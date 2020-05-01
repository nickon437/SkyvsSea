package skyvssea.model;

import skyvssea.model.piece.Piece;
import skyvssea.model.specialeffect.AbstractSpecialEffect;

import java.util.ArrayList;

public class SpecialEffectManager {
    private ArrayList<AbstractSpecialEffect> appliedSpecialEffects = new ArrayList<>();
    private Piece receiver;

    public SpecialEffectManager(Piece piece) {
        this.receiver = piece;
    }

    public void add(AbstractSpecialEffect specialEffect) {
        appliedSpecialEffects.add(specialEffect);
        specialEffect.apply(receiver);
    }

    public void updateEffectiveDuration() {
        ArrayList<AbstractSpecialEffect> toRemove = new ArrayList<>();
        for (AbstractSpecialEffect specialEffect : appliedSpecialEffects) {
            boolean isActive = specialEffect.updateEffectiveDuration();
            if (!isActive) { toRemove.add(specialEffect); }
        }
        appliedSpecialEffects.removeAll(toRemove);
    }
}
