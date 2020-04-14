package skyvssea.model;

public class Player {
    public static final String BLUE_COLOUR = "#3498db";
    public static final String RED_COLOUR = "#e74c3c";
    private String name;
    private String colour;
    private boolean status;

    public Player(String colour) {
        this("skyvssea.model.Player", colour);
    }

    public Player(String name, String colour) {
        this.name = name;
        this.colour = colour;
        this.status = false;
    }

    public String getName() {
        return name;
    }

    public String getColour() {
        return colour;
    }

    public boolean isStatus() { return status; }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
