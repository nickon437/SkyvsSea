package skyvssea.model;

import skyvssea.model.piece.*;

public class EagleFactory extends PieceFactory {

	private static EagleFactory eagleFactory;

	private EagleFactory() {}

	public static PieceFactory getInstance() {
		if (eagleFactory == null) {
			eagleFactory = new EagleFactory();
		}
		return eagleFactory;
	}

	@Override
	protected BigCharacter createBigCharacter() {
		return new BigEagle();
	}

	@Override
	protected MediumCharacter createMediumCharacter() {
		return new MediumEagle();
	}

	@Override
	protected SmallCharacter createSmallCharacter() {
		return new SmallEagle();
	}

	@Override
	protected BabyCharacter createBabyCharacter() {
		return new BabyEagle();
	}
}
