package skyvssea.model;

abstract class Shark extends Piece {

	public Shark(Hierarchy level, int numMove, int attackRange, int specialEffectCounter, SpecialEffect specialEffect) {
		super(level, numMove, attackRange, specialEffectCounter, specialEffect);
	}
}