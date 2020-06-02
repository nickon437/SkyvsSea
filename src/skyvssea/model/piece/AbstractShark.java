package skyvssea.model.piece;

import skyvssea.model.Direction;
import skyvssea.model.Hierarchy;

abstract class AbstractShark extends AbstractPiece {
	private final static Direction[] MOVE_DIRECTION = {Direction.NORTH, Direction.NORTHEAST, Direction.EAST,
			Direction.SOUTHEAST, Direction.SOUTH, Direction.SOUTHWEST, Direction.WEST, Direction.NORTHWEST};

	protected AbstractShark(String name, Hierarchy attackLevel, Hierarchy defenceLevel, int moveRange, int attackRange, int specialEffectCooldown) {
		super(name, attackLevel, defenceLevel, moveRange, MOVE_DIRECTION, attackRange, specialEffectCooldown);
	}
}