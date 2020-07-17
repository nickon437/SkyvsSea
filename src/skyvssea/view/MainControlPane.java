package skyvssea.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MainControlPane extends VBox {
    public MainControlPane(BoardPane boardPane, ActionPane actionPane) {
        this.getChildren().addAll(boardPane, actionPane);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20d);
        this.setPadding(new Insets(20d));
        MainControlPane.setVgrow(boardPane, Priority.ALWAYS);

        this.setStyle("-fx-background-color: white");
    }
}
