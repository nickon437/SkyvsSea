package skyvssea.model;

import skyvssea.model.specialeffect.ChangeAttackLevelDecorator;
import skyvssea.model.specialeffect.ChangeAttackRangeDecorator;
import skyvssea.model.specialeffect.ChangeDefenceLevelDecorator;
import skyvssea.model.specialeffect.ChangeMoveRangeDecorator;
import skyvssea.model.specialeffect.SpecialEffect;
import skyvssea.model.specialeffect.SpecialEffectBase;

public class SpecialEffectFactory {
    private static SpecialEffectFactory specialEffectFactory;

    private SpecialEffectFactory() {}

    public static SpecialEffectFactory getInstance() {
        if (specialEffectFactory == null) {
            specialEffectFactory = new SpecialEffectFactory();
        }
        return specialEffectFactory;
    }

    private SpecialEffect createDoubleAttackRange() {
        return new ChangeAttackRangeDecorator(2, new SpecialEffectBase("Attack range x2", SpecialEffect.DEFAULT_CASTER_TURN));
    }
    private SpecialEffect createDoubleMoveRange() {
    	return new ChangeMoveRangeDecorator(2, new SpecialEffectBase("Move range x2", SpecialEffect.DEFAULT_CASTER_TURN));
    }
    private SpecialEffect createRetarding() {
    	return new ChangeMoveRangeDecorator(0.5, new SpecialEffectBase("Retarding", SpecialEffect.DEFAULT_CASTER_TURN));
    }
    private SpecialEffect createFreezing() { 
    	return new ChangeMoveRangeDecorator(0, new ChangeAttackRangeDecorator(0, new SpecialEffectBase("Freezing", SpecialEffect.DEFAULT_CASTER_TURN)));
	}
    private SpecialEffect createStrengthening() { 
        return new ChangeAttackLevelDecorator(1, new ChangeDefenceLevelDecorator(1, new SpecialEffectBase("Strengthening", SpecialEffect.DEFAULT_CASTER_TURN)));
    }
    private SpecialEffect createWeakening() { 
        return new ChangeAttackLevelDecorator(-1, new ChangeDefenceLevelDecorator(-1, new SpecialEffectBase("Weakening", SpecialEffect.DEFAULT_CASTER_TURN)));
    }

    public SpecialEffect createSpecialEffect(SpecialEffectCode code) {
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
