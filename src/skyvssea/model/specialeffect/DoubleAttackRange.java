package skyvssea.model.specialeffect;

import skyvssea.model.Stat;
import skyvssea.model.piece.Piece;

public class DoubleAttackRange extends AbstractSpecialEffect {

    public DoubleAttackRange() {
        super("Double attack range", DEFAULT_CASTER_TURN);
    }

    @Override
    public void apply(Piece target) throws CloneNotSupportedException {
        Stat<Integer> attackRangeStat = target.getAttackRangeStat();
        originalValueStats.add(attackRangeStat.clone());

        int modifiedAttackRange = attackRangeStat.getValue() * 2;
        attackRangeStat.setValue(Integer.valueOf(modifiedAttackRange));
        currentValueStats.add(attackRangeStat);
    }

    @Override
    protected void remove() {
        Stat<Integer> attackRangeStat = currentValueStats.get(0);
        int originalAttackRange = (int) originalValueStats.get(0).getValue();
        attackRangeStat.setValue(Integer.valueOf(originalAttackRange));
    }

    @Override
    public String toString() {
        return "Double a piece attack range";
    }
}
