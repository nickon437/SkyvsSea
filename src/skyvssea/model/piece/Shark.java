package skyvssea.model.piece;

import skyvssea.model.specialeffect.SpecialEffect;
import skyvssea.model.Direction;
import skyvssea.model.Hierarchy;

abstract class Shark extends Piece {
	private final static Direction MOVE_DIRECTION[] = { Direction.NORTH, Direction.NORTHEAST, Direction.EAST,
			Direction.SOUTHEAST, Direction.SOUTH, Direction.SOUTHWEST, Direction.WEST, Direction.NORTHWEST };

	protected Shark(String name, Hierarchy attackLevel, Hierarchy defenceLevel, int numMove, int attackRange, SpecialEffect specialEffect,
					int specialEffectCooldown) {
		super(name, attackLevel, defenceLevel, numMove, MOVE_DIRECTION, attackRange, specialEffect, specialEffectCooldown);
	}
}