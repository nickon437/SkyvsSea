package skyvssea.model;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import skyvssea.model.command.Command;
import skyvssea.model.command.HistoryManager;
import skyvssea.model.command.UpdateEffectiveDurationCommand;
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
        appliedSpecialEffects.remove(specialEffect);
    }

    @Override
    @Ensures("appliedSpecialEffects.size() <= old(appliedSpecialEffects.size())")
    public void updateEffectiveDuration(HistoryManager historyManager) {
        for (int i = appliedSpecialEffects.size() - 1; i >= 0; i--) {
            SpecialEffect specialEffect = appliedSpecialEffects.get(i);

            int currentEffectiveDuration = specialEffect.getEffectiveDuration();
            int newEffectiveDuration = currentEffectiveDuration - 1;
            Command updateEffectiveDurationCommand = new UpdateEffectiveDurationCommand(this, specialEffect, newEffectiveDuration);
            historyManager.storeAndExecute(updateEffectiveDurationCommand);
        }
    }
}
