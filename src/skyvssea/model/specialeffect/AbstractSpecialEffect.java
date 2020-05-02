package skyvssea.model.specialeffect;

import skyvssea.model.Stat;
import skyvssea.model.piece.Piece;

import java.util.ArrayList;

public abstract class AbstractSpecialEffect {
    protected static final int DEFAULT_CASTER_TURN = 3;
//    protected final int DEFAULT_EFFECTIVE_NUM_TURN; // Nick - Commented out just incase for a power that allow other player to delay the effect duration

    // Nick - I leave it as list because maybe it will be useful for an SE that is a combination of SE like freezing, idk?
    protected ArrayList<Stat> originalValueStats = new ArrayList<>();
    protected ArrayList<Stat> currentValueStats = new ArrayList<>();
    protected String name;
    protected int effectiveDuration; // 3 = till the next turn of the caster.

    protected AbstractSpecialEffect(String name, int effectiveDuration) {
        this.name = name;
//        this.DEFAULT_EFFECTIVE_NUM_TURN = effectiveDuration;
        this.effectiveDuration = effectiveDuration;
    }

    public String getName() { return name; }

    public int getEffectiveDuration() { return effectiveDuration; }

    public boolean updateEffectiveDuration() {
        boolean isActive = true;
        effectiveDuration--;
        if (effectiveDuration <= 0) {
            remove();
            isActive = false;
        }
        return isActive;
    }

    protected void applyOnStat() {
//        Stat<Integer> moveRangeStat = target.getAttackRangeStat();
//
//        Stat<Integer> originalValueStat = new Stat<>(moveRangeStat.getValue());
//        originalValueStats.add(moveRangeStat);
//
//        moveRangeStat.setValue((moveRangeStat.getValue() * 2));
//        currentValueStats.add(moveRangeStat);
    }

    protected void saveOriginalValueStat(Stat stat) throws CloneNotSupportedException {
        originalValueStats.add(stat.clone());
    }

    public abstract void apply(Piece target) throws CloneNotSupportedException;
    protected abstract void remove();

    @Override
    public abstract String toString();
}
