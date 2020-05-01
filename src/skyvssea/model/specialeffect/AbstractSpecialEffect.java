package skyvssea.model.specialeffect;

import skyvssea.model.Stat;
import skyvssea.model.piece.Piece;

import java.util.ArrayList;

public abstract class AbstractSpecialEffect {
//    protected final int DEFAULT_EFFECTIVE_NUM_TURN;

    // Nick - I leave it as list because maybe it will be useful for an SE that is a combination of SE like freezing, idk?
    protected ArrayList<Stat> affectedStats = new ArrayList<>();
    protected int effectiveDuration;

    protected AbstractSpecialEffect(int effectiveDuration) {
//        this.DEFAULT_EFFECTIVE_NUM_TURN = effectiveDuration;
        this.effectiveDuration = effectiveDuration;
    }

    public boolean updateEffectiveDuration() {
        boolean isActive = true;
        effectiveDuration--;
        if (effectiveDuration <= 0) {
            remove();
            isActive = false;
        }
        return isActive;
    }

    public abstract void apply(Piece target);
    protected abstract void remove();

}
