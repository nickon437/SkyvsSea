package skyvssea.view;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class PieceView extends StackPane {
    private Circle circle;
    private Text text;

    public PieceView(String name, Color color) {
        this.circle = new Circle(5, color);
        this.text = createText(name);
        this.getChildren().addAll(circle, text);
    }

    private Text createText(String name) {
        Text text = new Text(name);
        text.setTextAlignment(TextAlignment.CENTER);
        return text;
    }

    public void updatePieceViewSize(double tileSize) {
        final double CIRCLE_CONTENT_PERCENTAGE = 0.9;
        final double LABEL_CONTENT_PERCENTAGE = 0.9;
        circle.setRadius((tileSize * CIRCLE_CONTENT_PERCENTAGE) / 2);
        text.setWrappingWidth(tileSize * CIRCLE_CONTENT_PERCENTAGE * LABEL_CONTENT_PERCENTAGE);
    }
}
