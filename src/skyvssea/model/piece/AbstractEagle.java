package skyvssea.model.piece;

import skyvssea.model.Direction;
import skyvssea.model.Hierarchy;

abstract class AbstractEagle extends AbstractPiece {
	private final static Direction[] MOVE_DIRECTION = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.JUMP_OVER};

	protected AbstractEagle(String name, Hierarchy attackLevel, Hierarchy defenceLevel, int moveRange, int attackRange, int specialEffectCooldown) {
		super(name, attackLevel, defenceLevel, moveRange, MOVE_DIRECTION, attackRange, specialEffectCooldown);
	}
}
