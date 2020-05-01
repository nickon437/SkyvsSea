package skyvssea.model.piece;

import skyvssea.model.Direction;
import skyvssea.model.Hierarchy;
import skyvssea.model.Stat;
import skyvssea.model.specialeffect.AbstractSpecialEffect;

import java.util.List;

public abstract class Piece {
    private String name;
    private Stat<Hierarchy> level;
    private Stat<Integer> numMove;
    private Direction[] moveDirection;
    private Stat<Integer> attackRange;
    private Stat<Integer> specialEffectCounter;
    private AbstractSpecialEffect specialEffect;
    private List<AbstractSpecialEffect> appliedSpecialEffect;

    protected Piece(String name, Hierarchy level, int numMove, Direction[] moveDirection, int attackRange, int specialEffectCounter, AbstractSpecialEffect specialEffect) {
    	this.name = name;
        this.level = new Stat(level);
    	this.numMove = new Stat(Integer.valueOf(numMove));
    	this.moveDirection = moveDirection;
    	this.attackRange = new Stat(Integer.valueOf(attackRange));
    	this.specialEffectCounter = new Stat(Integer.valueOf(specialEffectCounter));
    	this.specialEffect = specialEffect;
    }

    public String getName() { return name; }

    public int getNumMove() { return numMove.getValue(); }

    public Direction[] getMoveDirection() { return moveDirection; }

    public int getAttackRange() { return attackRange.getValue(); }
    public Stat<Integer> getAttackRangeStat() { return attackRange; }

    abstract protected void performSpeEff(Piece target);
    //Idea: Use prototype creation pattern to create a clone of self specialEffect and pass it to target

    protected AbstractSpecialEffect getSpecialEffect() {
		return specialEffect;
	}
}
