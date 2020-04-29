package skyvssea.model;

public class SharkFactory extends PieceFactory {

	private static SharkFactory sharkFactory;

	private SharkFactory() {}

	public static PieceFactory getInstance() {
		if (sharkFactory == null) {
			sharkFactory = new SharkFactory();
		}
		return sharkFactory;
	}

	@Override
	protected BigCharacter createBigCharacter() {
		return new BigShark();
	}

	@Override
	protected MediumCharacter createMediumCharacter() {
		return new MediumShark();
	}

	@Override
	protected SmallCharacter createSmallCharacter() {
		return new SmallShark();
	}

	@Override
	protected BabyCharacter createBabyCharacter() {
		return new BabyShark();
	}
}
