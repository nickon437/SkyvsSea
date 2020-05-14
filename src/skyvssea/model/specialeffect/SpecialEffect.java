package skyvssea.model.specialeffect;

import skyvssea.model.piece.AbstractPiece;

public interface SpecialEffect {
    int DEFAULT_CASTER_TURN = 3;

    void apply(AbstractPiece target);
	void remove(AbstractPiece target);
	boolean updateEffectiveDuration();
	int getEffectiveDuration();
	void setEffectiveDuration(int effectiveDuration);
	String getName();
	SpecialEffect copy();
	TargetType getTargetType();
}
