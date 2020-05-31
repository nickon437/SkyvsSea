package skyvssea.model;

import skyvssea.model.piece.AbstractPiece;
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

    private SpecialEffectObject createDoubleAttackRange(AbstractPiece caster) {
    	return new ActiveSpecialEffectObject(caster, "Attack range x2", TargetType.SELF, 
    			new ChangeAttackRangeDecorator(2, null));
    }
    
    private SpecialEffectObject createDoubleMoveRange(AbstractPiece caster) {
    	return new ActiveSpecialEffectObject(caster, "Move range x2", TargetType.SELF, 
    			new ChangeMoveRangeDecorator(2, null));
    }
    
    private SpecialEffectObject createRetarding(AbstractPiece caster) {
    	return new ActiveSpecialEffectObject(caster, "Retarding", TargetType.ENEMIES, 
    			new ChangeMoveRangeDecorator(0.5, null));
    }
    private SpecialEffectObject createFreezing(AbstractPiece caster) { 
    	return new ActiveSpecialEffectObject(caster, "Freezing", TargetType.ENEMIES, 
    			new ChangeMoveRangeDecorator(0, new ChangeAttackRangeDecorator(0, null)));
	}
    
    private SpecialEffectObject createStrengthening(AbstractPiece caster) { 
    	return new ActiveSpecialEffectObject(caster, "Strengthening", TargetType.COMRADES, 
    			new ChangeAttackLevelDecorator(1, new ChangeDefenceLevelDecorator(1, null)));
    }
    
    private SpecialEffectObject createWeakening(AbstractPiece caster) { 
    	return new ActiveSpecialEffectObject(caster, "Weakening", TargetType.ENEMIES, 
    			new ChangeAttackLevelDecorator(-1, new ChangeDefenceLevelDecorator(-1, null)));
    }
    
    public SpecialEffectObject createPassiveDefenceLevelPlus1(AbstractPiece caster) { 
    	return new PassiveSpecialEffectObject(caster, "Increasing Defence Level of Nearby Comrades", TargetType.COMRADES, 
    			new ChangeDefenceLevelDecorator(1, null));
    }
    
    public SpecialEffectObject createPassiveAttackLevelPlus1(AbstractPiece caster) { 
    	return new PassiveSpecialEffectObject(caster, "Increasing Attack Level of Nearby Comrades", TargetType.COMRADES, 
    			new ChangeAttackLevelDecorator(1, null));
    }
    
    public SpecialEffectObject createPassiveAntiSpecialEffect(AbstractPiece caster) { 
    	return new PassiveSpecialEffectObject(caster, "Shielding Nearby Comrades from Enemies' Special Effects", TargetType.COMRADES, 
    			new ActivateImmunityToSpecialEffectDecorator(null));
    }
    
    public SpecialEffectObject createPassiveFreezing(AbstractPiece caster) { 
    	return new PassiveSpecialEffectObject(caster, "Freezing Nearby Enemies", TargetType.ENEMIES, 
    			new ChangeMoveRangeDecorator(0, new ChangeAttackRangeDecorator(0, null)));
    }
    
    public SpecialEffectObject createPassiveDefenceLevelUpAttackLevelDown(AbstractPiece caster) { 
    	return new PassiveSpecialEffectObject(caster, "Self Defence Level Up, Self Attack Level Down", TargetType.SELF, 
    			new ChangeAttackLevelDecorator(-1, new ChangeDefenceLevelDecorator(1, null)));
    }
    
    public SpecialEffectObject createPassiveAttackLevelUpDefenceLevelDown(AbstractPiece caster) { 
    	return new PassiveSpecialEffectObject(caster, "Self Attack Level Up, Self Defence Level Down", TargetType.SELF, 
    			new ChangeAttackLevelDecorator(1, new ChangeDefenceLevelDecorator(-1, null)));
    }
    
    public SpecialEffectObject createSpecialEffect(AbstractPiece caster, SpecialEffectCode code) {
        switch (code) {
            case DOUBLE_ATTACK_RANGE:
                return createDoubleAttackRange(caster);
            case DOUBLE_MOVE_RANGE:
                return createDoubleMoveRange(caster);
            case RETARDING:
                return createRetarding(caster);
            case FREEZING:
                return createFreezing(caster);
            case STRENGTHENING:
                return createStrengthening(caster);
            case WEAKENING:
            	return createWeakening(caster);
            default:
                return null;
        }
    }
    
//    public SpecialEffectContainer copy(SpecialEffectContainer specialEffect) {
//    	return (SpecialEffectContainer) specialEffect.copy();
//    }
}
