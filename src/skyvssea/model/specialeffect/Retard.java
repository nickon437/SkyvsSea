package skyvssea.model.specialeffect;

import skyvssea.model.Stat;
import skyvssea.model.piece.Piece;

public class Retard extends AbstractSpecialEffect {

    public Retard() { super(DEFAULT_CASTER_TURN); }

    @Override
    public void apply(Piece target) throws CloneNotSupportedException {
        Stat<Integer> moveRangeStat = target.getNumMoveStat();
        originalValueStats.add(moveRangeStat.clone());

        int modifiedMoveRange = moveRangeStat.getValue() / 2;
        moveRangeStat.setValue(Integer.valueOf(modifiedMoveRange));
        currentValueStats.add(moveRangeStat);
    }

    @Override
    protected void remove() {
        Stat<Integer> moveRangeStat = currentValueStats.get(0);
        int originalMoveRange = (int) originalValueStats.get(0).getValue();
        moveRangeStat.setValue(Integer.valueOf(originalMoveRange));
    }
}
