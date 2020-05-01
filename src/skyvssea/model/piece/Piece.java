package skyvssea.model.piece;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import skyvssea.model.Direction;
import skyvssea.model.Hierarchy;
import skyvssea.model.SpecialEffectManager;
import skyvssea.model.Stat;
import skyvssea.model.specialeffect.AbstractSpecialEffect;

public abstract class Piece {
    private String name;
    private Stat<Hierarchy> level;
    private Stat<Integer> numMove;
    private Direction[] moveDirection;
    private Stat<Integer> attackRange;
    private AbstractSpecialEffect specialEffect;
    private final Stat<Integer> DEFAULT_SPECIAL_EFFECT_COOLDOWN;
    private int specialEffectCounter; // 0 = ready to use special effect
    private SpecialEffectManager specialEffectManager;

    protected Piece(String name, Hierarchy level, int numMove, Direction[] moveDirection, int attackRange,
                    AbstractSpecialEffect specialEffect, int specialEffectCooldown) {
    	this.name = name;
        this.level = new Stat(level);
    	this.numMove = new Stat(Integer.valueOf(numMove));
    	this.moveDirection = moveDirection;
    	this.attackRange = new Stat(Integer.valueOf(attackRange));
    	this.specialEffect = specialEffect;
        this.DEFAULT_SPECIAL_EFFECT_COOLDOWN = new Stat(Integer.valueOf(specialEffectCooldown));
        this.specialEffectCounter = 0;
    }

    public String getName() { return name; }

    public int getNumMove() { return numMove.getValue(); }
    public Stat<Integer> getNumMoveStat() { return numMove; }

    public Direction[] getMoveDirection() { return moveDirection; }

    public int getAttackRange() { return attackRange.getValue(); }
    public Stat<Integer> getAttackRangeStat() { return attackRange; }

    @Requires("specialEffectCounter <= 0")
    public void performSpecialEffect(Piece target) {
        target.getSpecialEffectManager().add(specialEffect);
        specialEffectCounter = DEFAULT_SPECIAL_EFFECT_COOLDOWN.getValue();
    }
    //Idea: Use prototype creation pattern to create a clone of self specialEffect and pass it to target

    protected AbstractSpecialEffect getSpecialEffect() {
		return specialEffect;
	}

	public SpecialEffectManager getSpecialEffectManager() {
        if (specialEffectManager == null) {
            specialEffectManager = new SpecialEffectManager(this);
        }
        return specialEffectManager;
    }

    @Ensures("specialEffectCounter >= 0")
    public void updateStatus() {
        if (specialEffectCounter > 0) { specialEffectCounter--; }
        specialEffectManager.updateEffectiveDuration();
    }
}
