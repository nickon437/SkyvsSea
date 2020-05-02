package skyvssea.model.specialeffect;

import skyvssea.model.Hierarchy;
import skyvssea.model.Stat;
import skyvssea.model.piece.Piece;

public class Weakening extends AbstractSpecialEffect {

    public Weakening() {
        super("Weakening", DEFAULT_CASTER_TURN);
    }

    @Override
    public void apply(Piece target) throws CloneNotSupportedException {
        Stat<Hierarchy> levelStat = target.getLevelStat();
        originalValueStats.add(levelStat.clone());

        Hierarchy modifiedLevel = levelStat.getValue().downgrade();
        levelStat.setValue(modifiedLevel);
        currentValueStats.add(levelStat);
    }

    @Override
    protected void remove() {
        Stat<Hierarchy> levelStat = currentValueStats.get(0);
        Hierarchy originalLevel = (Hierarchy) originalValueStats.get(0).getValue();
        levelStat.setValue(originalLevel);
    }

    @Override
    public String toString() {
        return "Reduce the hierarchy of a piece";
    }
}
