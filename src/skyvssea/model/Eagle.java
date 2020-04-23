package skyvssea.model;

abstract class Eagle extends Piece {

	protected Eagle(String name, Hierarchy level, int numMove, int attackRange, int specialEffectCounter, SpecialEffect specialEffect) {
		super(name, level, numMove, attackRange, specialEffectCounter, specialEffect);
	}
}
