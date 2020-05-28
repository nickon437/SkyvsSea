package skyvssea.model.specialeffect;

import com.google.java.contract.Requires;
import com.sun.prism.impl.Disposer.Target;

import skyvssea.model.PlayerManager;
import skyvssea.model.piece.AbstractPiece;

public abstract class SpecialEffectContainer implements SpecialEffect {
	protected AbstractSpecialEffectDecorator specialEffectDecorator;
	protected AbstractPiece caster;
	protected String name;
	protected TargetType targetType;
	protected int effectiveDuration;
	
	public SpecialEffectContainer(AbstractPiece caster, String name, int effectiveDuration, TargetType targetType, AbstractSpecialEffectDecorator specialEffectDecorator) {
		this.caster = caster;
		this.name = name;
		this.targetType = targetType;
		this.effectiveDuration = effectiveDuration; 
		this.specialEffectDecorator = specialEffectDecorator;
	}
	
	@Requires("effectiveDuration > 0")
	public abstract boolean updateEffectiveDuration();
	
	public int getEffectiveDuration() {
		return effectiveDuration;
	}
	
	public String getName() {
		return name;
	}
	
	public TargetType getTargetType() {
		return targetType;
	}
	
	public boolean usableOnPiece(AbstractPiece target, PlayerManager playerManager) {
		if (caster == target) {
			return targetType == TargetType.SELF;
		} else {
			boolean areComrades = playerManager.isCurrentPlayerPiece(target) == playerManager.isCurrentPlayerPiece(caster);
			boolean forComrades = targetType == TargetType.COMRADES;
			return areComrades == forComrades;			
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
	
//	@Override
//	public boolean equals(Object o) {
//		if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        SpecialEffectContainer compared = (SpecialEffectContainer) o;
//        return name.equals(compared.name) && targetType == compared.targetType && caster == compared.caster;
//	}
}
