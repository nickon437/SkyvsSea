package skyvssea.model;

import skyvssea.model.specialeffect.*;

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
    private AbstractSpecialEffect createRetarding() {
        return new Retarding();
    }
    private AbstractSpecialEffect createFreezing() { return new Freezing(); }
    private AbstractSpecialEffect createStrengthening() { return new Strengthening(); }
    private AbstractSpecialEffect createWeakening() { return new Weakening(); }

    public AbstractSpecialEffect createSpecialEffect(SpecialEffectCode code) {
        switch (code) {
            case DOUBLE_ATTACK_RANGE:
                return createDoubleAttackRange();
            case DOUBLE_MOVE_RANGE:
                return createDoubleMoveRange();
            case RETARDING:
                return createRetarding();
            case FREEZING:
                return createFreezing();
            case STRENGTHENING:
                return createStrengthening();
            case WEAKENING:
                return createWeakening();
            default:
                return null;
        }
    }
}
