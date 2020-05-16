package skyvssea.model.piece;

import com.google.java.contract.Ensures;
import skyvssea.model.*;
import skyvssea.model.command.Command;
import skyvssea.model.command.HistoryManager;
import skyvssea.model.command.UpdateCounterCommand;
import skyvssea.model.specialeffect.SpecialEffect;
import skyvssea.model.specialeffect.TargetType;

public abstract class AbstractPiece extends GameObject {
    private String name;
    private Hierarchy attackLevel;
	private Hierarchy defenceLevel;
    private int moveRange;
    private Direction[] moveDirections;
    private Direction[] attackDirections;
    private int attackRange;
    private SpecialEffect specialEffect;
    private final int DEFAULT_SPECIAL_EFFECT_COOLDOWN;
    private int specialEffectCounter; // 0 = ready to use special effect

	private SpecialEffectManagerInterface specialEffectManagerProxy;

    protected AbstractPiece(String name, Hierarchy attackLevel, Hierarchy defenceLevel, int moveRange,
                            Direction[] moveDirection, int attackRange, SpecialEffectCode specialEffectCode,
                            int specialEffectCooldown) {
    	this.name = name;
    	this.attackLevel = attackLevel;
    	this.defenceLevel = defenceLevel;
    	this.moveRange = moveRange;
    	this.moveDirections = moveDirection;
    	this.attackDirections = new Direction[] {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}; //Default
    	this.attackRange = attackRange;
    	specialEffect = SpecialEffectFactory.getInstance().createSpecialEffect(specialEffectCode);
        DEFAULT_SPECIAL_EFFECT_COOLDOWN = specialEffectCooldown;
        specialEffectCounter = 0;
    }

    public String getName() { return name; }

    public Hierarchy getAttackLevel() { return attackLevel; }
    public void setAttackLevel(Hierarchy attackLevel) { this.attackLevel = attackLevel; }

    public Hierarchy getDefenceLevel() { return defenceLevel; }
    public void setDefenceLevel(Hierarchy defenceLevel) { this.defenceLevel = defenceLevel; }

    public int getMoveRange() { return moveRange; }
    public void setMoveRange(int moveRange) { this.moveRange = moveRange; }

    public Direction[] getMoveDirections() { return moveDirections; }

    public int getAttackRange() { return attackRange; }
    public void setAttackRange(int attackRange) { this.attackRange = attackRange; }

    public SpecialEffect getSpecialEffect() { return specialEffect; }

    public int getDefaultSpecialEffectCooldown() { return DEFAULT_SPECIAL_EFFECT_COOLDOWN; }

    public int getSpecialEffectCounter() { return specialEffectCounter; }
    public void setSpecialEffectCounter(int specialEffectCounter) { this.specialEffectCounter = specialEffectCounter; }

	public boolean isSpecialEffectAvailable() {
		return specialEffect != null && specialEffectCounter <= 0;
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
        if (specialEffect == null) { return "Not applicable"; }
        String summary = "Special effect's name (SE): " +  specialEffect.getName() + "\n" +
                "SE effective duration: " + specialEffect.getEffectiveDuration() + "\n" +
                "SE cooldown duration: " + DEFAULT_SPECIAL_EFFECT_COOLDOWN + "\n" +
                "SE remaining cooldown duration: " + specialEffectCounter + "\n" +
                "SE description: " + specialEffect.toString();
        return summary;
    }
    
    @Ensures("specialEffectCounter >= 0")
    public void updateStatus(HistoryManager historyManager) {
        if (specialEffectCounter > 0) {
            int newSpecialEffectCounter = specialEffectCounter--;
            Command updateCounterCommand = new UpdateCounterCommand(this, newSpecialEffectCounter);
            historyManager.storeAndExecute(updateCounterCommand);
        }
//        if (specialEffectCounter > 0) {
//            specialEffectCounter--;
//        }
        getSpecialEffectManagerProxy().updateEffectiveDuration(historyManager);
    }

	public TargetType getSpecialEffectTargetType() {
		return specialEffect.getTargetType();
	}

	public Direction[] getAttackDirections() {
		return attackDirections;
	}
}
