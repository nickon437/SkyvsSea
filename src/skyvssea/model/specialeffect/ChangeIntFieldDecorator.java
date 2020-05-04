package skyvssea.model.specialeffect;

public abstract class ChangeIntFieldDecorator extends AbstractSpecialEffectDecorator {
	private Integer originalValue;
	private double factor;
	
	public ChangeIntFieldDecorator(double factor, SpecialEffect specialEffect) {
		super(specialEffect);
		this.setFactor(factor);
	}

	public Integer getOriginalValue() {
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
