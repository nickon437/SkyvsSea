package skyvssea.model;

abstract class Shark extends Piece {

	protected Shark(String name, Team team, Hierarchy level, int numMove, int attackRange, int specialEffectCounter, SpecialEffect specialEffect) {
		super(name, team, level, numMove, attackRange, specialEffectCounter, specialEffect);
	}
}