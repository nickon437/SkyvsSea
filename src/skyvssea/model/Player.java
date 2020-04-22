package skyvssea.model;

import com.google.java.contract.Requires;
import javafx.scene.paint.Color;

public class Player {
    private String name;
    private Color color;
    private Team team;

    @Requires("name !=  null && color != null")
    public Player(String name, Color color, Team team) {
        this.name = name;
        this.color = color;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
