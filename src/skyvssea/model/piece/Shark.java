package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;
import skyvssea.model.Direction;
import skyvssea.model.Hierarchy;

abstract class Shark extends Piece {
	private final static Direction MOVE_DIRECTION[] = { Direction.NORTH, Direction.NORTHEAST, Direction.EAST,
			Direction.SOUTHEAST, Direction.SOUTH, Direction.SOUTHWEST, Direction.WEST, Direction.NORTHWEST };

	protected Shark(String name, Hierarchy level, int numMove, int attackRange, SpecialEffectCode specialEffectCode,
					int specialEffectCooldown) {
		super(name, level, numMove, MOVE_DIRECTION, attackRange, specialEffectCode, specialEffectCooldown);
	}
}