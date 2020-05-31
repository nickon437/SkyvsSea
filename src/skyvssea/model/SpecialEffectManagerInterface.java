package skyvssea.model;

import skyvssea.model.specialeffect.SpecialEffectObject;

public interface SpecialEffectManagerInterface {
    void add(SpecialEffectObject specialEffect);
    void updateEffectiveDuration();
	void remove(SpecialEffectObject specialEffect);
	boolean isImmuneToEnemySpecialEffect();
	void setImmuneToEnemySpecialEffect(boolean immuneToSpecialEffect);
	void removeEnemySpecialEffect();
}
