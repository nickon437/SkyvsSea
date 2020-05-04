package skyvssea.util;

import javafx.scene.paint.Color;

public class ColorUtil {

    public static Color STANDARD_BUTTON_COLOR = Color.valueOf("#1E88E5");

    public static Color getTextContrastColor(Color backgroundColor) {
        if (backgroundColor.getRed()*0.299 + backgroundColor.getGreen()*0.587 + backgroundColor.getBlue()*0.114 > 186) {
            return Color.BLACK;
        } else {
            return Color.WHITE;
        }
    }

    public static Color getHoveringColor(boolean isHovered, Color backgroundColor) {
        backgroundColor = isHovered ? Color.color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), .8) :
                Color.color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), 1);
        return backgroundColor;
    }
}
