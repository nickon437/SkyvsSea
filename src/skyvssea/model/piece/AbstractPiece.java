package skyvssea.model.piece;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

import skyvssea.model.*;
import skyvssea.model.specialeffect.SpecialEffectObject;
import skyvssea.model.specialeffect.TargetType;

public abstract class AbstractPiece extends GameObject {
    private String name;
    private Hierarchy attackLevel;
    private final Hierarchy initialAttackLevel;
	private Hierarchy defenceLevel;
	private final Hierarchy initialDefenceLevel;
    private int moveRange;
    private final int initialMoveRange;
	private int attackRange;
	private final int initialAttackRange;
    private Direction[] moveDirections;
    private Direction[] attackDirections;
    private SpecialEffectCode specialEffectCode;
    private SpecialEffectObject specialEffect;
    protected SpecialEffectObject passiveEffect;
    private final int specialEffectCoolDown;
    private int specialEffectCounter; // 0 = ready to use special effect
	private SpecialEffectManagerInterface specialEffectManagerProxy;
	private boolean passiveEffectActivated;

	protected AbstractPiece(String name, Hierarchy attackLevel, Hierarchy defenceLevel, int moveRange,
                            Direction[] moveDirection, int attackRange, SpecialEffectCode specialEffectCode,
                            int specialEffectCooldown) {
    	this.name = name;
    	this.attackLevel = initialAttackLevel = attackLevel;
    	this.defenceLevel = initialDefenceLevel = defenceLevel;
    	this.moveRange = initialMoveRange = moveRange;
    	this.moveDirections = moveDirection;
    	this.attackDirections = new Direction[] {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}; //Default
    	this.attackRange = initialAttackRange = attackRange;
    	this.specialEffectCode = specialEffectCode;
        this.specialEffectCoolDown = specialEffectCooldown;
        specialEffectCounter = 0;
        passiveEffectActivated = false;
    }

    public String getName() { return name; }

    public Hierarchy getAttackLevel() { return attackLevel; }
    public void setAttackLevel(Hierarchy attackLevel) { this.attackLevel = attackLevel; }

    public Hierarchy getDefenceLevel() { return defenceLevel; }
    public void setDefenceLevel(Hierarchy defenceLevel) { this.defenceLevel = defenceLevel; }

	public Hierarchy getInitialAttackLevel() { return initialAttackLevel; }

	public Hierarchy getInitialDefenceLevel() { return initialDefenceLevel; }
	
    public int getMoveRange() { return moveRange; }
    public void setMoveRange(int moveRange) { this.moveRange = moveRange; }

    public int getInitialMoveRange() {return initialMoveRange; }
    
    public Direction[] getMoveDirections() { return moveDirections; }

    public int getAttackRange() { return attackRange; }
    public void setAttackRange(int attackRange) { this.attackRange = attackRange; }
    
    public int getInitialAttackRange() { return initialAttackRange; }

    @Requires("specialEffectCounter <= 0 && !passiveEffectActivated")
	public void performSpecialEffect(AbstractPiece target) {
		SpecialEffectObject copiedSpecialEffect = (SpecialEffectObject) specialEffect.copy();
	    if (copiedSpecialEffect != null) {
			target.getSpecialEffectManagerProxy().add(copiedSpecialEffect);
    		resetSpecialEffectCounter();    		
    	}
	}
	
	@Requires("passiveEffectActivated == true")
	public void performPassiveEffect(AbstractPiece target) {
		SpecialEffectObject copiedPassiveEffect = (SpecialEffectObject) getPassiveEffect().copy();
		if (copiedPassiveEffect != null) {
			target.getSpecialEffectManagerProxy().add(copiedPassiveEffect);   		
		}
	}
	
	public void receiveSpecialEffect(SpecialEffectObject specialEffect) {
		SpecialEffectObject copiedPassiveEffect = (SpecialEffectObject) specialEffect.copy();
		getSpecialEffectManagerProxy().add(copiedPassiveEffect);
	}
    
    private void resetSpecialEffectCounter() { specialEffectCounter = specialEffectCoolDown; }

	public boolean isSpecialEffectAvailable() {
		return getSpecialEffect() != null && specialEffectCounter <= 0 && !passiveEffectActivated;
    }

    @Ensures("specialEffectManagerProxy != null")
    public SpecialEffectManagerInterface getSpecialEffectManagerProxy() {
        if (specialEffectManagerProxy == null) {
            specialEffectManagerProxy = new SpecialEffectManagerProxy(this);
        }
        return specialEffectManagerProxy;
    }

    @Override
    public String toString() {
        String summary = "Name: " + getName() + "\n" +
                "AttackLevel: " + getAttackLevel() + "\n" +
                "DefenceLevel: " + getDefenceLevel() + "\n" +
                "Move range: " + getMoveRange() + "\n" +
                "Movable directions: ";
        for (Direction direction : getMoveDirections()) {
            summary += direction.toString() + " ";
        }
        summary += "\nAttack range: " + getAttackRange();
        return summary;
    }
	
    // Nick - Feel free to change the wording so that it sounds more cohesive
    public String getSpecialEffectSummary() {
        if (getSpecialEffect() == null) { return "Not applicable"; }
        String summary = "Special effect's name (SE): " +  getSpecialEffect().getName() + "\n" +
                "SE effective duration: " + getSpecialEffect().getEffectiveDuration() + "\n" +
                "SE cooldown duration: " + specialEffectCoolDown + "\n" +
                "SE remaining cooldown duration: " + specialEffectCounter + "\n" +
                "SE description: " + getSpecialEffect().toString() + "\n" +
                "Passive effect: " + getPassiveEffect().getName();
        return summary;
    }
    
    @Ensures("specialEffectCounter >= 0")
    public void updateStatus() {
        if (specialEffectCounter > 0) { specialEffectCounter--; }
        getSpecialEffectManagerProxy().updateEffectiveDuration();
    }

	public TargetType getSpecialEffectTargetType() {
		return getSpecialEffect().getTargetType();
	}

	public Direction[] getAttackDirections() {
		return attackDirections;
	}
	
    public boolean isPassiveEffectActivated() {
		return passiveEffectActivated;
	}

	public void togglePassiveEffectSwitch() {
		passiveEffectActivated = !passiveEffectActivated;
		
		// Handle applying/removing passive effect to/from itself if the passive effect is TargetType.SELF
		if (getPassiveEffect().getTargetType() == TargetType.SELF) {
			if (passiveEffectActivated) {
				// No need to duplicate passiveEffect since it is only applied to the piece itself
				getSpecialEffectManagerProxy().add(getPassiveEffect()); 
			} else {
				getSpecialEffectManagerProxy().remove(getPassiveEffect()); 
			}			
		}
	}

	public boolean isPassiveEffectTransmittable() {
		return getPassiveEffect().getTargetType() != TargetType.SELF;
	}

	public SpecialEffectObject getSpecialEffect() {
		if (specialEffect == null) {
			specialEffect = SpecialEffectFactory.getInstance().createSpecialEffect(this, specialEffectCode);
		}
		return specialEffect;
	}
	
	public abstract SpecialEffectObject getPassiveEffect();
}
