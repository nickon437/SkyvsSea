package skyvssea.util;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class ButtonUtil {
    public static void formatStandardButton(Button button, Color color) {
        button.setStyle("-fx-font-weight: bold;");
        button.setTextFill(ColorUtil.getTextContrastColor(color));
        button.setBackground(new Background(new BackgroundFill(color, new CornerRadii(5), null)));
        button.setCursor(Cursor.HAND);
    }

    public static void formatHoveringEffect(Button button, boolean isHovered) {
        Color curColor = (Color) button.getBackground().getFills().get(0).getFill();
        Color modifiedColor = ColorUtil.getHoveringColor(isHovered, curColor);
        button.setBackground(new Background(new BackgroundFill(modifiedColor, new CornerRadii(5), null)));
    }
}
