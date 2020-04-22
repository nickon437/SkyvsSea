package skyvssea.model;

abstract class Shark extends Piece {

	protected Shark(String name, Hierarchy level, int numMove, int attackRange, int specialEffectCounter, SpecialEffect specialEffect) {
		super(name, Team.SHARK, level, numMove, attackRange, specialEffectCounter, specialEffect);
	}
}