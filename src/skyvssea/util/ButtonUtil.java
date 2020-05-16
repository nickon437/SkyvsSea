package skyvssea.util;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ButtonUtil {

    public static final double STANDARD_BUTTON_HEIGHT = 40;
    public static final double BUTTON_SPACING = 20;

    @Requires("button != null && color != null")
    public static void formatStandardButton(Button button, Color color) {
        button.setStyle("-fx-font-weight: bold;");
        button.setTextFill(ColorUtil.getTextContrastColor(color));
        button.setBackground(new Background(new BackgroundFill(color, new CornerRadii(5), null)));
        button.setCursor(Cursor.HAND);
    }

    @Requires("button != null")
    @Ensures("button.getBackground().getFills().get(0).getFill() != old(button.getBackground().getFills().get(0).getFill())")
    public static void formatHoveringEffect(Button button, boolean isHovered) {
        Color curColor = (Color) button.getBackground().getFills().get(0).getFill();
        Color modifiedColor = ColorUtil.getHoveringColor(isHovered, curColor);
        button.setBackground(new Background(new BackgroundFill(modifiedColor, new CornerRadii(5), null)));
    }

    @Requires("button != null && url.length() > 0 && url.contains(\".\")")
    @Ensures("button.getGraphic() != null")
    public static void formatGraphic(Button button, String url) {
        ImageView imageView = new ImageView(url);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setFitHeight(24);
        button.setGraphic(imageView);
        button.setGraphicTextGap(4);
    }

    public static void maximizeHBoxControlSize(Control control) {
        control.setPrefHeight(STANDARD_BUTTON_HEIGHT);
        HBox.setHgrow(control, Priority.ALWAYS);
        control.setMaxWidth(Double.MAX_VALUE);
    }
}
