package skyvssea.model.specialeffect;

import skyvssea.model.piece.AbstractPiece;

public interface SpecialEffect {
    void apply(AbstractPiece target);
	void remove(AbstractPiece target);
	SpecialEffect copy();
}
