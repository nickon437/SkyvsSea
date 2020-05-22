package skyvssea.model.piece;

import java.util.List;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

import skyvssea.model.*;
import skyvssea.model.specialeffect.SpecialEffect;
import skyvssea.model.specialeffect.SpecialEffectContainer;
import skyvssea.model.specialeffect.TargetType;

public abstract class AbstractPiece extends GameObject {
    private String name;
    private Hierarchy attackLevel;
	private Hierarchy defenceLevel;
    private int moveRange;
    private int attackRange;
    private Direction[] moveDirections;
    private Direction[] attackDirections;
    private SpecialEffectCode specialEffectCode;
    private SpecialEffectCode passiveEffectCode;
    private SpecialEffectContainer specialEffect;
    private SpecialEffectContainer passiveEffect;
    private final int DEFAULT_SPECIAL_EFFECT_COOLDOWN;
    private int specialEffectCounter; // 0 = ready to use special effect
	private SpecialEffectManagerInterface specialEffectManagerProxy;
	private boolean passiveEffectActivated;

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
    	this.specialEffectCode = specialEffectCode;
        DEFAULT_SPECIAL_EFFECT_COOLDOWN = specialEffectCooldown;
        specialEffectCounter = 0;
        passiveEffectActivated = false;
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

    private int getSpecialEffectCounter() { return specialEffectCounter; }

	public void performSpecialEffect(AbstractPiece target) {
		SpecialEffectContainer copiedSpecialEffect = SpecialEffectFactory.getInstance().copy(getSpecialEffect());
	    if (copiedSpecialEffect != null && getSpecialEffectCounter() <= 0 && !passiveEffectActivated) {
			target.getSpecialEffectManagerProxy().add(copiedSpecialEffect);
    		resetSpecialEffectCounter();    		
    	}
	}
	
	@Requires("passiveEffectActivated == true && passiveEffect != null")
	public void passPassiveEffect(List<Tile> tiles, PlayerManager playerManager) {
		// No need to duplicate passiveEffect when assigning it to Tile as Tile is responsible of duplicating it when performing it to pieces
		for (Tile currentTile : tiles) {
			currentTile.addSpecialEffect(passiveEffect);
			if (currentTile.hasPiece()) {
				AbstractPiece currentPiece = (AbstractPiece) currentTile.getGameObject();
				if (passiveEffect.usableOnPiece(currentPiece, playerManager)) {
					performPassiveEffect(currentPiece);					
				}
			}
		}
	}
	
	public void removePassiveEffect(List<Tile> tiles) {
		for (Tile currentTile : tiles) {
			for (SpecialEffectContainer passiveEffect : currentTile.getSpecialEffects()) {
				if (passiveEffect == this.passiveEffect) {
					currentTile.removeSpecialEffect(passiveEffect);
					if (currentTile.hasPiece()) {
						AbstractPiece castedPiece = (AbstractPiece) currentTile.getGameObject();
						castedPiece.specialEffectManagerProxy.remove(passiveEffect);
					}
					break;
				}
			}		
		}
	}
	
	@Requires("passiveEffectActivated == true")
	public void performPassiveEffect(AbstractPiece target) {
		SpecialEffectContainer copiedPassiveEffect = SpecialEffectFactory.getInstance().copy(passiveEffect);
		if (copiedPassiveEffect != null) {
			target.getSpecialEffectManagerProxy().add(copiedPassiveEffect);   		
		}
	}
    
    private void resetSpecialEffectCounter() { specialEffectCounter = DEFAULT_SPECIAL_EFFECT_COOLDOWN; }

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
                "SE cooldown duration: " + DEFAULT_SPECIAL_EFFECT_COOLDOWN + "\n" +
                "SE remaining cooldown duration: " + specialEffectCounter + "\n" +
                "SE description: " + getSpecialEffect().toString();
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
		if (passiveEffect.getTargetType() == TargetType.SELF) {
			if (passiveEffectActivated) {
				// No need to duplicate passiveEffect since it is only applied to the piece itself
				specialEffectManagerProxy.add(passiveEffect); 
			} else {
				specialEffectManagerProxy.remove(passiveEffect); 
			}			
		}
	}

	public boolean isPassiveEffectTransmittable() {
		return passiveEffect.getTargetType() != TargetType.SELF;
	}

	public SpecialEffectContainer getSpecialEffect() {
		if (specialEffect == null) {
			specialEffect = SpecialEffectFactory.getInstance().createSpecialEffect(this, specialEffectCode);
		}
		return specialEffect;
	}

	public SpecialEffectContainer getPassiveEffect() {
		if (passiveEffect == null) {
			passiveEffect = SpecialEffectFactory.getInstance().createSpecialEffect(this, passiveEffectCode);
		}
		return passiveEffect;
	}
}
