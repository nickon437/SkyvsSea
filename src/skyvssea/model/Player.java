package skyvssea.model;

import com.google.java.contract.Requires;
import javafx.scene.paint.Color;
import skyvssea.model.piece.Piece;

import java.util.ArrayList;
import java.util.Map;

public class Player {
    private String name;
    private Color color;
    private Map<Hierarchy, ArrayList<Piece>> pieces;

    @Requires("name !=  null && color != null")
    public Player(String name, Color color, Map<Hierarchy, ArrayList<Piece>> pieces) {
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

    public boolean hasPiece(Piece piece) {
        for (Map.Entry<Hierarchy, ArrayList<Piece>> entry : pieces.entrySet()) {
            if (entry.getValue().contains(piece)) {
                return true;
            }
        }
        return false;
    }
}
