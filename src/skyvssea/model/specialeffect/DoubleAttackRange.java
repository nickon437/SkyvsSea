package skyvssea.model.specialeffect;

import skyvssea.model.Stat;
import skyvssea.model.piece.Piece;

public class DoubleAttackRange extends AbstractSpecialEffect {

    @Override
    public void apply(Piece target) {
        Stat<Integer> affectedStat = target.getAttackRangeStat();
        affectedStat.setValue(target.getAttackRange() * 2);
        affectedStats.add(affectedStat);
    }

    @Override
    public void remove() {
        Stat<Integer> affectedStat = affectedStats.get(0);
        affectedStat.setValue(affectedStat.getValue() / 2);
    }
}
