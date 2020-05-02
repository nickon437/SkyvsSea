package skyvssea.model.specialeffect;

import skyvssea.model.Stat;
import skyvssea.model.piece.Piece;

public class Freezing extends AbstractSpecialEffect {

    public Freezing() { super(DEFAULT_CASTER_TURN); }

    @Override
    public void apply(Piece target) throws CloneNotSupportedException {
        // Attack range
        Stat<Integer> attackRangeStat = target.getAttackRangeStat();
        originalValueStats.add(attackRangeStat.clone());

        int modifiedAttackRange = 0;
        attackRangeStat.setValue(Integer.valueOf(modifiedAttackRange));
        currentValueStats.add(attackRangeStat);

        // Move range
        Stat<Integer> moveRangeStat = target.getNumMoveStat();
        originalValueStats.add(moveRangeStat.clone());

        int modifiedMoveRange = 0;
        moveRangeStat.setValue(Integer.valueOf(modifiedMoveRange));
        currentValueStats.add(moveRangeStat);
    }

    @Override
    protected void remove() {
        Stat<Integer> attackRangeStat = currentValueStats.get(0);
        int originalAttackRange = (int) originalValueStats.get(0).getValue();
        attackRangeStat.setValue(Integer.valueOf(originalAttackRange));

        Stat<Integer> moveRangeStat = currentValueStats.get(1);
        int originalMoveRange = (int) originalValueStats.get(1).getValue();
        moveRangeStat.setValue(Integer.valueOf(originalMoveRange));
    }
}
