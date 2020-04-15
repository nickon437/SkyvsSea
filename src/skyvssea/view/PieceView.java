package skyvssea.view;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PieceView extends StackPane {
    private Circle circle;

    public PieceView() {
        this.circle = new Circle(5, Color.BLACK);
        this.getChildren().add(circle);
    }

}
