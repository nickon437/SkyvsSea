package skyvssea.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import skyvssea.model.Avatar;
import skyvssea.util.ColorUtil;

public class PieceView extends /*StackPane implements */Avatar {
    private Circle border;
    private Circle circle;
    private Text text;

    public PieceView(String name, Color color) {
        Color borderColor = color.darker().darker().darker();
        this.border = new Circle(5, borderColor);
        this.circle = new Circle(5, color);
        border.setStroke(borderColor);
        circle.setStroke(borderColor);

        this.text = createText(name, color);
        this.getChildren().addAll(border, circle, text);
    }

    private Text createText(String name, Color backgroundColor) {
        Text text = new Text(name);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setStyle("-fx-font-weight: bold;");
        text.setFill(ColorUtil.getTextContrastColor(backgroundColor));
        return text;
    }

    public void updateSize(double tileSize) {
        final double CIRCLE_CONTENT_PERCENTAGE = 0.8;
        final double LABEL_CONTENT_PERCENTAGE = 0.9;
        final double OFFSET = 0.05;

        border.setRadius((tileSize * CIRCLE_CONTENT_PERCENTAGE) / 2);
        circle.setRadius((tileSize * CIRCLE_CONTENT_PERCENTAGE) / 2);
        text.setWrappingWidth(tileSize * CIRCLE_CONTENT_PERCENTAGE * LABEL_CONTENT_PERCENTAGE);

        border.setTranslateY(circle.getTranslateY() + (tileSize * OFFSET));
        border.setStrokeWidth(tileSize * 0.03);
        circle.setStrokeWidth(tileSize * 0.02);
    }
}
