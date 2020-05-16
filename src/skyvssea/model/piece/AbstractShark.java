package skyvssea.model.piece;

import skyvssea.model.SpecialEffectCode;
import skyvssea.model.Direction;
import skyvssea.model.Hierarchy;

abstract class AbstractShark extends AbstractPiece {
	private final static Direction[] MOVE_DIRECTION = {Direction.NORTH, Direction.NORTHEAST, Direction.EAST,
			Direction.SOUTHEAST, Direction.SOUTH, Direction.SOUTHWEST, Direction.WEST, Direction.NORTHWEST};

	protected AbstractShark(String name, Hierarchy level, int moveRange, int attackRange,
							SpecialEffectCode specialEffectCode, int specialEffectCooldown) {
		super(name, level, moveRange, MOVE_DIRECTION, attackRange, specialEffectCode, specialEffectCooldown);
	}
}