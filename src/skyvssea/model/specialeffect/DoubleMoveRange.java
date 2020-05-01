package skyvssea.model.specialeffect;

import skyvssea.model.Stat;
import skyvssea.model.piece.Piece;

public class DoubleMoveRange extends AbstractSpecialEffect {

    public DoubleMoveRange() {
        super(2);
    }

    @Override
    public void apply(Piece target) {
        Stat<Integer> numMoveStat = target.getNumMoveStat();
        numMoveStat.setValue(target.getNumMove() * 2);
        affectedStats.add(numMoveStat);
    }

    @Override
    public void remove() {
        Stat<Integer> affectedStat = affectedStats.get(0);
        affectedStat.setValue(affectedStat.getValue() / 2);
    }
}
