package skyvssea.view;

import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;

public class MainView extends SplitPane {

    public MainView(Pane mainControlPane, Pane infoPane) {
        // TODO: Should I have this practice ie. having panes in constructor instead of separating them.
        this.getItems().addAll(mainControlPane, infoPane);
        this.setDividerPositions(0.8d);
    }
}
