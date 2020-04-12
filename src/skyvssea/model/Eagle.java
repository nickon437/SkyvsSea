package skyvssea.model;

abstract class Eagle extends Piece {

	public Eagle(Hierarchy level, int numMove, int attackRange, int specialEffectCounter, SpecialEffect specialEffect) {
		super(level, numMove, attackRange, specialEffectCounter, specialEffect);
	}
}
