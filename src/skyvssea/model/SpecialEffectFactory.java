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
    	return new ActiveSpecialEffectObject(caster, SpecialEffectCode.DOUBLE_ATTACK_RANGE.getText(), TargetType.SELF,
    			new ChangeAttackRangeDecorator(2, null));
    }
    
    public SpecialEffectObject createDoubleMoveRange(AbstractPiece caster) {
    	return new ActiveSpecialEffectObject(caster, SpecialEffectCode.DOUBLE_MOVE_RANGE.getText(), TargetType.SELF,
    			new ChangeMoveRangeDecorator(2, null));
    }
    
    public SpecialEffectObject createRetarding(AbstractPiece caster) {
    	return new ActiveSpecialEffectObject(caster, SpecialEffectCode.RETARDING.getText(), TargetType.ENEMIES,
    			new ChangeMoveRangeDecorator(0.5, null));
    }
    public SpecialEffectObject createFreezing(AbstractPiece caster) { 
    	return new ActiveSpecialEffectObject(caster, SpecialEffectCode.FREEZING.getText(), TargetType.ENEMIES,
    			new ChangeMoveRangeDecorator(0, new ChangeAttackRangeDecorator(0, null)));
	}
    
    public SpecialEffectObject createStrengthening(AbstractPiece caster) { 
    	return new ActiveSpecialEffectObject(caster, SpecialEffectCode.STRENGTHENING.getText(), TargetType.COMRADES,
    			new ChangeAttackLevelDecorator(1, new ChangeDefenceLevelDecorator(1, null)));
    }
    
    public SpecialEffectObject createWeakening(AbstractPiece caster) { 
    	return new ActiveSpecialEffectObject(caster, SpecialEffectCode.WEAKENING.getText(), TargetType.ENEMIES,
    			new ChangeAttackLevelDecorator(-1, new ChangeDefenceLevelDecorator(-1, null)));
    }
    
    public SpecialEffectObject createPassiveDefenceLevelPlus1(AbstractPiece caster) { 
    	return new PassiveSpecialEffectObject(caster, SpecialEffectCode.PASSIVE_DEFENCE_BOOST.getText(), TargetType.COMRADES,
    			new ChangeDefenceLevelDecorator(1, null));
    }
    
    public SpecialEffectObject createPassiveAttackLevelPlus1(AbstractPiece caster) { 
    	return new PassiveSpecialEffectObject(caster, SpecialEffectCode.PASSIVE_ATTACK_BOOST.getText(), TargetType.COMRADES,
    			new ChangeAttackLevelDecorator(1, null));
    }
    
    public SpecialEffectObject createPassiveAntiSpecialEffect(AbstractPiece caster) { 
    	return new PassiveSpecialEffectObject(caster, SpecialEffectCode.PASSIVE_ANTI_SPECIAL_EFFECT.getText(), TargetType.COMRADES,
    			new ActivateImmunityToSpecialEffectDecorator(null));
    }
    
    public SpecialEffectObject createPassiveFreezing(AbstractPiece caster) { 
    	return new PassiveSpecialEffectObject(caster, SpecialEffectCode.PASSIVE_FREEZING.getText(), TargetType.ENEMIES,
    			new ChangeMoveRangeDecorator(0, new ChangeAttackRangeDecorator(0, null)));
    }
    
    public SpecialEffectObject createPassiveDefenceLevelUpAttackLevelDown(AbstractPiece caster) { 
    	return new PassiveSpecialEffectObject(caster, SpecialEffectCode.PASSIVE_DEFENCE_BOOST_COST_ATTACK.getText(), TargetType.SELF,
    			new ChangeAttackLevelDecorator(-1, new ChangeDefenceLevelDecorator(1, null)));
    }
    
    public SpecialEffectObject createPassiveAttackLevelUpDefenceLevelDown(AbstractPiece caster) { 
    	return new PassiveSpecialEffectObject(caster, SpecialEffectCode.PASSIVE_ATTACK_BOOST_COST_DEFENCE.getText(), TargetType.SELF,
    			new ChangeAttackLevelDecorator(1, new ChangeDefenceLevelDecorator(-1, null)));
    }

    public SpecialEffectObject createSpecialEffect(SpecialEffectCode code, AbstractPiece caster) {
        switch (code) {
            case DOUBLE_ATTACK_RANGE:
                return createDoubleAttackRange(caster);
            case DOUBLE_MOVE_RANGE:
                return createDoubleMoveRange(caster);
            case FREEZING:
                return createFreezing(caster);
            case RETARDING:
                return createRetarding(caster);
            case WEAKENING:
                return createWeakening(caster);
            case STRENGTHENING:
                return createStrengthening(caster);
            default:
                return null;
        }
    }

    public SpecialEffectObject createSpecialEffect(String name, AbstractPiece caster) {
        SpecialEffectCode specialEffectCode = SpecialEffectCode.valueOf(name);
        return createSpecialEffect(specialEffectCode, caster);
    }
}
