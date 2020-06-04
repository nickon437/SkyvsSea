package skyvssea.model;

import skyvssea.model.command.HistoryManager;
import skyvssea.model.specialeffect.SpecialEffect;

import java.util.List;

public interface SpecialEffectManagerInterface {
    void add(SpecialEffect specialEffect);
    void remove(SpecialEffect specialEffect);
    void updateEffectiveDuration(HistoryManager historyManager);
    void setEffectiveDuration(String specialEffectName, int effectiveDuration);
    List<SpecialEffect> getAppliedSpecialEffects();
}
