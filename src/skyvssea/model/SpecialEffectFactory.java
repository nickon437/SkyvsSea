package skyvssea.model;

import skyvssea.model.specialeffect.ChangeAttackLevelDecorator;
import skyvssea.model.specialeffect.ChangeAttackRangeDecorator;
import skyvssea.model.specialeffect.ChangeDefenceLevelDecorator;
import skyvssea.model.specialeffect.ChangeMoveRangeDecorator;
import skyvssea.model.specialeffect.SpecialEffectContainer;
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

    private SpecialEffectContainer createDoubleAttackRange() {
    	return new SpecialEffectContainer("Attack range x2", TargetType.SELF, 
    			new ChangeAttackRangeDecorator(2, null));
    }
    
    private SpecialEffectContainer createDoubleMoveRange() {
    	return new SpecialEffectContainer("Move range x2", TargetType.SELF, 
    			new ChangeMoveRangeDecorator(2, null));
    }
    
    private SpecialEffectContainer createRetarding() {
    	return new SpecialEffectContainer("Retarding", TargetType.ENEMIES, 
    			new ChangeMoveRangeDecorator(0.5, null));
    }
    private SpecialEffectContainer createFreezing() { 
    	return new SpecialEffectContainer("Freezing", TargetType.ENEMIES, 
    			new ChangeMoveRangeDecorator(0, new ChangeAttackRangeDecorator(0, null)));
	}
    
    private SpecialEffectContainer createStrengthening() { 
    	return new SpecialEffectContainer("Strengthening", TargetType.COMRADES, 
    			new ChangeAttackLevelDecorator(1, new ChangeDefenceLevelDecorator(1, null)));
    }
    
    private SpecialEffectContainer createWeakening() { 
    	return new SpecialEffectContainer("Weakening", TargetType.ENEMIES, 
    			new ChangeAttackLevelDecorator(-1, new ChangeDefenceLevelDecorator(-1, null)));
    }

    public SpecialEffectContainer createSpecialEffect(SpecialEffectCode code) {
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
    
    public SpecialEffectContainer copy(SpecialEffectContainer specialEffect) {
    	return specialEffect.copy();
    }
}
