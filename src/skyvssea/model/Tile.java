package skyvssea.model;

import com.google.java.contract.Requires;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.specialeffect.SpecialEffectObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

public class Tile extends Observable implements AvatarCore {
    private Avatar avatar;
    private int x;
	private int y;
    private GameObject gameObject;
    private Set<SpecialEffectObject> specialEffects;

	private boolean isHighlighted;
    private boolean isScanned; // Nick - TODO: Will implement highlightScanTile later. But need to write our own Obs classes

    @Requires("x >= 0 && y >= 0 && x < skyvssea.view.BoardPane.NUM_SIDE_CELL && y < skyvssea.view.BoardPane.NUM_SIDE_CELL")
	public Tile(int x, int y) {
        this.x = x;
		this.y = y;
        this.gameObject = null;
    }

    public boolean hasGameObject() { return gameObject != null; }

    public boolean hasPiece() {
        return gameObject != null && gameObject instanceof AbstractPiece;
    }

    public GameObject getGameObject() { return gameObject; }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
        if (gameObject.getAvatar() != null) {
            setChanged();
            notifyObservers(gameObject.getAvatar());
        }
    }

    public void removeGameObject() { 
    	this.gameObject = null; 
    	setChanged();
        notifyObservers(null);
    }

    public boolean isHighlighted() { return isHighlighted; }

    public void setHighlighted(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
        setChanged();
        notifyObservers(isHighlighted);
    }

	public int getX() { return x; }
	public int getY() { return y; }

    @Override
    public void addAvatar(Avatar avatar) { this.avatar = avatar; }

    @Override
    public Avatar getAvatar() { return avatar; }

    /**
     * Invoked when a piece with activated passiveEffect makes a move
     * @param specialEffect to be added to this tile
     * @param playerManager used to determine if specialEffect is applicable to the piece on this tile
     */
	public void addSpecialEffect(SpecialEffectObject specialEffect, PlayerManager playerManager) {
		if (specialEffects == null) {
			specialEffects = new HashSet<>();
		}
		
		if (specialEffects.add(specialEffect) && hasPiece()) {
			AbstractPiece currentPiece = (AbstractPiece) gameObject;
			if (specialEffect.usableOnPiece(currentPiece, playerManager)) {
				currentPiece.receiveSpecialEffect(specialEffect);		
			}
		}
	}
	
	/**
	 * Invoked when the piece with activated passiveEffect moves away or turns off its passiveEffect
	 * @param specialEffect to be removed from this tile
	 */
	public void removeSpecialEffect(SpecialEffectObject specialEffect) {
		if (specialEffects != null) {
			specialEffects.remove(specialEffect);		
			if (hasPiece()) {
				AbstractPiece castedPiece = (AbstractPiece) gameObject;
				castedPiece.getSpecialEffectManagerProxy().remove(specialEffect);
			}
		}	
	}

	/**
	 * Invoked when a piece moves to another tile
	 * The piece will receive all applicable specialEffects stored in this tile
	 * @param playerManager used to determine if a specialEffect is applicable to the piece
	 */
	public void applySpecialEffects(PlayerManager playerManager) {
		if (specialEffects != null && gameObject instanceof AbstractPiece) {
			AbstractPiece piece = (AbstractPiece) gameObject;
			for (SpecialEffectObject specialEffect : specialEffects) {
				if (specialEffect.usableOnPiece(piece, playerManager)) {
					SpecialEffectObject copy = (SpecialEffectObject) specialEffect.copy();
					piece.getSpecialEffectManagerProxy().add(copy);
				}
			}
		}
	}

	/**
	 * Invoked when a piece moves to another tile
	 * Removes all specialEffects applied to the piece when it landed on this tile
	 */
	public void removeSpecialEffects() {
		if (specialEffects != null) {
			AbstractPiece piece = (AbstractPiece) gameObject;
			for (SpecialEffectObject specialEffect : specialEffects) {
				piece.getSpecialEffectManagerProxy().remove(specialEffect);
			}
		}
	}

}
