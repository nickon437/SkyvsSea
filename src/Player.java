public class Player {
    public static final String BLUE_COLOUR = "#3498db";
    public static final String RED_COLOUR = "#e74c3c";
    private String name;
    private String colour;

    public Player(String colour) {
        this("Player", colour);
    }

    public Player(String name, String colour) {
        this.name = name;
        this.colour = colour;
    }

    public String getName() {
        return name;
    }

    public String getColour() {
        return colour;
    }
}
