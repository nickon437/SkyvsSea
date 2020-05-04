package skyvssea.model.specialeffect;

public abstract class ChangeIntFieldDecorator extends AbstractSpecialEffectDecorator {
	private int originalValue;
	private double factor;
	
	public ChangeIntFieldDecorator(double factor, SpecialEffect specialEffect) {
		super(specialEffect);
		this.setFactor(factor);
	}

	public int getOriginalValue() {
		return originalValue;
	}

	public void setOriginalValue(Integer originalValue) {
		this.originalValue = originalValue;
	}

	public double getFactor() {
		return factor;
	}

	public void setFactor(double factor) {
		this.factor = factor;
	}
}
