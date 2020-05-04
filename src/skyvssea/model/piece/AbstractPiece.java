package skyvssea.model.piece;

import com.google.java.contract.Ensures;
import skyvssea.model.Direction;
import skyvssea.model.Hierarchy;
import skyvssea.model.SpecialEffectCode;
import skyvssea.model.SpecialEffectFactory;
import skyvssea.model.SpecialEffectManager;
import skyvssea.model.specialeffect.SpecialEffect;

public abstract class AbstractPiece {
    private String name;
    private Hierarchy attackLevel;
	private Hierarchy defenceLevel;
    private int moveRange;
    private Direction[] moveDirection;
    private int attackRange;
    private SpecialEffect specialEffect;
    protected final int DEFAULT_SPECIAL_EFFECT_COOLDOWN;
    private int specialEffectCounter; // 0 = ready to use special effect

	private SpecialEffectManager specialEffectManager;

    protected AbstractPiece(String name, Hierarchy attackLevel, Hierarchy defenceLevel, int moveRange, Direction[] moveDirection, int attackRange,
                    SpecialEffectCode specialEffectCode, int specialEffectCooldown) {
    	this.name = name;
    	this.attackLevel = attackLevel;
    	this.defenceLevel = defenceLevel;
    	this.moveRange = moveRange;
    	this.moveDirection = moveDirection;
    	this.attackRange = attackRange;
    	this.specialEffect = SpecialEffectFactory.getInstance().createSpecialEffect(specialEffectCode);
        this.DEFAULT_SPECIAL_EFFECT_COOLDOWN = specialEffectCooldown;
        this.specialEffectCounter = 0;
    }

    public String getName() { return name; }

    public int getMoveRange() { return moveRange; }

    public Direction[] getMoveDirection() { return moveDirection; }

    public int getAttackRange() { return attackRange; }
    
    public void setAttackRange(int attackRange) {
    	this.attackRange = attackRange;
    }

    public abstract void performSpecialEffect(AbstractPiece target);
    
	public void performSpecialEffect(AbstractPiece target, SpecialEffectCode specialEffectCode) {
		if (getSpecialEffectCounter() <= 0) {
			target.getSpecialEffectManager().add(SpecialEffectFactory.getInstance().createSpecialEffect(specialEffectCode));
    		resetSpecialEffectCounter();    		
    	}
	}
    
    public void resetSpecialEffectCounter() {
    	specialEffectCounter = DEFAULT_SPECIAL_EFFECT_COOLDOWN;
    }

    protected SpecialEffect getSpecialEffect() {
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
                "AttackLevel: " + getAttackLevel() + "\n" +
                "DefenceLevel: " + getDefenceLevel() + "\n" +
                "Move range: " + getMoveRange() + "\n" +
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
        String summary = "Special effect's name (SE): " +  specialEffect.getName() + "\n" +
                "SE effective duration: " + specialEffect.getEffectiveDuration() + "\n" +
                "SE cooldown duration: " + DEFAULT_SPECIAL_EFFECT_COOLDOWN + "\n" +
                "SE remaining cooldown duration: " + specialEffectCounter + "\n" +
                "SE description: " + specialEffect.toString();
        return summary;
    }
    
    @Ensures("specialEffectCounter >= 0")
    public void updateStatus() {
        if (specialEffectManager == null) {
            specialEffectManager = new SpecialEffectManager(this);
        }
        if (specialEffectCounter > 0) { specialEffectCounter--; }
        specialEffectManager.updateEffectiveDuration();
    }

	public void setMoveRange(int moveRange) {
		this.moveRange = moveRange;
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
	
	public int getSpecialEffectCounter() {
		return specialEffectCounter;
	}
	
	public void setSpecialEffectCounter(int specialEffectCounter) {
		this.specialEffectCounter = specialEffectCounter;
	}
}
