package skyvssea.model;

import skyvssea.model.specialeffect.SpecialEffectContainer;

public interface SpecialEffectManagerInterface {
    void add(SpecialEffectContainer specialEffect);
    void updateEffectiveDuration();
	void remove(SpecialEffectContainer specialEffect);
	boolean isImmuneToEnemySpecialEffect();
	void setImmuneToEnemySpecialEffect(boolean immuneToSpecialEffect);
	void removeEnemySpecialEffect();
}
