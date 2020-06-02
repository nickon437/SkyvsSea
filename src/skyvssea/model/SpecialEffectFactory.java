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
    final  String[] SpecialEffectSharkDescription = {
            "can make itself attack range double",
            "can increase own attack level from Medium to Big",
            "can decrease own attack level from Small to Middle"};
    final  String[] SpecialEffectEagleDescription = {
            "can move this piece again ",
            "can make one piece unable to move and attack others",
            "can halve the attack and move range of one piece "};

    private SpecialEffectFactory() {}

    public static SpecialEffectFactory getInstance() {
        if (specialEffectFactory == null) {
            specialEffectFactory = new SpecialEffectFactory();
        }
        return specialEffectFactory;
    }

    private SpecialEffect createDoubleAttackRange() {
        return new ChangeAttackRangeDecorator(2, new SpecialEffectBase("Attack range x2",SpecialEffectSharkDescription[0], TargetType.SELF));
    }
    private SpecialEffect createDoubleMoveRange() {
    	return new ChangeMoveRangeDecorator(2, new SpecialEffectBase("Move range x2", SpecialEffectSharkDescription[1],TargetType.SELF));
    }
    private SpecialEffect createRetarding() {
    	return new ChangeMoveRangeDecorator(0.5, new SpecialEffectBase("Retarding",SpecialEffectEagleDescription[2], TargetType.ENEMIES));
    }
    private SpecialEffect createFreezing() { 
    	return new ChangeMoveRangeDecorator(0, new ChangeAttackRangeDecorator(0,new SpecialEffectBase("Freezing", SpecialEffectEagleDescription[0], TargetType.ENEMIES)));
	}
    private SpecialEffect createStrengthening() { 
        return new ChangeAttackLevelDecorator(1, new ChangeDefenceLevelDecorator(1, new SpecialEffectBase("Strengthening",SpecialEffectSharkDescription[1], TargetType.COMRADES)));
    }
    private SpecialEffect createWeakening() { 
        return new ChangeAttackLevelDecorator(-1, new ChangeDefenceLevelDecorator(-1, new SpecialEffectBase("Weakening", SpecialEffectSharkDescription[2],TargetType.ENEMIES)));
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
    
    public SpecialEffect copy(SpecialEffect specialEffect) {
    	return specialEffect.copy();
    }
}
