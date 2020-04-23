package skyvssea.model;

import com.google.java.contract.Ensures;

public abstract class PieceFactory {
//	public abstract Map<Hierarchy, ArrayList<Piece>> createInitialLineUp();
	protected abstract BigCharacter createBigCharacter();
	protected abstract MediumCharacter createMediumCharacter();
	protected abstract SmallCharacter createSmallCharacter();
	protected abstract BabyCharacter createBabyCharacter();

	@Ensures("result != null")
	public Piece createPiece(Hierarchy level) {
		Piece piece = null;
		if (level == Hierarchy.BIG) {
			piece = (Piece) createBigCharacter();
		} else if (level == Hierarchy.MEDIUM) {
			piece = (Piece) createMediumCharacter();
		} else if (level == Hierarchy.SMALL) {
			piece = (Piece) createSmallCharacter();
		} else if (level == Hierarchy.BABY) {
			piece = (Piece) createBabyCharacter();
		}
		
		return piece;
	}
	
}
