package skyvssea.model.specialeffect;

import skyvssea.model.Stat;
import skyvssea.model.piece.Piece;

public class DoubleMoveRange extends AbstractSpecialEffect {

    public DoubleMoveRange() {
        super("Double move range", DEFAULT_CASTER_TURN);
    }

    @Override
    public void apply(Piece target) throws CloneNotSupportedException {
        Stat<Integer> moveRangeStat = target.getNumMoveStat();
        originalValueStats.add(moveRangeStat.clone());

        int modifiedMoveRange = moveRangeStat.getValue() * 2;
        moveRangeStat.setValue(Integer.valueOf(modifiedMoveRange));
        currentValueStats.add(moveRangeStat);
    }

    @Override
    protected void remove() {
        Stat<Integer> moveRangeStat = currentValueStats.get(0);
        int originalMoveRange = (int) originalValueStats.get(0).getValue();
        moveRangeStat.setValue(Integer.valueOf(originalMoveRange));
    }

    @Override
    public String toString() {
        return "Double their own move range";
    }
}
