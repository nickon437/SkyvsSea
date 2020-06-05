package skyvssea.model.specialeffect;

public abstract class ChangeIntFieldDecorator extends AbstractSpecialEffectDecorator {
	protected double factor;
	
	public ChangeIntFieldDecorator(double factor, AbstractSpecialEffectDecorator specialEffectWrappee) {
		super(specialEffectWrappee);
		this.factor = factor;
	}
	
	protected boolean isNewValueValid(int originalValue, int currentValue, int newValue) {
		return (newValue <= originalValue && newValue < currentValue) || (newValue >= originalValue && newValue > currentValue);
	}
}
