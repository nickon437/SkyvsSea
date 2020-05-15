package skyvssea.model.specialeffect;

import skyvssea.model.Hierarchy;
import skyvssea.model.Stat;
import skyvssea.model.piece.AbstractPiece;

public class Strengthening extends AbstractSpecialEffect {

    public Strengthening() {
        super("Strengthening", DEFAULT_CASTER_TURN);
    }

    @Override
    public void apply(AbstractPiece target) throws CloneNotSupportedException {
        Stat<Hierarchy> levelStat = target.getLevelStat();
        originalValueStats.add(levelStat.clone());

        Hierarchy modifiedLevel = levelStat.getValue().upgrade();
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
        return "Increase the hierarchy of a piece";
    }
}
