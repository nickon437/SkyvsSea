package skyvssea.model;

import skyvssea.model.specialeffect.SpecialEffect;

public interface SpecialEffectManagerInterface {
    void add(SpecialEffect specialEffect);
    void remove(SpecialEffect specialEffect);
    void updateEffectiveDuration();
}
