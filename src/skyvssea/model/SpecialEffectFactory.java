package skyvssea.model;

import skyvssea.model.specialeffect.AbstractSpecialEffect;
import skyvssea.model.specialeffect.DoubleAttackRange;
import skyvssea.model.specialeffect.DoubleMoveRange;

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
    private AbstractSpecialEffect createDoubleMoveRange() {
        return new DoubleMoveRange();
    }

    public AbstractSpecialEffect createSpecialEffect(SpecialEffectCode code) {
        switch (code) {
            case DOUBLE_ATTACK_RANGE:
                return createDoubleAttackRange();
            case DOUBLE_MOVE_RANGE:
                return createDoubleMoveRange();
            default:
                return null;
        }
    }
}
