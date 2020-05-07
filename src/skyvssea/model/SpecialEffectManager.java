package skyvssea.model;

import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.specialeffect.SpecialEffect;

import java.util.ArrayList;

public class SpecialEffectManager {
    private ArrayList<SpecialEffect> appliedSpecialEffects = new ArrayList<>();
    private AbstractPiece receiver;

    public SpecialEffectManager(AbstractPiece piece) {
        this.receiver = piece;
    }

    public void add(SpecialEffect specialEffect) {
        specialEffect.apply(receiver);
		appliedSpecialEffects.add(specialEffect);
    }

    public void updateEffectiveDuration() {
        ArrayList<SpecialEffect> toRemove = new ArrayList<>();
        for (SpecialEffect specialEffect : appliedSpecialEffects) {
            boolean isActive = specialEffect.updateEffectiveDuration();
            if (!isActive) { 
            	specialEffect.remove(receiver);
            	toRemove.add(specialEffect); 
        	}
        }
        appliedSpecialEffects.removeAll(toRemove);
    }
}
