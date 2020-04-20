package skyvssea.model;


import javafx.scene.paint.Color;

public class Player {
    private String name;
    private Color colour;
    private boolean status = false;


    public Player(String name, Color colour) {
        this.name = name;
        this.colour = colour;
        this.status = false;
    }

    public String getName() {
        return name;
    }

    public Color getColour() {
        return colour;
    }

    public boolean isStatus() { return status; }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
