package skyvssea.model.piece;

import skyvssea.model.Direction;
import skyvssea.model.Hierarchy;
import skyvssea.model.specialeffect.SpecialEffect;

abstract class Eagle extends Piece {
	private final static Direction MOVE_DIRECTION[] = { Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.JUMP_OVER };

	protected Eagle(String name, Hierarchy attackLevel, Hierarchy defenceLevel, int numMove, int attackRange, SpecialEffect specialEffect, int specialEffectCooldown) {
		super(name, attackLevel, defenceLevel, numMove, MOVE_DIRECTION, attackRange, specialEffect, specialEffectCooldown);
	}
}
