package skyvssea.model;

abstract class Shark extends Piece {
	private final static Direction MOVE_DIRECTION[] = {Direction.NORTH, Direction.NORTHEAST, Direction.EAST,
			Direction.SOUTHEAST, Direction.SOUTH, Direction.SOUTHWEST, Direction.WEST, Direction.NORTHWEST};

	protected Shark(String name, Hierarchy level, int numMove, int attackRange, int specialEffectCounter, SpecialEffect specialEffect) {
		super(name, Team.SHARK, level, numMove, MOVE_DIRECTION, attackRange, specialEffectCounter, specialEffect);
	}
}