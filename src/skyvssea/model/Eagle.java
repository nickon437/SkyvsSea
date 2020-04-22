package skyvssea.model;

abstract class Eagle extends Piece {

	protected Eagle(String name, Team team, Hierarchy level, int numMove, int attackRange, int specialEffectCounter, SpecialEffect specialEffect) {
		super(name, team, level, numMove, attackRange, specialEffectCounter, specialEffect);
	}
}
