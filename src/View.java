import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;

public class View extends SplitPane {

    public View() {
        super();
    }

    public void build() {
        GridPane board = createBoard(10, 10);
        HBox actionPane = createActionPane();
        VBox mainControlPane = createMainControlPane(board, actionPane);
        VBox infoPane = createInfoPane();

        this.getItems().add(mainControlPane);
        this.getItems().add(infoPane);
        this.setDividerPositions(0.8d);
    }

    private VBox createInfoPane() {
        TextArea statusTxt = new TextArea();
        TextArea speEffTxt = new TextArea();
        statusTxt.setEditable(false);
        speEffTxt.setEditable(false);

        VBox infoPane = new VBox();
        infoPane.getChildren().add(statusTxt);
        infoPane.getChildren().add(speEffTxt);

        VBox.setVgrow(statusTxt, Priority.ALWAYS);
        VBox.setVgrow(speEffTxt, Priority.ALWAYS);
        infoPane.setPadding(new Insets(20d));
        infoPane.setSpacing(20d);

        return infoPane;
    }

    private VBox createMainControlPane(GridPane board, HBox actionPane) {
        VBox mainControlPane = new VBox();
        mainControlPane.getChildren().add(board);
        mainControlPane.getChildren().add(actionPane);
        mainControlPane.setPadding(new Insets(20d));
        mainControlPane.setSpacing(20d);
        VBox.setVgrow(board, Priority.ALWAYS);

        return mainControlPane;
    }

    // TODO: Replace with TilePane
    private GridPane createBoard(int numCols, int numRows) {
        GridPane board = new GridPane();
        board.setGridLinesVisible(true);

        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            board.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            board.getRowConstraints().add(rowConst);
        }

        return board;
    }

    private HBox createActionPane() {
        HBox actionPane = new HBox();

        Button moveBtn = new Button("Move");
        Button killBtn = new Button("Kill");
        Button specialEffBtn = new Button("Special Effect");
        Button skipBtn = new Button("Skip");

        actionPane.getChildren().add(moveBtn);
        actionPane.getChildren().add(killBtn);
        actionPane.getChildren().add(specialEffBtn);
        actionPane.getChildren().add(skipBtn);
        actionPane.setSpacing(20d);

        // Maximize buttons' size to fill up action pane
        HBox.setHgrow(moveBtn, Priority.ALWAYS);
        HBox.setHgrow(killBtn, Priority.ALWAYS);
        HBox.setHgrow(specialEffBtn, Priority.ALWAYS);
        HBox.setHgrow(skipBtn, Priority.ALWAYS);
        moveBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        killBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        specialEffBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        skipBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        return actionPane;
    }

}
