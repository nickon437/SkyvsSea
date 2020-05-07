package skyvssea.model.specialeffect;

public abstract class ChangeIntFieldDecorator extends AbstractSpecialEffectDecorator {
	protected int originalValue;
	protected double factor;
	
	public ChangeIntFieldDecorator(double factor, SpecialEffect specialEffect) {
		super(specialEffect);
		this.factor = factor;
	}
}
