package skyvssea.model;

import skyvssea.model.command.HistoryManager;
import skyvssea.model.specialeffect.SpecialEffectObject;

public interface SpecialEffectManagerInterface {
    void add(SpecialEffectObject specialEffect);
    void remove(SpecialEffectObject specialEffect);
    void updateEffectiveDuration(HistoryManager historyManager);
	boolean isImmuneToEnemySpecialEffect();
	void setImmuneToEnemySpecialEffect(boolean immuneToSpecialEffect);
	void removeEnemySpecialEffect();
}
