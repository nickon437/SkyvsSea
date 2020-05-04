package skyvssea.model;

import com.google.java.contract.Ensures;
import skyvssea.model.piece.*;

public abstract class AbstractPieceFactory {
	protected abstract BigCharacter createBigCharacter();
	protected abstract MediumCharacter createMediumCharacter();
	protected abstract SmallCharacter createSmallCharacter();
	protected abstract BabyCharacter createBabyCharacter();

	@Ensures("result != null")
	public AbstractPiece createPiece(Hierarchy level) {
		AbstractPiece piece = null;
		if (level == Hierarchy.BIG) {
			piece = (AbstractPiece) createBigCharacter();
		} else if (level == Hierarchy.MEDIUM) {
			piece = (AbstractPiece) createMediumCharacter();
		} else if (level == Hierarchy.SMALL) {
			piece = (AbstractPiece) createSmallCharacter();
		} else if (level == Hierarchy.BABY) {
			piece = (AbstractPiece) createBabyCharacter();
		}
		
		return piece;
	}
}
