package skyvssea.model;

import javafx.scene.paint.Color;

public class Player {
    private String name;
    private Color colour;

    public Player(String name, Color colour) {
        this.name = name;
        this.colour = colour;
    }

    public String getName() {
        return name;
    }

    public Color getColour() {
        return colour;
    }
}
