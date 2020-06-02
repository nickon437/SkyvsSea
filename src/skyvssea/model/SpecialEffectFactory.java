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

    public SpecialEffectObject createDoubleAttackRange(AbstractPiece caster) {
    	return new ActiveSpecialEffectObject(caster, "Attack range x2", TargetType.SELF, 
    			new ChangeAttackRangeDecorator(2, null));
    }
    
    public SpecialEffectObject createDoubleMoveRange(AbstractPiece caster) {
    	return new ActiveSpecialEffectObject(caster, "Move range x2", TargetType.SELF, 
    			new ChangeMoveRangeDecorator(2, null));
    }
    
    public SpecialEffectObject createRetarding(AbstractPiece caster) {
    	return new ActiveSpecialEffectObject(caster, "Retarding", TargetType.ENEMIES, 
    			new ChangeMoveRangeDecorator(0.5, null));
    }
    public SpecialEffectObject createFreezing(AbstractPiece caster) { 
    	return new ActiveSpecialEffectObject(caster, "Freezing", TargetType.ENEMIES, 
    			new ChangeMoveRangeDecorator(0, new ChangeAttackRangeDecorator(0, null)));
	}
    
    public SpecialEffectObject createStrengthening(AbstractPiece caster) { 
    	return new ActiveSpecialEffectObject(caster, "Strengthening", TargetType.COMRADES, 
    			new ChangeAttackLevelDecorator(1, new ChangeDefenceLevelDecorator(1, null)));
    }
    
    public SpecialEffectObject createWeakening(AbstractPiece caster) { 
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
}
