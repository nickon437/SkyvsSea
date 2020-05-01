package skyvssea.model;

import skyvssea.model.specialeffect.AbstractSpecialEffect;
import skyvssea.model.specialeffect.DoubleAttackRange;

public class SpecialEffectFactory {

    private static SpecialEffectFactory specialEffectFactory;

    private SpecialEffectFactory() {}

    public static SpecialEffectFactory getInstance() {
        if (specialEffectFactory == null) {
            specialEffectFactory = new SpecialEffectFactory();
        }
        return specialEffectFactory;
    }

    private AbstractSpecialEffect createDoubleAttackRange() {
        return new DoubleAttackRange();
    }

    public AbstractSpecialEffect createSpecialEffect(SpecialEffectCode code) {
        switch (code) {
            case DOUBLE_ATTACK_RANGE:
                return createDoubleAttackRange();
            default:
                return null;
        }
    }
}
