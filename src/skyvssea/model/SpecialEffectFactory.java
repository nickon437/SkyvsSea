package skyvssea.model;

import skyvssea.model.specialeffect.ChangeAttackLevelDecorator;
import skyvssea.model.specialeffect.ChangeAttackRangeDecorator;
import skyvssea.model.specialeffect.ChangeDefenceLevelDecorator;
import skyvssea.model.specialeffect.ChangeMoveRangeDecorator;
import skyvssea.model.specialeffect.SpecialEffect;
import skyvssea.model.specialeffect.SpecialEffectBase;
import skyvssea.model.specialeffect.TargetType;

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
        return new ChangeAttackRangeDecorator(2, new SpecialEffectBase(SpecialEffectCode.DOUBLE_ATTACK_RANGE.getText(), TargetType.SELF));
    }
    private SpecialEffect createDoubleMoveRange() {
    	return new ChangeMoveRangeDecorator(2, new SpecialEffectBase(SpecialEffectCode.DOUBLE_MOVE_RANGE.getText(), TargetType.SELF));
    }
    private SpecialEffect createRetarding() {
    	return new ChangeMoveRangeDecorator(0.5, new SpecialEffectBase(SpecialEffectCode.RETARDING.getText(), TargetType.ENEMIES));
    }
    private SpecialEffect createFreezing() { 
    	return new ChangeMoveRangeDecorator(0, new ChangeAttackRangeDecorator(0, new SpecialEffectBase(SpecialEffectCode.FREEZING.getText(), TargetType.ENEMIES)));
	}
    private SpecialEffect createStrengthening() { 
        return new ChangeAttackLevelDecorator(1, new ChangeDefenceLevelDecorator(1, new SpecialEffectBase(SpecialEffectCode.STRENGTHENING.getText(), TargetType.COMRADES)));
    }
    private SpecialEffect createWeakening() { 
        return new ChangeAttackLevelDecorator(-1, new ChangeDefenceLevelDecorator(-1, new SpecialEffectBase(SpecialEffectCode.WEAKENING.getText(), TargetType.ENEMIES)));
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

    public SpecialEffect createSpecialEffect(String name) {
        SpecialEffectCode specialEffectCode = SpecialEffectCode.valueOf(name);
        return createSpecialEffect(specialEffectCode);
    }
    
    public SpecialEffect copy(SpecialEffect specialEffect) {
    	return specialEffect.copy();
    }
}
