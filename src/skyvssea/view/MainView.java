package skyvssea.view;

import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;

public class MainView extends SplitPane {

    public MainView(MainControlPane mainControlPane, InfoPane infoPane) {
        this.getItems().addAll(mainControlPane, infoPane);
        this.setDividerPositions(0.8d);
    }
}
