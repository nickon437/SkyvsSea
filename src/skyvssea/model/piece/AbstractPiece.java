package skyvssea.model.piece;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

import skyvssea.model.*;
import skyvssea.model.command.Command;
import skyvssea.model.command.HistoryManager;
import skyvssea.model.command.UpdateCounterCommand;
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
    protected SpecialEffectObject activeEffect;
    protected SpecialEffectObject passiveEffect;
    private final int activeEffectCoolDown;
    private int activeEffectCounter; // 0 = ready to use special effect
	private SpecialEffectManagerInterface specialEffectManagerProxy;
	private boolean passiveEffectActivated;

	protected AbstractPiece(String name, Hierarchy attackLevel, Hierarchy defenceLevel, int moveRange,
                            Direction[] moveDirection, int attackRange, int activeEffectCoolDown) {
    	this.name = name;
    	this.attackLevel = initialAttackLevel = attackLevel;
    	this.defenceLevel = initialDefenceLevel = defenceLevel;
    	this.moveRange = initialMoveRange = moveRange;
    	this.moveDirections = moveDirection;
    	this.attackDirections = new Direction[] {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}; //Default
    	this.attackRange = initialAttackRange = attackRange;
        this.activeEffectCoolDown = activeEffectCoolDown;
        activeEffectCounter = 0;
        passiveEffectActivated = false;
    }

    public String getName() { return name; }

    public Hierarchy getAttackLevel() { return attackLevel; }
    public void setAttackLevel(Hierarchy attackLevel) { this.attackLevel = attackLevel; }

    public Hierarchy getDefenceLevel() { return defenceLevel; }
    public void setDefenceLevel(Hierarchy defenceLevel) { this.defenceLevel = defenceLevel; }

	public Hierarchy getInitialAttackLevel() { return initialAttackLevel; }
	public Hierarchy getInitialDefenceLevel() { return initialDefenceLevel; }
	public int getInitialMoveRange() {return initialMoveRange; }
	public int getInitialAttackRange() { return initialAttackRange; }
	
    public int getMoveRange() { return moveRange; }
    public void setMoveRange(int moveRange) { this.moveRange = moveRange; }
   
    public Direction[] getMoveDirections() { return moveDirections; }
	public Direction[] getAttackDirections() { return attackDirections; }
    public int getAttackRange() { return attackRange; }
    public void setAttackRange(int attackRange) { this.attackRange = attackRange; }
    

    @Requires("activeEffectCounter <= 0 && !passiveEffectActivated")
	public void performActiveEffect(AbstractPiece target) {
		SpecialEffectObject copiedActiveEffect = (SpecialEffectObject) activeEffect.copy();
	    if (copiedActiveEffect != null) {
			target.getSpecialEffectManagerProxy().add(copiedActiveEffect);
    		resetActiveEffectCounter();    		
    	}
	}
	
    public abstract SpecialEffectObject getActiveEffect();
    
    public int getActiveEffectCoolDown() { return activeEffectCoolDown; }
    
    public int getActiveEffectCounter() { return activeEffectCounter; }
    public void setSpecialEffectCounter(int activeEffectCounter) { this.activeEffectCounter = activeEffectCounter; }
    
    private void resetActiveEffectCounter() { activeEffectCounter = activeEffectCoolDown; }
    
    public boolean isActiveEffectAvailable() {
    	return getActiveEffect() != null && activeEffectCounter <= 0 && !passiveEffectActivated;
    }
    
    public TargetType getActiveEffectTargetType() {
    	return getActiveEffect().getTargetType();
    }
    
    public abstract SpecialEffectObject getPassiveEffect();
    
	@Requires("passiveEffectActivated == true")
	public void performPassiveEffect(AbstractPiece target) {
		SpecialEffectObject copiedPassiveEffect = (SpecialEffectObject) getPassiveEffect().copy();
		if (copiedPassiveEffect != null) {
			target.getSpecialEffectManagerProxy().add(copiedPassiveEffect);   		
		}
	}
	
	public boolean isPassiveEffectActivated() {
		return passiveEffectActivated;
	}
	
	public void setPassiveEffectActivated(boolean activated) {
		passiveEffectActivated = activated;
	}	
	
	public boolean isPassiveEffectTransmittable() {
		return getPassiveEffect().getTargetType() != TargetType.SELF;
	}
	
	public void receiveSpecialEffect(SpecialEffectObject specialEffect) {
		SpecialEffectObject copiedSpecialEffect = (SpecialEffectObject) specialEffect.copy();
		getSpecialEffectManagerProxy().add(copiedSpecialEffect);
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
        if (getActiveEffect() == null) { return "Not applicable"; }
        String summary = "Active effect's name (AE): " +  getActiveEffect().getName() + "\n" +
                "AE effective duration: " + getActiveEffect().getEffectiveDuration() + "\n" +
                "AE cooldown duration: " + activeEffectCoolDown + "\n" +
                "AE remaining cooldown duration: " + activeEffectCounter + "\n" +
                "AE description: " + getActiveEffect().toString() + "\n" +
                "Passive effect: " + getPassiveEffect().getName();
        return summary;
    }

    @Requires("historyManager != null")
    @Ensures("activeEffectCounter >= 0")
    public void updateStatus(HistoryManager historyManager) {
        if (activeEffectCounter > 0) {
            int newActiveEffectCounter = activeEffectCounter - 1;
            Command updateCounterCommand = new UpdateCounterCommand(this, newActiveEffectCounter);
            historyManager.storeAndExecute(updateCounterCommand);
        }
        getSpecialEffectManagerProxy().updateEffectiveDuration(historyManager);
    }
}
