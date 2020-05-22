package skyvssea.model;

import skyvssea.model.specialeffect.SpecialEffectContainer;

public interface SpecialEffectManagerInterface {
    void add(SpecialEffectContainer specialEffect);
    void updateEffectiveDuration();
	void remove(SpecialEffectContainer specialEffect);
}
