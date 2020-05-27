package skyvssea.util;

import javafx.scene.paint.Color;

public class ColorUtil {

    public static final Color DEFAULT_LIGHT_BASE_COLOR = Color.WHITE;
    public static final Color DEFAULT_DARK_BASE_COLOR = Color.valueOf("#c8c8c8");
    public static final Color HIGHLIGHTED_COLOR = Color.valueOf("#FCC42C");
    public static final Color SCANNED_COLOR = Color.valueOf("#fde7aa");

    public static final Color STANDARD_BUTTON_COLOR = Color.valueOf("#1E88E5");
    public static final Color SECONDARY_BUTTON_COLOR = Color.valueOf("#E0E0E0");

    private static final double DARKER_BRIGHTER_FACTOR = 0.9;

    public static Color getTextContrastColor(Color bgColor) {
        Color textColor = bgColor.getRed()*255*0.299 + bgColor.getGreen()*255*0.587 + bgColor.getBlue()*255*0.114 > 186 ?
                Color.BLACK : Color.WHITE;
        return textColor;
    }

    public static Color getHoveringColor(boolean isHovered, Color bgColor) {
        bgColor = isHovered ? bgColor.deriveColor(0, 1, DARKER_BRIGHTER_FACTOR, 1) :
                bgColor.deriveColor(0, 1, 1/DARKER_BRIGHTER_FACTOR, 1);
        return bgColor;
    }
}
