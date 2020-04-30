package skyvssea.model.piece;

import skyvssea.model.Direction;
import skyvssea.model.Hierarchy;
import skyvssea.model.SpecialEffect;

abstract class Eagle extends Piece {
	private final static Direction MOVE_DIRECTION[] = { Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.JUMP_OVER };

	protected Eagle(String name, Hierarchy level, int numMove, int attackRange, int specialEffectCounter, SpecialEffect specialEffect) {
		super(name, level, numMove, MOVE_DIRECTION, attackRange, specialEffectCounter, specialEffect);
	}
}
