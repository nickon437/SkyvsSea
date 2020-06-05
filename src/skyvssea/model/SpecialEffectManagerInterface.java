package skyvssea.model;

import skyvssea.model.command.HistoryManager;
import skyvssea.model.specialeffect.SpecialEffectObject;

import java.util.List;

public interface SpecialEffectManagerInterface {
    void add(SpecialEffectObject specialEffect);
    void remove(SpecialEffectObject specialEffect);
    void updateEffectiveDuration(HistoryManager historyManager);
    void setEffectiveDuration(String specialEffectName, int effectiveDuration);
    List<SpecialEffectObject> getAppliedSpecialEffects();
	boolean isImmuneToEnemySpecialEffect();
	void setImmuneToEnemySpecialEffect(boolean immuneToSpecialEffect);
	void removeEnemySpecialEffect();
}
