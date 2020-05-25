package skyvssea.model;

import com.google.java.contract.Requires;
import skyvssea.model.observer.Observer;
import skyvssea.model.observer.Subject;
import skyvssea.model.piece.AbstractPiece;

import java.util.ArrayList;
import java.util.List;

public class Tile implements Subject, AvatarCore {

    private List<Observer> observers = new ArrayList<>();
    private Avatar avatar;
    public int x;
	public int y;
    private GameObject gameObject;
    private boolean isHighlighted;
    private boolean isScanned; // Nick - TODO: Will implement highlightScanTile later. But need to write our own Obs classes

    @Requires("x >= 0 && y >= 0")
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
            notifyObservers(gameObject.getAvatar());
        }
    }

    public void removeGameObject() { 
    	this.gameObject = null;
        notifyObservers(null);
    }

    public boolean isHighlighted() { return isHighlighted; }

    public void setHighlighted(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
        notifyObservers(isHighlighted);
    }

	public int getX() { return x; }
	public int getY() { return y; }

    @Override
    public void addAvatar(Avatar avatar) { this.avatar = avatar; }

    @Override
    public Avatar getAvatar() { return avatar; }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(Object arg) {
        for (Observer observer : observers) {
            observer.update(this, arg);
        }
    }
}
