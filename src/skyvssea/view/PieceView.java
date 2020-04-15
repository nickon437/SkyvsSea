package skyvssea.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;

public class PieceView extends StackPane {
    private Circle circle;
    private Label label;

    public PieceView(String name) {
        this.circle = new Circle(5, Color.ORCHID);
        this.label = createLabel(name);
        this.getChildren().addAll(circle, label);
    }

    private Label createLabel(String name) {
        Label label = new Label(name);
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        return label;
    }

    public void updatePieceViewSize(double tileSize) {
        final double CIRCLE_CONTENT_PERCENTAGE = 0.9;
        final double LABEL_CONTENT_PERCENTAGE = 0.9;
        circle.setRadius((tileSize * CIRCLE_CONTENT_PERCENTAGE) / 2);
        label.setPrefWidth(tileSize * CIRCLE_CONTENT_PERCENTAGE * LABEL_CONTENT_PERCENTAGE);
    }
}
