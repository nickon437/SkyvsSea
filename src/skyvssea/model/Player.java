package skyvssea.model;

import com.google.java.contract.Requires;
import javafx.scene.paint.Color;
import skyvssea.model.piece.AbstractPiece;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class Player {
    private String name;
    private Color color;
    private Map<Hierarchy, List<AbstractPiece>> pieces;
    private int numUndos = STANDARD_NUM_UNDOS;

    private static final int STANDARD_NUM_UNDOS = 3;

    @Requires("name !=  null && color != null")
    public Player(String name, Color color, Map<Hierarchy, List<AbstractPiece>> pieces) {
        this.name = name;
        this.color = color;
        this.pieces = pieces;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public boolean hasPiece(AbstractPiece piece) {
        for (Map.Entry<Hierarchy, List<AbstractPiece>> entry : pieces.entrySet()) {
            if (entry.getValue().contains(piece)) {
                return true;
            }
        }
        return false;
    }

    public void reduceNumUndos() {
        numUndos--;
    }

    /**
     * Disable the undo function if a player selected undo and then made any kind of move
     * @return boolean
     */
    public boolean validateUndoAvailability() {
        if (numUndos < STANDARD_NUM_UNDOS) {
            numUndos = 0;
            return false;
        } else {
        	return true;
        }
    }

    public boolean isUndoAvailabile() {
        return numUndos > 0 ? true : false;
    }
}
