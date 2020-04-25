package skyvssea.util;

import javafx.scene.paint.Color;

public class ColorUtil {

    public static Color getTextContrastColor(Color backgroundColor) {
        if (backgroundColor.getRed()*0.299 + backgroundColor.getGreen()*0.587 + backgroundColor.getBlue()*0.114 > 186) {
            return Color.BLACK;
        } else {
            return Color.WHITE;
        }
    }
}
