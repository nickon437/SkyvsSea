package skyvssea.model.piece;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import skyvssea.model.*;
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
                    SpecialEffectCode specialEffectCode, int specialEffectCooldown) {
    	this.name = name;
        this.level = new Stat(level);
    	this.numMove = new Stat(Integer.valueOf(numMove));
    	this.moveDirection = moveDirection;
    	this.attackRange = new Stat(Integer.valueOf(attackRange));
    	this.specialEffect = SpecialEffectFactory.getInstance().createSpecialEffect(specialEffectCode);
        this.DEFAULT_SPECIAL_EFFECT_COOLDOWN = new Stat(Integer.valueOf(specialEffectCooldown));
        this.specialEffectCounter = 0;
    }

    public String getName() { return name; }

    public Hierarchy getLevel() { return level.getValue(); }
    public Stat<Hierarchy> getLevelStat() { return level; }

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

	public boolean isSpecialEffectAvailable() {
        return (specialEffect != null && specialEffectCounter <= 0) ? true : false;
    }

	public SpecialEffectManager getSpecialEffectManager() {
        if (specialEffectManager == null) {
            specialEffectManager = new SpecialEffectManager(this);
        }
        return specialEffectManager;
    }

    @Override
    public String toString() {
        String summary = "Name: " + getName() + "\n" +
                "Level: " + getLevel() + "\n" +
                "Move range: " + getNumMove() + "\n" +
                "Movable directions: ";
        for (Direction direction : getMoveDirection()) {
            summary += direction.toString() + " ";
        }
        summary += "\nAttack range: " + getAttackRange();
        return summary;
    }

    // Nick - Feel free to change the wording so that it sounds more cohesive
    public String getSpecialEffectSummary() {
        if (specialEffect == null) { return "Not applicable"; }
        specialEffect.getName();
        specialEffect.getEffectiveDuration();
        DEFAULT_SPECIAL_EFFECT_COOLDOWN.getValue().intValue();
        specialEffect.toString();
        String summary = "Special effect's name (SE): " +  specialEffect.getName() + "\n" +
                "SE effective duration: " + specialEffect.getEffectiveDuration() + "\n" +
                "SE cooldown duration: " + DEFAULT_SPECIAL_EFFECT_COOLDOWN.getValue().intValue() + "\n" +
                "SE remaining cooldown duration: " + specialEffectCounter + "\n" +
                "SE description: " + specialEffect.toString();
        return summary;
    }

    // Nick - TODO: Show the current applied specialeffect info

    @Ensures("specialEffectCounter >= 0")
    public void updateStatus() {
        if (specialEffectManager == null) {
            specialEffectManager = new SpecialEffectManager(this);
        }
        if (specialEffectCounter > 0) { specialEffectCounter--; }
        specialEffectManager.updateEffectiveDuration();
    }
}
