import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;

public class View extends SplitPane {

    private GridPane board;

    public View() {
        super();
    }

    // TODO: Update view so that buttons are at the bottom and textbox (Current status & SpeEff TextBox) is on the side
    public void build() {
        createBoard(10, 10);
        createActionPane();

        this.setDividerPositions(0.8d);
    }

    // TODO: Replace with TilePane
    private void createBoard(int numCols, int numRows) {
        board = new GridPane();
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

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(board);
        AnchorPane.setTopAnchor(board, 20d);
        AnchorPane.setBottomAnchor(board, 20d);
        AnchorPane.setLeftAnchor(board, 20d);
        AnchorPane.setRightAnchor(board, 20d);

        this.getItems().add(anchorPane);
    }

    private void createActionPane() {
        VBox actionPane = new VBox();

        Button moveBtn = new Button("Move");
        Button killBtn = new Button("Kill");
        Button specialEffBtn = new Button("Special Effect");

        actionPane.getChildren().add(moveBtn);
        actionPane.getChildren().add(killBtn);
        actionPane.getChildren().add(specialEffBtn);
        actionPane.setSpacing(20d);

        // Maximize buttons' size to fill up action pane
        VBox.setVgrow(moveBtn, Priority.ALWAYS);
        VBox.setVgrow(killBtn, Priority.ALWAYS);
        VBox.setVgrow(specialEffBtn, Priority.ALWAYS);
        moveBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        killBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        specialEffBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(actionPane);
        AnchorPane.setTopAnchor(actionPane, 20d);
        AnchorPane.setBottomAnchor(actionPane, 20d);
        AnchorPane.setLeftAnchor(actionPane, 20d);
        AnchorPane.setRightAnchor(actionPane, 20d);

        this.getItems().add(anchorPane);
    }

}
