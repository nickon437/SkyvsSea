package skyvssea.model.piece;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import skyvssea.model.Direction;
import skyvssea.model.Hierarchy;
import skyvssea.model.SpecialEffectManager;
import skyvssea.model.Stat;
import skyvssea.model.specialeffect.SpecialEffect;

public abstract class Piece {
    private String name;
    private Hierarchy attackLevel;
	private Hierarchy defenceLevel;
    private int numMove;
    private Direction[] moveDirection;
    private int attackRange;
    private SpecialEffect specialEffect;
    private final int DEFAULT_SPECIAL_EFFECT_COOLDOWN;
    private int specialEffectCounter; // 0 = ready to use special effect
    private SpecialEffectManager specialEffectManager;

    protected Piece(String name, Hierarchy attackLevel, Hierarchy defenceLevel, int numMove, Direction[] moveDirection, int attackRange,
                    SpecialEffect specialEffect, int specialEffectCooldown) {
    	this.name = name;
    	this.attackLevel = attackLevel;
    	this.defenceLevel = defenceLevel;
    	this.numMove = numMove;
    	this.moveDirection = moveDirection;
    	this.attackRange = attackRange;
    	this.specialEffect = specialEffect;
        this.DEFAULT_SPECIAL_EFFECT_COOLDOWN = specialEffectCooldown;
        this.specialEffectCounter = 0;
    }

    public String getName() { return name; }

    public int getNumMove() { return numMove; }

    public Direction[] getMoveDirection() { return moveDirection; }

    public int getAttackRange() { return attackRange; }
    
    public void setAttackRange(int attackRange) {
    	this.attackRange = attackRange;
    }

    @Requires("specialEffectCounter <= 0")
    public void performSpecialEffect(Piece target) {
        target.getSpecialEffectManager().add(specialEffect);
        specialEffectCounter = DEFAULT_SPECIAL_EFFECT_COOLDOWN;
    }
    //Idea: Use prototype creation pattern to create a clone of self specialEffect and pass it to target

    protected SpecialEffect getSpecialEffect() {
		return specialEffect;
	}

	public boolean isSpecialEffectAvailable() {
        if (specialEffectCounter <= 0) { return true; }
        return false;
    }

	public SpecialEffectManager getSpecialEffectManager() {
        if (specialEffectManager == null) {
            specialEffectManager = new SpecialEffectManager(this);
        }
        return specialEffectManager;
    }

    @Ensures("specialEffectCounter >= 0")
    public void updateStatus() {
        if (specialEffectManager == null) {
            specialEffectManager = new SpecialEffectManager(this);
        }
        if (specialEffectCounter > 0) { specialEffectCounter--; }
        specialEffectManager.updateEffectiveDuration();
    }

	public void setNumMove(int moveRange) {
		numMove = moveRange;
	}
	
    public Hierarchy getAttackLevel() {
		return attackLevel;
	}

	public void setAttackLevel(Hierarchy attackLevel) {
		this.attackLevel = attackLevel;
	}

	public Hierarchy getDefenceLevel() {
		return defenceLevel;
	}

	public void setDefenceLevel(Hierarchy defenceLevel) {
		this.defenceLevel = defenceLevel;
	}
}
