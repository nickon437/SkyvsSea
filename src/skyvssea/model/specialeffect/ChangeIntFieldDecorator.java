package skyvssea.model.specialeffect;

public abstract class ChangeIntFieldDecorator extends AbstractSpecialEffectDecorator {
	protected int originalValue;
	protected double factor;
	
	public ChangeIntFieldDecorator(double factor, AbstractSpecialEffectDecorator specialEffectWrappee) {
		super(specialEffectWrappee);
		this.factor = factor;
	}
}
