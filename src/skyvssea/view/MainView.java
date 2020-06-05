package skyvssea.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import skyvssea.util.RegionUtil;
import skyvssea.util.ResourceManager;

public class MainView extends AnchorPane {

    private SplitPane gameSplitPane = new SplitPane();
    private StackPane winnerPane = new StackPane();
    private Label declarationLabel = new Label("Winner");

    public MainView(MainControlPane mainControlPane, InfoPane infoPane) {
        gameSplitPane.getItems().addAll(mainControlPane, infoPane);

        formatGameSplitPane(gameSplitPane);
        formatWinnerPane(winnerPane);

        this.getChildren().addAll(gameSplitPane, winnerPane);
    }

    private void formatGameSplitPane(SplitPane gameSplitPane) {
        gameSplitPane.setDividerPositions(0.8d);
        RegionUtil.setAnchor(gameSplitPane, 0.0, 0.0, 0.0, 0.0);
    }

    public void formatWinnerPane(StackPane winnerPane) {
        Pane overlayPane = new Pane();
        RegionUtil.setBackground(overlayPane, Color.BLACK, null, null);
        overlayPane.setOpacity(0.8);

        ResourceManager.setFont(ResourceManager.HEADING_STYLE, declarationLabel);
        VBox winnerInfo = new VBox(declarationLabel);
        winnerInfo.setAlignment(Pos.CENTER);
        winnerInfo.setMaxSize(200, 100);
        RegionUtil.setBackground(winnerInfo, Color.WHITE, new CornerRadii(30), null);

        winnerPane.getChildren().addAll(overlayPane, winnerInfo);
        winnerPane.setVisible(false);
        RegionUtil.setAnchor(winnerPane, 0.0, 0.0, 0.0, 0.0);
    }

    public void setDeclarationLabel(String winner) {
        declarationLabel.setText(winner.toUpperCase() + " WON");
        winnerPane.setVisible(true);
    }
}
