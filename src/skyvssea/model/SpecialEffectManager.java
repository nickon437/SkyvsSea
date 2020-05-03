package skyvssea.model;

import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.specialeffect.AbstractSpecialEffect;

import java.util.ArrayList;

public class SpecialEffectManager {
    private ArrayList<AbstractSpecialEffect> appliedSpecialEffects = new ArrayList<>();
    private AbstractPiece receiver;

    public SpecialEffectManager(AbstractPiece piece) {
        this.receiver = piece;
    }

    public void add(AbstractSpecialEffect specialEffect) {
        try {
            specialEffect.apply(receiver);
            appliedSpecialEffects.add(specialEffect);
        } catch (CloneNotSupportedException e) {
            System.err.println(e.getMessage());
        }
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
