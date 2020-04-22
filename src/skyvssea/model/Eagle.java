package skyvssea.model;

abstract class Eagle extends Piece {
	private final static Direction MOVE_DIRECTION[] = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.JUMPOVER};

	protected Eagle(String name, Hierarchy level, int numMove, int attackRange, int specialEffectCounter, SpecialEffect specialEffect) {
		super(name, Team.EAGLE, level, numMove, MOVE_DIRECTION, attackRange, specialEffectCounter, specialEffect);
	}
}
