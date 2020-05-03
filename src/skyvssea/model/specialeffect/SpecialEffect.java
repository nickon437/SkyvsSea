package skyvssea.model.specialeffect;

import skyvssea.model.piece.Piece;

public interface SpecialEffect {
    static final int DEFAULT_CASTER_TURN = 3;

    void apply(Piece target);
	void remove(Piece target);
	boolean updateEffectiveDuration();
}
