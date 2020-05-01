package skyvssea.model.specialeffect;

import skyvssea.model.Stat;
import skyvssea.model.piece.Piece;

import java.util.ArrayList;

public abstract class AbstractSpecialEffect {
    protected final int DEFAULT_EFFECTIVE_NUM_TURN;

    // Nick - I leave it as list because maybe it will be useful for an SE that is a combination of SE like freezing, idk?
    protected ArrayList<Stat> affectedStats = new ArrayList<>();
    protected int remainingEffectiveNumTurn;

    protected AbstractSpecialEffect(int defaultEffectiveNumTurn) {
        this.DEFAULT_EFFECTIVE_NUM_TURN = defaultEffectiveNumTurn;
    }

    public abstract void apply(Piece target);
    public abstract void remove();
}
