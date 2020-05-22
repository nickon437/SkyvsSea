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

    private SpecialEffectContainer createDoubleAttackRange(AbstractPiece caster) {
    	return new ActiveSpecialEffectContainer(caster, "Attack range x2", TargetType.SELF, 
    			new ChangeAttackRangeDecorator(2, null));
    }
    
    private SpecialEffectContainer createDoubleMoveRange(AbstractPiece caster) {
    	return new ActiveSpecialEffectContainer(caster, "Move range x2", TargetType.SELF, 
    			new ChangeMoveRangeDecorator(2, null));
    }
    
    private SpecialEffectContainer createRetarding(AbstractPiece caster) {
    	return new ActiveSpecialEffectContainer(caster, "Retarding", TargetType.ENEMIES, 
    			new ChangeMoveRangeDecorator(0.5, null));
    }
    private SpecialEffectContainer createFreezing(AbstractPiece caster) { 
    	return new ActiveSpecialEffectContainer(caster, "Freezing", TargetType.ENEMIES, 
    			new ChangeMoveRangeDecorator(0, new ChangeAttackRangeDecorator(0, null)));
	}
    
    private SpecialEffectContainer createStrengthening(AbstractPiece caster) { 
    	return new ActiveSpecialEffectContainer(caster, "Strengthening", TargetType.COMRADES, 
    			new ChangeAttackLevelDecorator(1, new ChangeDefenceLevelDecorator(1, null)));
    }
    
    private SpecialEffectContainer createWeakening(AbstractPiece caster) { 
    	return new ActiveSpecialEffectContainer(caster, "Weakening", TargetType.ENEMIES, 
    			new ChangeAttackLevelDecorator(-1, new ChangeDefenceLevelDecorator(-1, null)));
    }
    
    private SpecialEffectContainer createTougheningComrades(AbstractPiece caster) { 
    	return new PassiveSpecialEffectContainer(caster, "Increasing Defence Level of Nearby Comrades", TargetType.COMRADES, 
    			new ChangeDefenceLevelDecorator(1, null));
    }

    public SpecialEffectContainer createSpecialEffect(AbstractPiece caster, SpecialEffectCode code) {
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
            case TOUGHENING_COMRADES:
            	return createTougheningComrades(caster);
            default:
                return null;
        }
    }
    
    public SpecialEffectContainer copy(SpecialEffectContainer specialEffect) {
    	return (SpecialEffectContainer) specialEffect.copy();
    }
}
