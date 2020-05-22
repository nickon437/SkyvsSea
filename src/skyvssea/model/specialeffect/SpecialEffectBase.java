package skyvssea.model.specialeffect;

import skyvssea.model.piece.AbstractPiece;

public class SpecialEffectBase implements SpecialEffect {
	private int effectiveDuration;
	private String name;
	private TargetType targetType;
	
	public SpecialEffectBase(String name, TargetType targetType) {
		this.name = name;
		this.targetType = targetType;
		this.effectiveDuration = SpecialEffect.DEFAULT_CASTER_TURN; 
	}
	
	public SpecialEffectBase(String name, int effectiveDuration, TargetType targetType) {
		this.name = name;
		this.targetType = targetType;
		this.effectiveDuration = effectiveDuration; 
	}
	
	@Override
    public boolean updateEffectiveDuration() {
        boolean isActive = true;
        effectiveDuration--;
        if (effectiveDuration <= 0) {
            isActive = false;
        }
        return isActive;
    }
    
	@Override
	public void apply(AbstractPiece target) {
		// empty
	}

	@Override
	public void remove(AbstractPiece target) {
		// empty
	}

	@Override
	public int getEffectiveDuration() {
		return effectiveDuration;
	}

	public void setEffectiveDuration(int effectiveDuration) {
		this.effectiveDuration = effectiveDuration;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public SpecialEffect copy() {
		return new SpecialEffectBase(name, effectiveDuration, targetType);
	}

	@Override
	public TargetType getTargetType() {
		return targetType;
	}

}
