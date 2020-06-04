package skyvssea.model.specialeffect;

import skyvssea.model.PlayerManager;
import skyvssea.model.piece.AbstractPiece;

public abstract class SpecialEffectObject implements SpecialEffect {
	protected AbstractSpecialEffectDecorator specialEffectDecorator;
	protected AbstractPiece caster;
	protected String name;
	protected TargetType targetType;
	protected int effectiveDuration;
	
	public SpecialEffectObject(AbstractPiece caster, String name, int effectiveDuration, TargetType targetType, AbstractSpecialEffectDecorator specialEffectDecorator) {
		this.caster = caster;
		this.name = name;
		this.targetType = targetType;
		this.effectiveDuration = effectiveDuration; 
		this.specialEffectDecorator = specialEffectDecorator;
	}
	
//	public abstract boolean updateEffectiveDuration();
	
	public int getEffectiveDuration() {
		return effectiveDuration;
	}
	
	public abstract void setEffectiveDuration(int newEffectiveDuration);
	
	public String getName() {
		return name;
	}
	
	public TargetType getTargetType() {
		return targetType;
	}
	
	public boolean usableOnPiece(AbstractPiece target, PlayerManager playerManager) {
		// Satisfied when a piece with activated passive effect for comrades before the current round moves to an adjacent tile
		if (caster == target) {
			return targetType == TargetType.SELF;
		} 
		//Satisfied when a piece wants to use its special effect on itself
		else if (targetType == TargetType.SELF) {
			return caster == target;
		} 
		// For special effects for comrades/enemies
		else {
			boolean areComrades = playerManager.isCurrentPlayerPiece(target) == playerManager.isCurrentPlayerPiece(caster);
			boolean forComrades = targetType == TargetType.COMRADES;
			boolean applicable = areComrades == forComrades;
			if (applicable && !forComrades && target.getSpecialEffectManagerProxy().isImmuneToEnemySpecialEffect()) {
				return false;
			} else {
				return applicable;
			}
		}
	};
	
	@Override
	public void apply(AbstractPiece target) {
		specialEffectDecorator.apply(target);
	}
	@Override
	public void remove(AbstractPiece target) {
		specialEffectDecorator.remove(target);
	}	
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecialEffectObject compared = (SpecialEffectObject) o;
        return name.equals(compared.name) && targetType == compared.targetType && caster == compared.caster;
	}
}
